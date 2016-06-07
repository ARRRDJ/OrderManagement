package nl.avisi.ordermanagement.controller;

import nl.avisi.ordermanagement.domain.Invoice;
import nl.avisi.ordermanagement.service.invoice.InvoiceService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by robert on 6/1/16.
 */

@RestController
@RequestMapping(value = "/invoices")
public class InvoiceController {
    // Inject services
    @Autowired
    private InvoiceService invoiceService;

    /**
     * This function is used to get a specific invoice and display it when there's a GET request on /invoices/{id}
     *
     * @param id The ID of the invoice that has to be found
     * @return The response message with the found invoice or a message saying the invoice has not been found
     */
    @RequestMapping(value = "/{id}")
    public Map<String, Object> show(@PathVariable String id) {
//      Get the invoice
        Invoice invoice = invoiceService.findOne(id);
//      Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("invoice", invoice);
//      Send to client
        return response;
    }

    /**
     * This function is used to get all the invoices and display them when there's a GET request on /invoices/
     *
     * @return The response message with all the invoices and a number depicting the amount of invoices that have been found
     */
    @RequestMapping(value = "/")
    public Map<String, Object> showAll() {
//      Get all invoices
        List<Invoice> invoices = invoiceService.findAll();
//      Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("totalInvoices", invoices.size());
        response.put("invoices", invoices);
//      Send to client
        return response;
    }

    /**
     * This function is used to delete a invoice when there's a DELETE request on /invoices/{id}
     *
     * @param id The ID of the invoice that has to be deleted
     * @return The response message, which can either be a success message or a message saying the invoice hasn't been found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> delete(@PathVariable String id) {
//      Get the invoice that has to be deleted
        Invoice invoice = invoiceService.findOne(id);

//      Build response
        Map<String, Object> response = new HashMap<String, Object>();
//      Checks if the invoice exists and deletes it
        if (invoice != null) {
            invoiceService.delete(id);
            response.put("message", "Invoice deleted successfully");
        } else {
            response.put("message", "Invoice not found");
        }
//      Send to client
        return response;
    }

    /**
     * This function is used to get invoices with a certain number and display them when there's a GET request on /invoices/search/number/{number}
     *
     * @param number The number of the invoice/invoices that have to be found
     * @return The response message with the found invoice/invoices
     */
    @RequestMapping(value = "/search/invoiceNumber/{number}")
    public Map<String, Object> findByInvoiceNumber(@PathVariable String number) {
//      Find the invoice
        List<Invoice> invoices = invoiceService.findByInvoiceNumber(number);
//      Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("totalInvoices", invoices.size());
        response.put("invoices", invoices);
//      Send to client
        return response;
    }
}