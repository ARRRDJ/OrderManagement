package nl.avisi.ordermanagement.mailer;


import net.sargue.mailgun.content.MailContent;

import java.io.File;

/**
 * Created by robert on 6/2/16.
 */
public class Mailer {
    public static void send(String receiver, String sender, String attachementLocation) {
        sender = sender.replaceAll("\\s+", "");
        receiver = receiver.replaceAll("\\s+", "");
        System.out.println(sender);
        System.out.println(receiver);

        Configuration configuration = new Configuration()
                .domain("sandbox2daab7c66827426c93078d9aee195fa2.mailgun.org")
                .apiKey("key-9860623a5fd8d4d33bd1d18d03f278be")
//                postmaster@sandbox2daab7c66827426c93078d9aee195fa2.mailgun.org
                .from("Avisi Order Management", sender);

        MailContent content = new MailContent()
                .h3("Here is your invoice")
                .p("Avisi")
                .close();

        MailBuilder.using(configuration)
                .to(receiver)
                .subject("Your invoice is ready")
                .content(content)
                .multipart()
                .attachment(new File(attachementLocation))
                .build()
                .send();

    }
}
