package nl.avisi.ordermanagement.pdf;

/**
 * Created by robert on 5/31/16.
 */

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PdfGenerator {
    public static void generatePDF(String btw, String kvk, String BIC, String IBAN, String message, String companyName, String contactPersonName, String mailBox, String zipCode, String senderName
            , String senderStreet, String senderZipCode, String senderCity, String senderWebsite, String expireDate, String invoiceNumber, String identification, String companyNumber, String orderLines)
            throws IOException {
        try {
            // Send data
            String urlParameters =
                    "BTW=" + URLEncoder.encode(btw, "UTF-8") +
                            "&KVK=" + URLEncoder.encode(kvk, "UTF-8") +
                            "&BIC=" + URLEncoder.encode(BIC, "UTF-8") +
                            "&IBAN=" + URLEncoder.encode(IBAN, "UTF-8") +
                            "&Message=" + URLEncoder.encode(message, "UTF-8") +
                            "&CompanyName=" + URLEncoder.encode(companyName, "UTF-8") +
                            "&ContactPersonName=" + URLEncoder.encode(contactPersonName, "UTF-8") +
                            "&MailBox=" + URLEncoder.encode(mailBox, "UTF-8") +
                            "&ZipCode=" + URLEncoder.encode(zipCode, "UTF-8") +
                            "&SenderName=" + URLEncoder.encode(senderName, "UTF-8") +
                            "&SenderStreet=" + URLEncoder.encode(senderStreet, "UTF-8") +
                            "&SenderZipCode=" + URLEncoder.encode(senderZipCode, "UTF-8") +
                            "&SenderCity=" + URLEncoder.encode(senderCity, "UTF-8") +
                            "&SenderWebsite=" + URLEncoder.encode(senderWebsite, "UTF-8") +
                            "&ExpireDate=" + URLEncoder.encode(expireDate, "UTF-8") +
                            "&InvoiceNumber=" + URLEncoder.encode(invoiceNumber, "UTF-8") +
                            "&Identification=" + URLEncoder.encode(identification, "UTF-8") +
                            "&CompanyNumber=" + URLEncoder.encode(companyNumber, "UTF-8") +
                            "&OrderLines=" + URLEncoder.encode(orderLines, "UTF-8");
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            String request = "http://roberkg89.eightynine.axc.nl/pdfGenerator/generate/";
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();


            InputStream in = conn.getInputStream();
            OutputStream fos = new FileOutputStream("./savedPDF/" + senderName + "_" + companyName + ".pdf");

            int length = -1;

            byte[] buffer = new byte[1024];

            while ((length = in.read(buffer)) != -1) {

                fos.write(buffer, 0, length);

            }

            System.out.println("Done!");
            fos.close();
            in.close();
        } catch (Exception ignored) {
            System.out.println("Something went wrong while generating PDF. Caused by: \n" + ignored);
        }
    }
}