package nl.avisi.ordermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.*;
import nl.avisi.ordermanagement.service.contactPerson.ContactPersonService;
import nl.avisi.ordermanagement.service.customer.CompanyService;
import nl.avisi.ordermanagement.service.invoice.InvoiceService;
import nl.avisi.ordermanagement.service.order.OrderService;
import nl.avisi.ordermanagement.service.salesItem.SalesItemService;
import nl.avisi.ordermanagement.service.sender.SenderService;
import nl.avisi.ordermanagement.service.tax.TaxService;
import nl.avisi.ordermanagement.service.type.TypeService;
import org.json.JSONArray;
import org.json.JSONObject;
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

import java.util.*;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = OrderManagement.class)
@WebIntegrationTest
public class OrderControllerTest extends TestCase {
    //Required to Generate JSON content from Java objects
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //Required to delete the data added for tests.
    //Directly invoke the APIs interacting with the DB
    @Autowired
    private OrderService orderService;

    //Test RestTemplate to invoke the APIs.
    private RestTemplate restTemplate = new TestRestTemplate();

//    @Test
//    public void createOrder() throws Exception {
//        //Add some test data for the API
//        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
//        ContactPerson contactPerson = new ContactPerson("Sam", "Mas", "mijn@mail.nl", "072747474");
//        contactPersonList.add(contactPerson);
//
//        Company company1 = new Company("Top Bedrijf", "mail@mail.nl", "Stad", "8182OP", "Straat 789", contactPersonList, "POSTBUS", "2ou349837");
//        companyService.save(company1);
//        String companyId = company1.getId();
//        String contactPersonId = contactPerson.getId();
//
//        Sender sender = new Sender("avisiTest", "email@email.nl", "Meander 251", "6825 MC", "Arnhem", "test", "test", "test", "test", "http://www.avisi.nl");
//        senderService.save(sender);
//        String senderId = sender.getId();
//
//        Type type = new Type("Goed Type");
//        Type savedType = typeService.save(type);
//        Tax tax = new Tax(5.5);
//        Tax savedTax = taxService.save(tax);
//
//        //Create a new sales item using the CompanyService API
//        SalesItem salesItem = new SalesItem("Naam", "Description", 2.50, "SKU", savedType, savedTax);
//        salesItemService.save(salesItem);
//        String salesItemId = salesItem.getId();
//
//        JSONArray array = new JSONArray();
//        JSONObject item = new JSONObject();
//        item.put("salesItemId", salesItemId);
//        item.put("quantity", 3);
//        array.put(item);
//
//        String string = "[{\"quantity\":3,\"salesItemId\":\"575698a4e188d40d6292d4e9\"}]";
//        JSONArray jsonArray = new JSONArray(string);
//
//        System.out.println("||||||||||||||||||||||||||||||||||||||||");
//        System.out.println(array);
//
//
//        //Building the JSON String
//        Map<String, Object> requestBody = new HashMap<String, Object>();
//        requestBody.put("number", "1");
//        requestBody.put("period", "Monthly");
//        requestBody.put("startDate", "2016-06-28");
//        requestBody.put("endDate", "2017-06-28");
//        requestBody.put("invoiceEmail", "test@test.nl");
//        requestBody.put("payDays", 30);
//        requestBody.put("companyId", companyId);
//        requestBody.put("contactPersonId", contactPersonId);
//        requestBody.put("senderId", senderId);
//        requestBody.put("orderLines", array);
//        requestBody.put("message", "Message");
//        requestBody.put("identification", "12");
//
//        //Add the JSON to the request
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        //Creating http entity object with request body and headers
//        HttpEntity<String> httpEntity =
//                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);
//
//        //Invoking the API
//        Map<String, Object> apiResponse =
//                restTemplate.postForObject("http://localhost:8080/orders/", httpEntity, Map.class, Collections.EMPTY_MAP);
//
//        //Asserting the response of the API.
//        String message = apiResponse.get("message").toString();
//        assertEquals("Order created successfully", message);
//        String orderId = ((Map<String, Object>) apiResponse.get("Order")).get("id").toString();
//        assertNotNull(apiResponse);
//
//        //Fetching the type details directly from the DB to verify the API succeeded
//        Order orderFromDb = orderService.findOne(orderId);
//        assertEquals("1", orderFromDb.getNumber());
//
//        //Delete the data added for testing
//        orderService.delete(orderId);
//    }

