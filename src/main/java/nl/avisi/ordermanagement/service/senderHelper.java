package nl.avisi.ordermanagement.service;

import nl.avisi.ordermanagement.domain.*;
import nl.avisi.ordermanagement.mailer.Mailer;
import nl.avisi.ordermanagement.pdf.PdfGenerator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by robert on 6/3/16.
 */
public class senderHelper {
    /**
     * This function is used to generate a PDF
     * @param order The order with the required data for the PDF
     * @param invoice The invoice with the required data for the PDF
     * @param contactPerson The contact person with the required data for the PDF
     * @param company The company with the required data for the PDF
     * @param sender The sender with the required data for the PDF
     * @param orderLinesAsJSONString The order lines with the required data for the PDF as a JSON string
     **/
    public static void generatePDF(Order order, Invoice invoice, ContactPerson contactPerson, Company company, Sender sender, String orderLinesAsJSONString) {
        String expireDate = new SimpleDateFormat("dd/MM/yyyy").format(invoice.getExpireDate());
        try {
            PdfGenerator.generatePDF(sender.getBTWNumber(), sender.getKVKNumber(), sender.getBICNumber(), sender.getIBANNumber(), order.getMessage(), company.getName(), contactPerson.getFirstName() + " " + contactPerson.getLastName(), company.getMailBox(), company.getZipcode(), sender.getName(), sender.getAddress(), sender.getZipCode(), sender.getCity(), sender.getWebsite(), expireDate, invoice.getNumber(), order.getIdentification(), company.getNumber(), orderLinesAsJSONString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function is used to mail an invoice to the customer
     * @param order The order with the required data for the mail
     * @param company The company with the required data for the mail
     * @param sender The sender with the required data for the mail
     **/
    public static void sendInvoice(Order order, Company company, Sender sender) {
        Mailer.send(order.getInvoiceEmail(), sender.getEmail(), "./savedPDF/" + sender.getName() + "_" + company.getName() + ".pdf");
        System.out.println("Sent it!");
    }
    /**
     * This function is used to convert the order lines of an order to a string
     * @param order The order of which the order lines have to be converted to a string
     * @return Returns the generated JSON string
     **/
    public static String convertOrderLineToString(Order order) {
        DecimalFormat df = new DecimalFormat("#.##");
        String orderLinesAsJSONString = "[";
        int i = 1;
        for (OrderLine orderLine : order.getOrderLines()) {
            orderLinesAsJSONString += "{\"quantity\": " + orderLine.getQuantity() + ", \"description\": \"" + orderLine.getSalesItem().getDescription() + "\", \"price\":" + orderLine.getSalesItem().getPrice() + ", \"taxRate\":" + orderLine.getSalesItem().getTax().getRate() + ", \"total\":" + df.format((orderLine.getSalesItem().getPrice() * orderLine.getQuantity()) * ((orderLine.getSalesItem().getTax().getRate() / 100) + 1)) + "}";
            if (i != order.getOrderLines().size()) {
                orderLinesAsJSONString += ",";
            }
            i++;
        }
        orderLinesAsJSONString += "]";
        return orderLinesAsJSONString;
    }
}
