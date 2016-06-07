package nl.avisi.ordermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.Sender;
import nl.avisi.ordermanagement.service.sender.SenderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sjors on 5/30/16.
 */

// @RunWith(SpringJUnit4ClassRunner.class)
// @SpringApplicationConfiguration(OrderManagement.class)
@WebIntegrationTest
public class SenderControllerTest extends TestCase {
    //Required to Generate JSON content from Java objects
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private SenderService senderService;

    //Test RestTemplate to invoke the APIs.
    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void createSender() throws Exception {
        //Building the JSON String
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("name", "avisi5");
        requestBody.put("email", "test@test.nl");
        requestBody.put("address", "Meander 251");
        requestBody.put("zipCode", "6825 MC");
        requestBody.put("city", "Arnhem");
        requestBody.put("kvknumber", "test");
        requestBody.put("bicnumber", "test");
        requestBody.put("ibannumber", "test");
        requestBody.put("btwnumber", "test");
        requestBody.put("website", "http://www.avisi.nl");

        //Add the JSON to the request
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

        //Invoking the API
        Map<String, Object> apiResponse =
                restTemplate.postForObject("http://localhost:8080/senders/", httpEntity, Map.class, Collections.EMPTY_MAP);


        //Asserting the response of the API.
        String message = apiResponse.get("message").toString();
        assertEquals("Sender created successfully", message);
        String senderId = ((Map<String, Object>) apiResponse.get("sender")).get("id").toString();
        assertNotNull(apiResponse);

        //Fetching the type details directly from the DB to verify the API succeeded
        Sender senderFromDb = senderService.findOne(senderId);
        assertEquals("avisi5", senderFromDb.getName());

        //Delete the data added for testing
        senderService.delete(senderId);
    }

    @Test
    public void deleteSender() {
        //Create a new product using the CompanyService API
        Sender sender = new Sender(
                "avisiTest",
                "email@email.nl",
                "Meander 251",
                "6825 MC",
                "Arnhem",
                "test",
                "test",
                "test",
                "test",
                "http://www.avisi.nl"
        );
        senderService.save(sender);
        String senderId = sender.getId();


        //Now Invoke the API to delete the saleItem
        restTemplate.delete("http://localhost:8080/senders/" + senderId, Collections.EMPTY_MAP);

        //Try to fetch from the DB directly
        Sender senderFromDB = senderService.findOne(senderId);
        //and assert that there is no data found
        assertNull(senderFromDB);
    }

    @Test
    public void showAllSender() {
        //Create new products using the CompanyService API
        Sender sender1 = new Sender(
                "avisiTest1",
                "test@test.nl",
                "Meander 251",
                "6825 MC",
                "Arnhem",
                "test",
                "test",
                "test",
                "test",
                "http://www.avisi.nl"
        );
        senderService.save(sender1);
        String sender1Id = sender1.getId();
        Sender sender2 = new Sender(
                "avisiTest1",
                "test@test.nl",
                "Meander 251",
                "6825 MC",
                "Arnhem",
                "test",
                "test",
                "test",
                "test",
                "http://www.avisi.nl"
        );
        senderService.save(sender2);
        String sender2Id = sender1.getId();

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/senders/", Map.class);

        //Assert the response from the API
        int totalSenders = Integer.parseInt(apiResponse.get("totalSenders").toString());

        //      Because these tests don't run in order or because there could already be data in the database there's a check if the database is bigger than the 2 added companies
        assertTrue(totalSenders >= 2);

        List<Map<String, Object>> senders = (List<Map<String, Object>>) apiResponse.get("senders");
        assertTrue(senders.size() >= 2);

        //Delete the test data created
        senderService.delete(sender1Id);
        senderService.delete(sender2Id);
    }

    @Test
    public void showSender() {
        //Create a new product using the CompanyService API
        Sender sender = new Sender(
                "avisiTest1",
                "test@test.nl",
                "Meander 251",
                "6825 MC",
                "Arnhem",
                "test",
                "test",
                "test",
                "test",
                "http://www.avisi.nl"
        );
        senderService.save(sender);
        String senderId = sender.getId();

        //      Makes a call to the API to get a company
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/senders/" + senderId, Map.class);

        //      Checks if a tax is returned
        assertNotNull(apiResponse.get("sender"));
        System.out.println(apiResponse.get("sender"));

//      Delete the tax
        senderService.delete(senderId);
    }
}
