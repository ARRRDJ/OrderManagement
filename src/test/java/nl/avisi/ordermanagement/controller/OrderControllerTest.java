//package nl.avisi.ordermanagement.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import junit.framework.TestCase;
//import nl.avisi.ordermanagement.App;
//import nl.avisi.ordermanagement.service.order.OrderService;
//import nl.avisi.ordermanagement.service.salesItem.SalesItemService;
//import nl.avisi.ordermanagement.service.type.TypeService;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.TestRestTemplate;
//import org.springframework.boot.test.WebIntegrationTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.web.client.RestTemplate;
//
///**
// * Created by Luuk on 18-5-2016.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(App.class)
//@WebIntegrationTest
//public class OrderControllerTest extends TestCase {
//    //Required to Generate JSON content from Java objects
//    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
//
//    //Required to delete the data added for tests.
//    //Directly invoke the APIs interacting with the DB
//    @Autowired
//    private OrderService orderService;
//    //    @Autowired
////    private OrderLineService orderLineService;
//    @Autowired
//    private SalesItemService productService;
//    @Autowired
//    private TypeService typeService;
//
//    //Test RestTemplate to invoke the APIs.
//    private RestTemplate restTemplate = new TestRestTemplate();
//
//    @Before
//    public void setUp() {
//        productService.deleteAll();
//        orderService.deleteAll();
//    }
//
////    @Test
////    public void addOrder() throws Exception {
////        Period period = new Period("testPeriod");
////        periodService.savePeriod(period);
////        String periodId = period.getId();
////
////
//////        "name": "Order 3",
//////                "periodId": "573b09ed8d222e1c09b72b8c",
//////                "orderLines": [
//////        {"id": "573b06418d22ae271bdd5de0", "quantity": 2},
//////        {"id": "573b06418d22ae271bdd5de0", "quantity": 2},
//////        {"id": "573b06418d22ae271bdd5de0", "quantity": 1}
//////        ]
////
////
////        //Building the JSON String
////        Map<String, Object> requestBody = new HashMap<String, Object>();
////        requestBody.put("name", "Order 1");
////        requestBody.put("periodId", periodId);
//////        requestBody.put("orderLines", [{"id", }]);
////        requestBody.put("taxes", 21.0);
////        requestBody.put("sku", "56s565562d");
////        requestBody.put("typeId", typeId);
////
//////        Add the JSON to the request
////        HttpHeaders requestHeaders = new HttpHeaders();
////        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
////
////        //Creating http entity object with request body and headers
////        HttpEntity<String> httpEntity =
////                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);
////
////        //Invoking the API
////        Map<String, Object> apiResponse =
////                restTemplate.postForObject("http://localhost:8080/products/add/", httpEntity, Map.class, Collections.EMPTY_MAP);
////
////
////        //Asserting the response of the API.
////        String message = apiResponse.get("message").toString();
////        assertEquals("Product created successfully", message);
////        String productId = ((Map<String, Object>) apiResponse.get("product")).get("id").toString();
////        assertNotNull(apiResponse);
////
////        //Fetching the Product details directly from the DB to verify the API succeeded
////        Product productFromDb = productService.findOne(productId);
////        assertEquals("Product 1", productFromDb.getName());
////        assertEquals("QWER1234", productFromDb.getDescription());
////        assertEquals(20.95, productFromDb.getPrice());
////        assertEquals(21.0, productFromDb.getTaxes());
////        assertEquals("56s565562d", productFromDb.getSku());
////        assertEquals(typeId, productFromDb.getType().getId());
////
////        //Delete the data added for testing
////        productService.delete(productId);
////
////    }
//
//}