    @Test
    public void testDeleteOrder() {
        ContactPerson contactPerson = new ContactPerson("Firstname", "Lastname", "e@mail.com", "06-1234567");
        contactPerson.setId("id");
        List<ContactPerson> cpList = new ArrayList<>();
        cpList.add(contactPerson);
        Company company = new Company("Name", "invoice@mail.com", "City", "Zipcode", "Adress", cpList, "MailBox", "Number");
        company.setId("id");
        Sender sender = new Sender("Name", "e@mail.com", "Address", "Zipcode", "City", "KVKNumber", "BICNumber", "IBANNumber", "BTWNumber", "Website");
        sender.setId("id");
        Type type = new Type("Type");
        type.setId("id");
        Tax tax = new Tax(5.5);
        tax.setId("id");
        SalesItem salesItem = new SalesItem("Name", "Description", 5.5, "SKU", type, tax);
        salesItem.setId("id");
        OrderLine orderLine = new OrderLine(5, salesItem);
        orderLine.setId("id");
        List<OrderLine> odlList = new ArrayList<>();
        odlList.add(orderLine);
        Invoice invoice = new Invoice("number", new Date(), new Date(), new Date());
        invoice.setId("id");
        List<Invoice> inList = new ArrayList<>();
        inList.add(invoice);

        Order order = new Order("1", true, new Date(), new Date(), "MONTHLY", "invoice@mail.nl", 30, company, contactPerson, sender, "message", "identification");
        order.setOrderLines(odlList);
        order.setInvoices(inList);
        order.setId("testID");
        orderService.save(order);

        String orderID = order.getId();

        //Now Invoke the API to delete the company
        restTemplate.delete("http://localhost:8080/orders/" + orderID, Collections.EMPTY_MAP);

        //Try to fetch from the DB directly
        Order orderFromDB = orderService.findOne(orderID);
        //and assert that there is no data found
        assertFalse(orderFromDB.isActive());

        orderService.delete(orderID);
    }

    @Test
    public void testShowOrder() throws Exception {
        ContactPerson contactPerson = new ContactPerson("Firstname", "Lastname", "e@mail.com", "06-1234567");
        contactPerson.setId("id");
        List<ContactPerson> cpList = new ArrayList<>();
        cpList.add(contactPerson);
        Company company = new Company("Name", "invoice@mail.com", "City", "Zipcode", "Adress", cpList, "MailBox", "Number");
        company.setId("id");
        Sender sender = new Sender("Name", "e@mail.com", "Address", "Zipcode", "City", "KVKNumber", "BICNumber", "IBANNumber", "BTWNumber", "Website");
        sender.setId("id");
        Type type = new Type("Type");
        type.setId("id");
        Tax tax = new Tax(5.5);
        tax.setId("id");
        SalesItem salesItem = new SalesItem("Name", "Description", 5.5, "SKU", type, tax);
        salesItem.setId("id");
        OrderLine orderLine = new OrderLine(5, salesItem);
        orderLine.setId("id");
        List<OrderLine> odlList = new ArrayList<>();
        odlList.add(orderLine);
        Invoice invoice = new Invoice("number", new Date(), new Date(), new Date());
        invoice.setId("id");
        List<Invoice> inList = new ArrayList<>();
        inList.add(invoice);

        Order order = new Order("1", true, new Date(), new Date(), "MONTHLY", "invoice@mail.nl", 30, company, contactPerson, sender, "message", "identification");
        order.setOrderLines(odlList);
        order.setInvoices(inList);
        order.setId("testID");
        orderService.save(order);

        String orderID = order.getId();

//      Makes a call to the API to get a company
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/orders/" + orderID, Map.class);

//      Checks if a company is returned
        assertNotNull(apiResponse.get("order"));
        System.out.println(apiResponse.get("order"));

        orderService.delete(orderID);
    }

    @Test
    public void testShowAllOrders() throws Exception {
        ContactPerson contactPerson = new ContactPerson("Firstname", "Lastname", "e@mail.com", "06-1234567");
        contactPerson.setId("id");
        List<ContactPerson> cpList = new ArrayList<>();
        cpList.add(contactPerson);
        Company company = new Company("Name", "invoice@mail.com", "City", "Zipcode", "Adress", cpList, "MailBox", "Number");
        company.setId("id");
        Sender sender = new Sender("Name", "e@mail.com", "Address", "Zipcode", "City", "KVKNumber", "BICNumber", "IBANNumber", "BTWNumber", "Website");
        sender.setId("id");
        Type type = new Type("Type");
        type.setId("id");
        Tax tax = new Tax(5.5);
        tax.setId("id");
        SalesItem salesItem = new SalesItem("Name", "Description", 5.5, "SKU", type, tax);
        salesItem.setId("id");
        OrderLine orderLine = new OrderLine(5, salesItem);
        orderLine.setId("id");
        List<OrderLine> odlList = new ArrayList<>();
        odlList.add(orderLine);
        Invoice invoice = new Invoice("number", new Date(), new Date(), new Date());
        invoice.setId("id");
        List<Invoice> inList = new ArrayList<>();
        inList.add(invoice);

        Order order = new Order("1", true, new Date(), new Date(), "MONTHLY", "invoice@mail.nl", 30, company, contactPerson, sender, "message", "identification");
        order.setOrderLines(odlList);
        order.setInvoices(inList);
        order.setId("testID");
        orderService.save(order);
        String orderID = order.getId();

        Order order2 = new Order("2", true, new Date(), new Date(), "MONTHLY", "invoice@mail.nl", 30, company, contactPerson, sender, "message", "identification");
        order.setOrderLines(odlList);
        order.setInvoices(inList);
        order.setId("testID2");
        orderService.save(order);
        String orderID2 = order.getId();

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/orders/", Map.class);

        //Assert the response from the API
        int totalCompanies = Integer.parseInt(apiResponse.get("totalOrders").toString());

//      Because these tests don't run in order or because there could already be data in the database there's a check if the database is bigger than the 2 added companies
        assertTrue(totalCompanies >= 2);

        List<Map<String, Object>> companies = (List<Map<String, Object>>) apiResponse.get("orders");
        assertTrue(companies.size() >= 2);

        //Delete the test data created
        orderService.delete(orderID);
        orderService.delete(orderID2);
    }

