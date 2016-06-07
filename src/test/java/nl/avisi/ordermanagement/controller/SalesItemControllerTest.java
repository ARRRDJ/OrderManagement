package nl.avisi.ordermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.SalesItem;
import nl.avisi.ordermanagement.domain.Tax;
import nl.avisi.ordermanagement.domain.Type;
import nl.avisi.ordermanagement.service.salesItem.SalesItemService;
import nl.avisi.ordermanagement.service.tax.TaxService;
import nl.avisi.ordermanagement.service.type.TypeService;
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
 * Created by robert on 5/11/16.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(OrderManagement.class)
@WebIntegrationTest
public class SalesItemControllerTest extends TestCase {
    //Required to Generate JSON content from Java objects
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //Required to delete the data added for tests.
    //Directly invoke the APIs interacting with the DB
    @Autowired
    private SalesItemService salesItemService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TaxService taxService;

    //Test RestTemplate to invoke the APIs.
    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testCreateSalesItem() throws Exception {
        Type type = new Type("Goed Type");
        Type savedType = typeService.save(type);
        Tax tax = new Tax(5.5);
        Tax savedTax = taxService.save(tax);

        //Building the JSON String
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("name", "SalesItem");
        requestBody.put("description", "Een mooi sales item");
        requestBody.put("price", 55.50);
        requestBody.put("sku", "abc1234");
        requestBody.put("typeId", savedType.getId());
        requestBody.put("taxId", savedTax.getId());

        //Add the JSON to the request
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

        //Invoking the API
        Map<String, Object> apiResponse =
                restTemplate.postForObject("http://localhost:8080/salesItems/", httpEntity, Map.class, Collections.EMPTY_MAP);


        //Asserting the response of the API.
        String message = apiResponse.get("message").toString();
        assertEquals("SalesItem created successfully", message);
        String salesItemID = ((Map<String, Object>) apiResponse.get("salesItem")).get("id").toString();
        assertNotNull(apiResponse);

        //Fetching the sales item details directly from the DB to verify the API succeeded
        SalesItem salesItemFromDB = salesItemService.findOne(salesItemID);
        assertEquals("SalesItem", salesItemFromDB.getName());
        assertEquals("Een mooi sales item", salesItemFromDB.getDescription());
        assertEquals(55.50, salesItemFromDB.getPrice());
        assertEquals("abc1234", salesItemFromDB.getSku());
        assertEquals(savedType.getId(), salesItemFromDB.getType().getId());
        assertEquals(savedTax.getId(), salesItemFromDB.getTax().getId());

        //Delete the data added for testing
        salesItemService.delete(salesItemID);
        typeService.delete(savedType.getId());
        taxService.delete(savedTax.getId());
    }

    @Test
    public void testDeleteSalesItem() {
        Type type = new Type("Goed Type");
        Type savedType = typeService.save(type);
        Tax tax = new Tax(5.5);
        Tax savedTax = taxService.save(tax);

        //Create a new sales item using the SalesItemService API
        SalesItem salesItem = new SalesItem("Naam", "Description", 2.50, "SKU", type, tax);
        salesItemService.save(salesItem);

        String salesItemID = salesItem.getId();

        //Now Invoke the API to delete the sales item
        restTemplate.delete("http://localhost:8080/salesItems/" + salesItemID, Collections.EMPTY_MAP);

        //Try to fetch from the DB directly
        SalesItem salesItemFromDB = salesItemService.findOne(salesItemID);
        //and assert that there is no data found
        assertNull(salesItemFromDB);

        typeService.delete(savedType.getId());
        taxService.delete(savedTax.getId());
    }

    //
    @Test
    public void testUpdateSalesItem() throws Exception {
        Type type = new Type("Goed Type");
        Type savedType = typeService.save(type);
        Tax tax = new Tax(5.5);
        Tax savedTax = taxService.save(tax);

        //Create a new sales item using the SalesItemService API
        SalesItem salesItem = new SalesItem("Naam", "Description", 2.50, "SKU", type, tax);
        salesItemService.save(salesItem);

        String salesItemID = salesItem.getId();

        //Building the JSON String for the update
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("name", "SalesItem");
        requestBody.put("description", "Een mooi sales item");
        requestBody.put("price", 55.50);
        requestBody.put("sku", "abc1234");
        requestBody.put("typeId", savedType.getId());
        requestBody.put("taxId", savedTax.getId());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

        //Invoking the API
        Map<String, Object> apiResponse = (Map) restTemplate.exchange("http://localhost:8080/salesItems/" + salesItemID,
                HttpMethod.PUT, httpEntity, Map.class, Collections.EMPTY_MAP).getBody();

        assertNotNull(apiResponse);
        assertTrue(!apiResponse.isEmpty());

        //Asserting the response of the API.
        String message = apiResponse.get("message").toString();
        assertEquals("SalesItem updated successfully", message);

        //Fetching the sales item details directly from the DB to verify the API succeeded in updating the sales item details
        SalesItem salesItemFromDB = salesItemService.findOne(salesItemID);
        assertEquals(requestBody.get("name"), salesItemFromDB.getName());
        assertEquals(requestBody.get("description"), salesItemFromDB.getDescription());
        assertEquals(requestBody.get("price"), salesItemFromDB.getPrice());
        assertEquals(requestBody.get("sku"), salesItemFromDB.getSku());
        assertEquals(requestBody.get("typeId"), salesItemFromDB.getType().getId());
        assertEquals(requestBody.get("taxId"), salesItemFromDB.getTax().getId());

        //Delete the data added for testing
        salesItemService.delete(salesItemID);
        typeService.delete(savedType.getId());
        taxService.delete(savedTax.getId());
    }

