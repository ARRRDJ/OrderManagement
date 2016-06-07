package nl.avisi.ordermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import nl.avisi.ordermanagement.domain.Type;
import nl.avisi.ordermanagement.service.type.TypeService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by robert on 5/11/16.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = OrderManagement.class)
@WebIntegrationTest
public class TypeControllerTest extends TestCase {
    //Required to Generate JSON content from Java objects
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //Required to delete the data added for tests.
    //Directly invoke the APIs interacting with the DB
    @Autowired
    private TypeService typeService;

    //Test RestTemplate to invoke the APIs.
    private RestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setUp() {
//        typeService.deleteAll();
    }

    @Test
    public void testCreateType() throws Exception {
        //Building the JSON String
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("name", "Type 1");

        //Add the JSON to the request
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

        //Invoking the API
        Map<String, Object> apiResponse =
                restTemplate.postForObject("http://localhost:8080/types/", httpEntity, Map.class, Collections.EMPTY_MAP);


        //Asserting the response of the API.
        String message = apiResponse.get("message").toString();
        assertEquals("Type created successfully", message);
        String typeId = ((Map<String, Object>) apiResponse.get("type")).get("id").toString();
        assertNotNull(apiResponse);

        //Fetching the type details directly from the DB to verify the API succeeded
        Type typeFromDb = typeService.findOne(typeId);
        assertEquals("Type 1", typeFromDb.getName());

        //Delete the data added for testing
        typeService.delete(typeId);

    }

    @Test
    public void testDeleteType() {
        //Create a new type using the TypeService API
        Type type = new Type("Type 2");
        typeService.save(type);
        String typeId = type.getId();

        //Now Invoke the API to delete the type
        restTemplate.delete("http://localhost:8080/types/" + typeId, Collections.EMPTY_MAP);

        //Try to fetch from the DB directly
        Type typeFromDb = typeService.findOne(typeId);
        //and assert that there is no data found
        assertNull(typeFromDb);
    }

    @Test
    public void testShowAllTypes() throws Exception {
        //Add some test data for the API
        Type type1 = new Type("testType 1");
        typeService.save(type1);
        String type1Id = type1.getId();

        Type type2 = new Type("testType 2");
        typeService.save(type2);
        String type2Id = type2.getId();


        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/types/", Map.class);

        //Assert the response from the API
        int totalTypes = Integer.parseInt(apiResponse.get("totalTypes").toString());
        //Because these tests don't run in order or because there could already be data in the database there's a check if the database is bigger than the 2 added companies

        assertTrue(totalTypes >= 2);

        List<Map<String, Object>> types = (List<Map<String, Object>>) apiResponse.get("types");
        assertTrue(types.size() >= 2);

        //Delete the test data created
        typeService.delete(type1.getId());
        typeService.delete(type2.getId());
    }

    @Test
    public void testShowType() throws Exception {
        //Create a new type using the typeService API
        Type type = new Type("testType");
        typeService.save(type);
        String typeId = type.getId();


        //Now make a call to the API to get details of the types
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/types/" + typeId, Map.class);

//      Checks if a tax is returned
        assertNotNull(apiResponse.get("type"));
        System.out.println(apiResponse.get("type"));

//      Delete the tax
        typeService.delete(typeId);
    }

    @Test
    public void testUpdateType() throws Exception {
        //Create a new type using the TypeService API
        Type type = new Type("Type1");
        typeService.save(type);

        String typeId = type.getId();

        //Now create Request body with the updated Book Data.
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("name", "Type2");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

        //Invoking the API
        Map<String, Object> apiResponse = (Map) restTemplate.exchange("http://localhost:8080/types/" + typeId,
                HttpMethod.PUT, httpEntity, Map.class, Collections.EMPTY_MAP).getBody();

        assertNotNull(apiResponse);
        assertTrue(!apiResponse.isEmpty());

        //Asserting the response of the API.
        String message = apiResponse.get("message").toString();
        assertEquals("Type Updated successfully", message);

        //Fetching the type details directly from the DB to verify the API succeeded in updating the type details
        Type typeFromDb = typeService.findOne(typeId);
        assertEquals(requestBody.get("name"), typeFromDb.getName());

        //Delete the data added for testing
        typeService.delete(typeId);
    }
}
