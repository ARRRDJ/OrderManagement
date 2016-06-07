package nl.avisi.ordermanagement.scheduledOrder;

import nl.avisi.ordermanagement.domain.Invoice;
import nl.avisi.ordermanagement.domain.Order;
import nl.avisi.ordermanagement.service.invoice.InvoiceService;
import nl.avisi.ordermanagement.service.order.OrderService;
import nl.avisi.ordermanagement.service.senderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import static nl.avisi.ordermanagement.service.senderHelper.convertOrderLineToString;

/**
 * Created by robert on 5/19/16.
 */
@Component
public class invoiceSender {
    @Autowired
    OrderService orderService;
    @Autowired
    InvoiceService invoiceService;

    //    @Scheduled(cron = "0 15 10 ? * *")
    /**
     * This function is used to check for invoices that have to be send
     */
    @Scheduled(fixedRate = 10000)
    public void sendDueInvoices() {
        System.out.println("Checking for orders to be send " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        Date today = new Date();
        String timestampFormatted = new SimpleDateFormat("dd/MM/yyyy").format(today);

        for (Order order : orderService.findAllActive()) {
            for (Invoice invoice : order.getInvoices()) {
                if (invoice.getSendAt() == null && timestampFormatted.toString().equals(new SimpleDateFormat("dd/MM/yyyy").format(invoice.getScheduledDate()))) {
                    // check for invoices to be send
                    System.out.println("SENDING NOW");
                    senderHelper.generatePDF(order, invoice, order.getContactPerson(), order.getCompany(), order.getSender(), convertOrderLineToString(order));
                    senderHelper.sendInvoice(order, order.getCompany(), order.getSender());
                    invoice.setSendAt(new Date());
                    invoiceService.save(invoice);
                }
            }
        }

        System.out.println("Successfully send invoices");
    }
}