    //
    @Test
    public void testShowAllSalesItems() throws Exception {
        //Add some test data for the API
        Type type1 = new Type("Goed Type");
        Type savedType1 = typeService.save(type1);
        Tax tax1 = new Tax(5.5);
        Tax savedTax1 = taxService.save(tax1);

        SalesItem salesItem = new SalesItem("Naam", "Description", 2.50, "SKU", type1, tax1);
        salesItemService.save(salesItem);

        String salesItemID1 = salesItem.getId();

        Type type2 = new Type("Goed Type");
        Type savedType2 = typeService.save(type2);
        Tax tax2 = new Tax(5.5);
        Tax savedTax2 = taxService.save(tax2);

        SalesItem salesItem2 = new SalesItem("Naam2", "Description2", 2.75, "SKU2", type2, tax2);
        salesItemService.save(salesItem2);

        String salesItemID2 = salesItem2.getId();

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/salesItems/", Map.class);

//      Get the added sales items to delete the contact persons
        SalesItem salesItemFromDB1 = salesItemService.findOne(salesItemID1);
        SalesItem salesItemFromDB2 = salesItemService.findOne(salesItemID2);

        //Assert the response from the API
        int totalSalesItems = Integer.parseInt(apiResponse.get("totalSalesItems").toString());

//      Because these tests don't run in order or because there could already be data in the database there's a check if the database is bigger than the 2 added sales items
        assertTrue(totalSalesItems >= 2);

        List<Map<String, Object>> salesItems = (List<Map<String, Object>>) apiResponse.get("salesItems");
        assertTrue(salesItems.size() >= 2);

        //Delete the test data created
        salesItemService.delete(salesItemID1);
        salesItemService.delete(salesItemID2);
        typeService.delete(savedType1.getId());
        typeService.delete(savedType2.getId());
        taxService.delete(savedTax1.getId());
        taxService.delete(savedTax2.getId());
    }

    //
    @Test
    public void testShowSalesItem() throws Exception {
        //Add some test data for the API
        Type type = new Type("Goed Type");
        Type savedType = typeService.save(type);
        Tax tax = new Tax(5.5);
        Tax savedTax = taxService.save(tax);

        SalesItem salesItem = new SalesItem("Naam", "Description", 2.50, "SKU", type, tax);
        salesItemService.save(salesItem);

        String salesItemID = salesItem.getId();

//      Makes a call to the API to get a sales item
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/salesItems/" + salesItemID, Map.class);

//      Checks if a sales item is returned
        assertNotNull(apiResponse.get("salesItem"));
        System.out.println(apiResponse.get("salesItem"));

//      Get the added sales item to delete the contact persons
        SalesItem salesItemFromDB = salesItemService.findOne(salesItemID);

        salesItemService.delete(salesItemID);
        taxService.delete(savedTax.getId());
        typeService.delete(savedType.getId());
    }

    //
    @Test
    public void testGetSalesItemByName() throws Exception {
        //Add some test data for the API
        Type type1 = new Type("Goed Type");
        Type savedType1 = typeService.save(type1);
        Tax tax1 = new Tax(5.5);
        Tax savedTax1 = taxService.save(tax1);

        SalesItem salesItem1 = new SalesItem("Naam", "Description", 2.50, "SKU", type1, tax1);
        salesItemService.save(salesItem1);

        String salesItemID1 = salesItem1.getId();

        Type type2 = new Type("Goed Type");
        Type savedType2 = typeService.save(type2);
        Tax tax2 = new Tax(5.5);
        Tax savedTax2 = taxService.save(tax2);

        SalesItem salesItem2 = new SalesItem("Naam2", "Description2", 22.50, "SKU2", type2, tax2);
        salesItemService.save(salesItem2);

        String salesItemID2 = salesItem2.getId();

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/salesItems/search/nameOrDescription/" + salesItem1.getName(), Map.class);

        //Assert the response from the API
        int totalSalesItems = Integer.parseInt(apiResponse.get("totalSalesItems").toString());
        assertTrue(totalSalesItems == 1);

        List<Map<String, Object>> salesItems = (List<Map<String, Object>>) apiResponse.get("salesItems");
        assertTrue(salesItems.size() == 1);

        //Delete the test data created
        salesItemService.delete(salesItemID1);
        salesItemService.delete(salesItemID2);
        typeService.delete(type1.getId());
        typeService.delete(type2.getId());
        taxService.delete(tax1.getId());
        taxService.delete(tax2.getId());
    }
}
