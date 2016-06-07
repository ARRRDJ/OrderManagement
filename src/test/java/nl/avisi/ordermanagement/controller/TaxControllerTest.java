package nl.avisi.ordermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.Tax;
import nl.avisi.ordermanagement.service.tax.TaxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Luuk on 2-6-2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(OrderManagement.class)
@WebIntegrationTest
public class TaxControllerTest extends TestCase {
    //Required to Generate JSON content from Java objects
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //Required to delete the data added for tests.
    //Directly invoke the APIs interacting with the DB
    @Autowired
    private TaxService taxService;

    //Test RestTemplate to invoke the APIs.
    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void createTax() throws Exception {
        //Building the JSON String
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("rate", 21.0);

        //Add the JSON to the request
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

        //Invoking the API
        Map<String, Object> apiResponse =
                restTemplate.postForObject("http://localhost:8080/taxes/", httpEntity, Map.class, Collections.EMPTY_MAP);

        //Asserting the response of the API.
        String message = apiResponse.get("message").toString();
        assertEquals("Tax created successfully", message);
        String taxId = ((Map<String, Object>) apiResponse.get("tax")).get("id").toString();
        assertNotNull(apiResponse);

        //Fetching the company details directly from the DB to verify the API succeeded
        Tax taxFromDB = taxService.findOne(taxId);
        assertEquals(21.0, taxFromDB.getRate());

        //Delete the data added for testing
        taxService.delete(taxId);
    }

    @Test
    public void deleteTax() {
        //Create a new product using the TaxService API
        Tax tax = new Tax(21.0);
        taxService.save(tax);
        String taxId = tax.getId();

        //Now Invoke the API to delete the company
        restTemplate.delete("http://localhost:8080/taxes/" + taxId, Collections.EMPTY_MAP);

        //Try to fetch from the DB directly
        Tax taxFromDB = taxService.findOne(taxId);
        //and assert that there is no data found
        assertNull(taxFromDB);
    }

    @Test
    public void updateTax() throws Exception {
        //Create a new product using the TaxService API
        Tax tax = new Tax(21.0);
        taxService.save(tax);
        String taxId = tax.getId();

        //Building the JSON String
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("rate", 22.0);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

        //Invoking the API
        Map<String, Object> apiResponse = (Map) restTemplate.exchange("http://localhost:8080/taxes/" + taxId,
                HttpMethod.PUT, httpEntity, Map.class, Collections.EMPTY_MAP).getBody();

        assertNotNull(apiResponse);
        assertTrue(!apiResponse.isEmpty());

        //Asserting the response of the API.
        String message = apiResponse.get("message").toString();
        assertEquals("Tax updated successfully", message);

        //Fetching the tax details directly from the DB to verify the API succeeded in updating the company details
        Tax taxFromDb = taxService.findOne(taxId);
        assertEquals(requestBody.get("rate"), taxFromDb.getRate());

        //Delete the data added for testing
        taxService.delete(tax.getId());
    }

    @Test
    public void showAllTaxes() throws Exception {
        //Create a new product using the TaxService API
        Tax tax1 = new Tax(21.0);
        taxService.save(tax1);
        String tax1Id = tax1.getId();

        //Create a new product using the TaxService API
        Tax tax2 = new Tax(19.0);
        taxService.save(tax2);
        String tax2Id = tax2.getId();

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/taxes/", Map.class);

        //Assert the response from the API
        int totalTaxes = Integer.parseInt(apiResponse.get("totalTaxes").toString());

//      Because these tests don't run in order or because there could already be data in the database there's a check if the database is bigger than the 2 added companies
        assertTrue(totalTaxes >= 2);

        List<Map<String, Object>> companies = (List<Map<String, Object>>) apiResponse.get("taxes");
        assertTrue(companies.size() >= 2);

        //Delete the test data created
        taxService.delete(tax1Id);
        taxService.delete(tax2Id);
    }

    @Test
    public void showTax() throws Exception {
        //Create a new product using the TaxService API
        Tax tax = new Tax(21.0);
        taxService.save(tax);
        String taxId = tax.getId();

//      Makes a call to the API to get a company
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/taxes/" + taxId, Map.class);

//      Checks if a tax is returned
        assertNotNull(apiResponse.get("tax"));
        System.out.println(apiResponse.get("tax"));

//      Delete the tax
        taxService.delete(taxId);
    }
}