    @Test
    public void testGetOrderByInvoiceEmail() throws Exception {
        ContactPerson contactPerson = new ContactPerson("Firstname", "Lastname", "e@mail.com", "06-1234567");
        contactPerson.setId("id");
        List<ContactPerson> cpList = new ArrayList<>();
        cpList.add(contactPerson);
        Company company = new Company("Name", "invoice@mail.com", "City", "Zipcode", "Adress", cpList, "MailBox", "Number");
        company.setId("id");
        Sender sender = new Sender("Name", "e@mail.com", "Address", "Zipcode", "City", "KVKNumber", "BICNumber", "IBANNumber", "BTWNumber", "Website");
        sender.setId("id");
        Type type = new Type("Type");
        type.setId("id");
        Tax tax = new Tax(5.5);
        tax.setId("id");
        SalesItem salesItem = new SalesItem("Name", "Description", 5.5, "SKU", type, tax);
        salesItem.setId("id");
        OrderLine orderLine = new OrderLine(5, salesItem);
        orderLine.setId("id");
        List<OrderLine> odlList = new ArrayList<>();
        odlList.add(orderLine);
        Invoice invoice = new Invoice("number", new Date(), new Date(), new Date());
        invoice.setId("id");
        List<Invoice> inList = new ArrayList<>();
        inList.add(invoice);

        Order order = new Order("1", true, new Date(), new Date(), "MONTHLY", "email@email.nl", 30, company, contactPerson, sender, "message", "identification");
        order.setOrderLines(odlList);
        order.setInvoices(inList);
        order.setId("testID");
        orderService.save(order);
        String orderID = order.getId();

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/orders/search/invoiceEmail/" + order.getInvoiceEmail(), Map.class);

        //Assert the response from the API
        int totalCompanies = Integer.parseInt(apiResponse.get("totalOrders").toString());
        assertTrue(totalCompanies == 1);

        List<Map<String, Object>> companies = (List<Map<String, Object>>) apiResponse.get("orders");
        assertTrue(companies.size() == 1);

        //Delete the test data created
        orderService.delete(orderID);
    }

    @Test
    public void testGetOrderByNumber() throws Exception {
        ContactPerson contactPerson = new ContactPerson("Firstname", "Lastname", "e@mail.com", "06-1234567");
        contactPerson.setId("id");
        List<ContactPerson> cpList = new ArrayList<>();
        cpList.add(contactPerson);
        Company company = new Company("Name", "invoice@mail.com", "City", "Zipcode", "Adress", cpList, "MailBox", "Number");
        company.setId("id");
        Sender sender = new Sender("Name", "e@mail.com", "Address", "Zipcode", "City", "KVKNumber", "BICNumber", "IBANNumber", "BTWNumber", "Website");
        sender.setId("id");
        Type type = new Type("Type");
        type.setId("id");
        Tax tax = new Tax(5.5);
        tax.setId("id");
        SalesItem salesItem = new SalesItem("Name", "Description", 5.5, "SKU", type, tax);
        salesItem.setId("id");
        OrderLine orderLine = new OrderLine(5, salesItem);
        orderLine.setId("id");
        List<OrderLine> odlList = new ArrayList<>();
        odlList.add(orderLine);
        Invoice invoice = new Invoice("number", new Date(), new Date(), new Date());
        invoice.setId("id");
        List<Invoice> inList = new ArrayList<>();
        inList.add(invoice);

        Order order = new Order("5543", true, new Date(), new Date(), "MONTHLY", "invoice@mail.nl", 30, company, contactPerson, sender, "message", "identification");
        order.setOrderLines(odlList);
        order.setInvoices(inList);
        order.setId("testID");
        orderService.save(order);
        String orderID = order.getId();

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/orders/search/number/" + order.getNumber(), Map.class);

        //Assert the response from the API
        int totalCompanies = Integer.parseInt(apiResponse.get("totalOrders").toString());
        assertTrue(totalCompanies == 1);

        List<Map<String, Object>> companies = (List<Map<String, Object>>) apiResponse.get("orders");
        assertTrue(companies.size() == 1);

        //Delete the test data created
        orderService.delete(orderID);
    }

}


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
