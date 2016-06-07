package nl.avisi.ordermanagement.controller;

import nl.avisi.ordermanagement.domain.*;
import nl.avisi.ordermanagement.service.contactPerson.ContactPersonService;
import nl.avisi.ordermanagement.service.customer.CompanyService;
import nl.avisi.ordermanagement.service.invoice.InvoiceService;
import nl.avisi.ordermanagement.service.order.OrderService;
import nl.avisi.ordermanagement.service.salesItem.SalesItemService;
import nl.avisi.ordermanagement.service.sender.SenderService;
import nl.avisi.ordermanagement.service.senderHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static nl.avisi.ordermanagement.service.senderHelper.convertOrderLineToString;

/**
 * Created by robert on 4/19/16.
 */
@RestController
@RequestMapping(value = "/orders")
@Component
public class OrderController implements CRUDController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private SalesItemService salesItemService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ContactPersonService contactPersonService;

    @Autowired
    private SenderService senderService;

    /**
     * This method is used to create a order when there's a POST request on /orders/
     *
     * @param body Contains the variables of the POST request, accessed via the JSONObject
     * @return The response containing a success message and the saved order.
     */
    @Override
    public Map<String, Object> create(@RequestBody String body) {
        JSONObject jsonObject = new JSONObject(body);

        Company company = companyService.findOne(jsonObject.getString("companyId"));
        ContactPerson contactPerson = contactPersonService.findOne(jsonObject.getString("contactPersonId"));
        Sender sender = senderService.findOne(jsonObject.getString("senderId"));

        List<Invoice> invoiceList = getInvoiceList(jsonObject.getString("period"), jsonObject.getString("endDate"), jsonObject.getString("startDate"), jsonObject.getInt("payDays"));
        Order order = new Order(jsonObject.getString("number"), true, new Date(), new Date(), jsonObject.getString("period"), jsonObject.getString("invoiceEmail"), jsonObject.getInt("payDays"), company, contactPerson, sender, jsonObject.getString("message"), jsonObject.getString("identification"));
        order.setInvoices(invoiceList);

        List<OrderLine> orderLineList = new ArrayList<OrderLine>();

        JSONArray orderLines = jsonObject.getJSONArray("orderLines");
        for (int i = 0; i < orderLines.length(); ++i) {
            JSONObject orderLine = orderLines.getJSONObject(i);
            SalesItem salesItem = salesItemService.findOne(orderLine.getString("salesItemId"));
            OrderLine createdOrderLine = new OrderLine(orderLine.getInt("quantity"), salesItem);
            orderLineList.add(createdOrderLine);
        }
        order.setOrderLines(orderLineList);

        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("message", "Order created successfully");
        response.put("order", orderService.save(order));

        return response;
    }

    /**
     * This function is used to get a specific order and display it when there's a GET request on /orders/{id}
     *
     * @param id The ID of the order that has to be found
     * @return The response message with the found order or a message saying the order has not been found
     */
    @Override
    public Map<String, Object> show(@PathVariable String id) {
        Order order = orderService.findOne(id);
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        if (order != null) {
            response.put("order", order);
        } else {
            response.put("message", "Order not found");
        }
        return response;
    }

    /**
     * This function is used to get all the orders and display them when there's a GET request on /orders/
     *
     * @return The response message with all the orders and a number depicting the amount of orders that have been found
     */
    @Override
    public Map<String, Object> showAll() {
        List<Order> orders = orderService.findAllActive();
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("totalOrders", orders.size());
        response.put("orders", orders);
        return response;
    }

    /**
     * This function is used to update a order when there's a PUT request on /orders/{id}
     *
     * @param id   The ID of the order that has to be updated
     * @param body Contains the variables of the PUT request, accessed via the JSONObject
     * @return The response message, which can either be a success message with the updated order or a message saying the order hasn't been found
     */
    @Override
    public Map<String, Object> update(@RequestBody String body, @PathVariable String id) {
        JSONObject jsonObject = new JSONObject(body);
        Order order = orderService.findOne(id);
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        if (order != null) {
            order.setInvoiceEmail(jsonObject.getString("invoiceEmail"));
            order.setUpdatedAt(new Date());
            Order savedOrder = orderService.save(order);

            response.put("message", "Order updated successfully");
            response.put("order", savedOrder);
            return response;
        } else {
            response.put("message", "Order not found");
            return response;
        }
    }

    /**
     * This function is used to delete a order when there's a DELETE request on /orders/{id}
     *
     * @param id The ID of the order that has to be deleted
     * @return The response message, which can either be a success message or a message saying the order hasn't been found
     */
    @Override
    public Map<String, Object> delete(@PathVariable String id) {
        Order order = orderService.findOne(id);
        Map<String, Object> response = new HashMap<String, Object>();
        if (order != null) {
            order.setActive(false);
            orderService.save(order);
            response.put("message", "Order deleted successfully");
        } else {
            response.put("message", "Order not found");
        }
        return response;
    }

    /**
     * This function is used to get orders with a certain number and display them when there's a GET request on /orders/search/name/{name}
     *
     * @param name The name of the order/orders that have to be found
     * @return The response message with the found order/orders
     */
    @RequestMapping(value = "/search/name/{name}")
    public Map<String, Object> getOrderByName(@PathVariable String name) {
        List<Order> orders = orderService.findByName(name);
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("totalOrders", orders.size());
        response.put("orders", orders);
        return response;
    }

    /**
     * A function that generates the orders and puts them in a list
     *
     * @param period    The period of the order (weekly, monthly, yearly)
     * @param endDate   The end date of the recurring orders
     * @param startDate The start date of the recurring orders
     * @param payDays   The period a order has to pay the order in days
     * @return Returns the generated order list
     */
    private List<Invoice> getInvoiceList(String period, String endDate, String startDate, int payDays) {
        List<Invoice> invoiceList = new ArrayList<Invoice>();
        Calendar calendarDate = Calendar.getInstance();
        Calendar endDateCalendar = Calendar.getInstance();
        int number = 1;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

//      Uses try/catch to prevent having to use ParseException
        Date startDateFormatted = null;
        Date endDateFormatted = null;
        try {
            startDateFormatted = formatter.parse(startDate);
            endDateFormatted = formatter.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendarDate.setTime(startDateFormatted);
        endDateCalendar.setTime(endDateFormatted);

        //TODO dagen waarmee expire date moet opgehoogd worden uit config bestand halen (als die bestaat)
        switch (period) {
            case "Weekly":
                invoiceList = generateInvoice(Calendar.DATE, 7, payDays, number, calendarDate, endDateCalendar);
                break;
            case "Monthly":
                invoiceList = generateInvoice(Calendar.MONTH, 1, payDays, number, calendarDate, endDateCalendar);
                break;
            case "Yearly":
                invoiceList = generateInvoice(Calendar.YEAR, 1, payDays, number, calendarDate, endDateCalendar);
                break;
        }

        return invoiceList;
    }

    /**
     * A function that generates the invoices for an order
     *
     * @param period The type of period, can be day, month or year
     * @param periodTime The amount of period, can specify the amount of days, months or years
     * @param payDays The amount of days the customer has to pay the invoice
     * @param number The invoice number
     * @param calendarDate The start date of the invoices
     * @param endDateCalendar The end date of the invoices
     * @return Returns the list with the created invoices
     */
    public List<Invoice> generateInvoice(int period, int periodTime, int payDays, int number, Calendar calendarDate, Calendar endDateCalendar) {
        Calendar expireDate = Calendar.getInstance();
        List<Invoice> invoices = new ArrayList<>();
        while (!calendarDate.after(endDateCalendar)) {
            expireDate.setTime(calendarDate.getTime());
            expireDate.add(Calendar.DATE, payDays);
            invoices.add(new Invoice(Integer.toString(number), calendarDate.getTime(), null, expireDate.getTime()));
            calendarDate.add(period, periodTime);
            number++;
        }
        invoices.add(new Invoice(Integer.toString(number), calendarDate.getTime(), null, expireDate.getTime()));
        return invoices;
    }

    /**
     * A function that generates the pdf
     * @param id The ID of the order that contains the invoice
     * @param invoiceId The ID of the invoice that has to be converted to a PDF
     * @return Returns a response with a success message
     * @throws ParseException An exception gets thrown when there's incorrect data
     */
    @RequestMapping(value = "/{id}/invoices/{invoiceId}/pdf")
    public Map<String, Object> generatePDF(@PathVariable String id, @PathVariable String invoiceId) throws ParseException {
        Order order = orderService.findOne(id);
        Invoice invoice = invoiceService.findOne(invoiceId);
        ContactPerson contactPerson = order.getContactPerson();
        Company company = order.getCompany();
        Sender sender = order.getSender();
        String orderLinesAsJSONString = convertOrderLineToString(order);

        senderHelper.generatePDF(order, invoice, contactPerson, company, sender, orderLinesAsJSONString);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "success");
        File file = new File("./savedPDF/" + sender.getName() + "_" + company.getName() + ".pdf");
        response.put("pdf", file);
        return response;
    }

    /**
     * A function that generates an invoice and sends it
     * @param id The ID of the order that has the invoice that has to be send
     * @param invoiceId The ID of the invoice that has to be send
     * @return Returns a response with a success message
     */
    @RequestMapping(value = "/{id}/invoices/{invoiceId}/send")
    public Map<String, Object> sendInvoice(@PathVariable String id, @PathVariable String invoiceId) {
        Order order = orderService.findOne(id);
        Invoice invoice = invoiceService.findOne(invoiceId);
        String orderLinesAsJSONString = convertOrderLineToString(order);

        senderHelper.generatePDF(order, invoice, order.getContactPerson(), order.getCompany(), order.getSender(), orderLinesAsJSONString);
        senderHelper.sendInvoice(order, order.getCompany(), order.getSender());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", "true");
        return response;
    }

    /**
     * This function is used to find the invoices of an order
     * @param id The ID of the order of which the invoices have to be retrieved
     * @return Returns a response with the invoices
     */
    @RequestMapping(value = "/{id}/invoices/")
    public Map<String, Object> getInvoices(@PathVariable String id) {
        Order order = orderService.findOne(id);
        List<Invoice> invoices = order.getInvoices();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("invoices", invoices);
        return response;
    }

    /**
     * This function is used to send the due invoices
     * @return Returns a response with a success message
     */
    @RequestMapping(value = "/sendDue")
    public Map<String, Object> sendDue() {
        System.out.println("Checking");
        Date today = new Date();
        String timestampFormatted = new SimpleDateFormat("dd/MM/yyyy").format(today);

        for (Order order : orderService.findAllActive()) {
            for (Invoice invoice : order.getInvoices()) {
                if (invoice.getSendAt() == null && timestampFormatted.toString().equals(new SimpleDateFormat("dd/MM/yyyy").format(invoice.getScheduledDate()))) {
                    System.out.println("SENDING NOW");
                    senderHelper.generatePDF(order, invoice, order.getContactPerson(), order.getCompany(), order.getSender(), convertOrderLineToString(order));
                    senderHelper.sendInvoice(order, order.getCompany(), order.getSender());

                    invoice.setSendAt(new Date());
                    invoiceService.save(invoice);
                }
            }
        }
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", "true");
        return response;
    }


}

