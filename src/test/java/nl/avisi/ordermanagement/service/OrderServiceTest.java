package nl.avisi.ordermanagement.service;

import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.*;
import nl.avisi.ordermanagement.service.contactPerson.ContactPersonService;
import nl.avisi.ordermanagement.service.customer.CompanyService;
import nl.avisi.ordermanagement.service.invoice.InvoiceService;
import nl.avisi.ordermanagement.service.order.OrderService;
import nl.avisi.ordermanagement.service.salesItem.SalesItemService;
import nl.avisi.ordermanagement.service.sender.SenderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Sam on 5/12/16.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(OrderManagement.class)
@WebIntegrationTest
public class OrderServiceTest {
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

    private String id = "testid";

    @Before
    public void setUp(){
        ContactPerson contactPerson = new ContactPerson("Firstname", "Lastname", "e@mail.com", "06-1234567");
        contactPerson.setId("id");
        List<ContactPerson> cpList = new ArrayList<>();
        cpList.add(contactPerson);
        Company company = new Company("Name", "invoice@mail.com", "City", "Zipcode", "Adress", cpList, "MailBox", "Number");
        company.setId("id");
        Sender sender = new Sender("Name", "e@mail.com", "Address", "Zipcode", "City", "KVKNumber", "BICNumber", "IBANNumber", "BTWNumber", "Website");
        sender.setId("id");
        Type type = new Type("Type");
        type.setId("id");
        Tax tax = new Tax(5.5);
        tax.setId("id");
        SalesItem salesItem = new SalesItem("Name", "Description", 5.5, "SKU", type, tax);
        salesItem.setId("id");
        OrderLine orderLine = new OrderLine(5, salesItem);
        orderLine.setId("id");
        List<OrderLine> odlList = new ArrayList<>();
        odlList.add(orderLine);
        Invoice invoice = new Invoice("number", new Date(), new Date(), new Date());
        invoice.setId("id");
        List<Invoice> inList = new ArrayList<>();
        inList.add(invoice);

        Order order1 = new Order("1", true, new Date(), new Date(), "MONTHLY", "invoice@mail.nl", 30, company, contactPerson, sender, "message", "identification");
        order1.setId(id);
        order1.setOrderLines(odlList);
        order1.setInvoices(inList);

        Order order2 = new Order("2", false, new Date(), new Date(), "YEARLY", "invoices@mail.nl", 14, company, contactPerson, sender, "messages", "identifications");
        order2.setId("testid2");
        order2.setOrderLines(odlList);
        order2.setInvoices(inList);

        orderService.save(order1);
        orderService.save(order2);
    }

    @Test
    public void saveOrder() throws Exception {
        ContactPerson contactPerson = new ContactPerson("Firstname", "Lastname", "e@mail.com", "06-1234567");
        contactPerson.setId("id");
        List<ContactPerson> cpList = new ArrayList<>();
        cpList.add(contactPerson);
        Company company = new Company("Name", "invoice@mail.com", "City", "Zipcode", "Adress", cpList, "MailBox", "Number");
        company.setId("id");
        Sender sender = new Sender("Name", "e@mail.com", "Address", "Zipcode", "City", "KVKNumber", "BICNumber", "IBANNumber", "BTWNumber", "Website");
        sender.setId("id");
        Type type = new Type("Type");
        type.setId("id");
        Tax tax = new Tax(5.5);
        tax.setId("id");
        SalesItem salesItem = new SalesItem("Name", "Description", 5.5, "SKU", type, tax);
        salesItem.setId("id");
        OrderLine orderLine = new OrderLine(5, salesItem);
        orderLine.setId("id");
        List<OrderLine> odlList = new ArrayList<>();
        odlList.add(orderLine);
        Invoice invoice = new Invoice("number", new Date(), new Date(), new Date());
        invoice.setId("id");
        List<Invoice> inList = new ArrayList<>();
        inList.add(invoice);

        Order order = new Order("3", true, new Date(), new Date(), "MONTHLY", "invoice@mail.nl", 30, company, contactPerson, sender, "message", "identification");
        order.setId("testid3");
        order.setOrderLines(odlList);
        order.setInvoices(inList);
        orderService.save(order);

        Order retrievedOrder = orderService.findOne("testid3");
        assertNotNull(retrievedOrder);
        orderService.delete("testid3");
    }

    @Test
    public void getOrders() throws Exception {
        List<Order> orders = orderService.findAll();
        assertNotNull("failure - Expected to retrieve orders", orders);
        assertTrue(orders.size() >= 2);
    }

    @Test
    public void getOrderByID() throws Exception {
        Order order = orderService.findOne(id);
        assertNotNull(order);
    }

    @Test
    public void getActiveOrders() throws Exception {
        List<Order> orders = orderService.findAllActive();
        assertNotNull("Should find orders", orders);
        assertTrue(orders.size() >= 1);
    }

    //TODO tests voor de nog niet gemaakte functies

    @After
    public void cleanUp() {
        companyService.delete("testid");
        companyService.delete("testid2");
    }

}