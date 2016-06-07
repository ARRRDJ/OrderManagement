package nl.avisi.ordermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.Company;
import nl.avisi.ordermanagement.domain.ContactPerson;
import nl.avisi.ordermanagement.service.contactPerson.ContactPersonService;
import nl.avisi.ordermanagement.service.customer.CompanyService;
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

import java.util.*;

/**
 * Created by robert on 5/11/16.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(OrderManagement.class)
@WebIntegrationTest
public class CompanyControllerTest extends TestCase {
    //Required to Generate JSON content from Java objects
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //Required to delete the data added for tests.
    //Directly invoke the APIs interacting with the DB
    @Autowired
    private CompanyService companyService;

    @Autowired
    private ContactPersonService contactPersonService;

    //Test RestTemplate to invoke the APIs.
    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testCreateCompany() throws Exception {
        ContactPerson contactPerson = new ContactPerson("C.", "Ontact", "me@mail.nl", "06757575");
        ContactPerson contactPerson2 = new ContactPerson("C2.", "Ontact2", "me2@mail.nl", "064738383");
        ContactPerson[] contactPersonsArray = new ContactPerson[]{contactPerson, contactPerson2};

        //Building the JSON String
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("name", "Een Bedrijf");
        requestBody.put("email", "e@mail.nl");
        requestBody.put("city", "Arnhem");
        requestBody.put("telephoneNumber", "068585588");
        requestBody.put("zipcode", "3575YU");
        requestBody.put("address", "Straat 123");
        requestBody.put("mailBox", "Mailbox");
        requestBody.put("number", "50");
        requestBody.put("contactPersons", contactPersonsArray);

        //Add the JSON to the request
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

        //Invoking the API
        Map<String, Object> apiResponse =
                restTemplate.postForObject("http://localhost:8080/companies/", httpEntity, Map.class, Collections.EMPTY_MAP);


        //Asserting the response of the API.
        String message = apiResponse.get("message").toString();
        assertEquals("Company created successfully", message);
        String companyID = ((Map<String, Object>) apiResponse.get("company")).get("id").toString();
        assertNotNull(apiResponse);

        //Fetching the company details directly from the DB to verify the API succeeded
        Company companyFromDB = companyService.findOne(companyID);
        assertEquals("Een Bedrijf", companyFromDB.getName());
        assertEquals("e@mail.nl", companyFromDB.getInvoiceEmail());
        assertEquals("Arnhem", companyFromDB.getCity());
        assertEquals("3575YU", companyFromDB.getZipcode());
        assertEquals("Straat 123", companyFromDB.getAddress());
        assertEquals("Mailbox", companyFromDB.getMailBox());
        assertEquals("50", companyFromDB.getNumber());
        assertEquals(contactPerson.getFirstName(), companyFromDB.getContactPersons().get(0).getFirstName());

        //Delete the data added for testing
        companyService.delete(companyID);
        contactPersonService.delete(companyFromDB.getContactPersons().get(0).getId());
        contactPersonService.delete(companyFromDB.getContactPersons().get(1).getId());
    }

    @Test
    public void testDeleteCompany() {
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
        ContactPerson contactPerson = new ContactPerson("P.", "Erson", "em@ail.com", "06383338");
        contactPersonList.add(contactPerson);
//
        //Create a new product using the CompanyService API
        Company company = new Company("Test Bedrijf", "email@mail.nl", "Nijmegen", "8715FG", "Straat 321", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company);

        String companyID = company.getId();

        //Get company to delete the contact person
        Company companyForCP = companyService.findOne(companyID);

        //Now Invoke the API to delete the company
        restTemplate.delete("http://localhost:8080/companies/" + companyID, Collections.EMPTY_MAP);

        //Try to fetch from the DB directly
        Company companyFromDB = companyService.findOne(companyID);
        //and assert that there is no data found
        assertNull(companyFromDB);

        contactPersonService.delete(companyForCP.getContactPersons().get(0).getId());
    }

    @Test
    public void testUpdateCompany() throws Exception {
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
        ContactPerson contactPerson = new ContactPerson("C.", "Ontact", "oodpsowjjjff@mail.nl", "0260505050");
        contactPersonList.add(contactPerson);

        Company company1 = new Company("Goed Bedrijf", "henk@mail.nl", "Maastricht", "1188AW", "Straat 456", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company1);

        //Add update data for the API
        ContactPerson contactPerson3 = new ContactPerson("Koos", "Ontact", "email@adres.nl", "032858585");
        //String contactPerson2ID = contactPerson2.getId();
        ContactPerson[] contactPersonsArray2 = new ContactPerson[]{contactPerson3};

        //Building the JSON String
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("name", "Groot Bedrijf");
        requestBody.put("email", "meel@mail.nl");
        requestBody.put("telephoneNumber", "06748484");
        requestBody.put("city", "Breda");
        requestBody.put("zipcode", "1298ER");
        requestBody.put("address", "Straat 654");
        requestBody.put("mailBox", "POSTBUS");
        requestBody.put("number", "2ou349837");
        requestBody.put("contactPersons", contactPersonsArray2);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

        //Invoking the API
        Map<String, Object> apiResponse = (Map) restTemplate.exchange("http://localhost:8080/companies/" + company1.getId(),
                HttpMethod.PUT, httpEntity, Map.class, Collections.EMPTY_MAP).getBody();

        assertNotNull(apiResponse);
        assertTrue(!apiResponse.isEmpty());

        //Asserting the response of the API.
        String message = apiResponse.get("message").toString();
        assertEquals("Company updated successfully", message);

        //Fetching the company details directly from the DB to verify the API succeeded in updating the company details
        Company companyFromDB = companyService.findOne(company1.getId());
        assertEquals(requestBody.get("name"), companyFromDB.getName());
        assertEquals(requestBody.get("email"), companyFromDB.getInvoiceEmail());
        assertEquals(requestBody.get("city"), companyFromDB.getCity());
        assertEquals(requestBody.get("zipcode"), companyFromDB.getZipcode());
        assertEquals(requestBody.get("address"), companyFromDB.getAddress());
        assertEquals("POSTBUS", companyFromDB.getMailBox());
        assertEquals("2ou349837", companyFromDB.getNumber());
        assertEquals(contactPerson3.getFirstName(), companyFromDB.getContactPersons().get(0).getFirstName());

        //Delete the data added for testing
        companyService.delete(company1.getId());
        contactPersonService.delete(companyFromDB.getContactPersons().get(0).getId());
        List<ContactPerson> contactPersonToDelete = contactPersonService.findByEmail("oodpsowjjjff@mail.nl");
        contactPersonService.delete(contactPersonToDelete.get(0).getId());
    }

    @Test
    public void testShowAllCompanies() throws Exception {
        //Add some test data for the API
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
        ContactPerson contactPerson = new ContactPerson("Sam", "Mas", "mijn@mail.nl", "072747474");
        contactPersonList.add(contactPerson);

        Company company1 = new Company("Top Bedrijf", "mail@mail.nl", "Stad", "8182OP", "Straat 789", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company1);

        Company company2 = new Company("Topper Bedrijf", "em@ail.nl", "Dorp", "9119NE", "Straat 987", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company2);

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/companies/", Map.class);

//      Get the added companies to delete the contact persons
        Company companyFromDB1 = companyService.findOne(company1.getId());
        Company companyFromDB2 = companyService.findOne(company2.getId());

        //Assert the response from the API
        int totalCompanies = Integer.parseInt(apiResponse.get("totalCompanies").toString());

//      Because these tests don't run in order or because there could already be data in the database there's a check if the database is bigger than the 2 added companies
        assertTrue(totalCompanies >= 2);

        List<Map<String, Object>> companies = (List<Map<String, Object>>) apiResponse.get("companies");
        assertTrue(companies.size() >= 2);

        //Delete the test data created
        companyService.delete(company1.getId());
        companyService.delete(company2.getId());

//      Delete contact persons
        contactPersonService.delete(companyFromDB1.getContactPersons().get(0).getId());
        contactPersonService.delete(companyFromDB2.getContactPersons().get(0).getId());
    }

    @Test
    public void testShowCompany() throws Exception {
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
        ContactPerson contactPerson = new ContactPerson("Iets", "Anders", "abc@123.nl", "06758484");
        contactPersonList.add(contactPerson);

        Company company = new Company("Familie Bedrijf", "me@mail.nl", "Utrecht", "0987HS", "Straat 10", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company);
        //Get the ID
        String companyID = company.getId();

//      Makes a call to the API to get a company
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/companies/" + companyID, Map.class);

//      Checks if a company is returned
        assertNotNull(apiResponse.get("company"));
        System.out.println(apiResponse.get("company"));

//      Get the added company to delete the contact persons
        Company companyFromDB = companyService.findOne(company.getId());

        contactPersonService.delete(companyFromDB.getContactPersons().get(0).getId());
        companyService.delete(companyID);
    }

    @Test
    public void testGetCompanyByName() throws Exception {
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
        ContactPerson contactPerson = new ContactPerson("Pieter", "Contact", "pieter@email.com", "0363373");
        contactPersonList.add(contactPerson);

        Company company1 = new Company("Super Bedrijf", "email@adres.nl", "Zwolle", "5463IW", "Straat 11", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company1);

        Company company2 = new Company("Jan's Bedrijf", "mijn@mail.nl", "Amsterdam", "1823GH", "Straat 12", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company2);

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/companies/search/name/" + company1.getName(), Map.class);

        //Assert the response from the API
        int totalCompanies = Integer.parseInt(apiResponse.get("totalCompanies").toString());
        assertTrue(totalCompanies == 1);

        List<Map<String, Object>> companies = (List<Map<String, Object>>) apiResponse.get("companies");
        assertTrue(companies.size() == 1);

//      Get the added company to delete the contact persons
        Company companyFromDB1 = companyService.findOne(company1.getId());
        Company companyFromDB2 = companyService.findOne(company2.getId());

        //Delete the test data created
        companyService.delete(company1.getId());
        companyService.delete(company2.getId());
        contactPersonService.delete(companyFromDB1.getContactPersons().get(0).getId());
        contactPersonService.delete(companyFromDB2.getContactPersons().get(0).getId());
    }

    @Test
    public void testGetCompanyByEmail() throws Exception {
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
        ContactPerson contactPerson = new ContactPerson("Sam", "Contact", "sam@email.com", "065858534");
        contactPersonList.add(contactPerson);

        Company company1 = new Company("Pieter's Bedrijf", "email@adres.nl", "Zwolle", "1839AS", "Straat 18", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company1);

        Company company2 = new Company("Johannas's Bedrijf", "mijn@mail.nl", "Amsterdam", "1234AB", "Straat 19", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company2);

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/companies/search/email/" + company1.getInvoiceEmail(), Map.class);

        //Assert the response from the API
        int totalCompanies = Integer.parseInt(apiResponse.get("totalCompanies").toString());
        assertTrue(totalCompanies == 1);

        List<Map<String, Object>> companies = (List<Map<String, Object>>) apiResponse.get("companies");
        assertTrue(companies.size() == 1);

//      Get the added company to delete the contact persons
        Company companyFromDB1 = companyService.findOne(company1.getId());
        Company companyFromDB2 = companyService.findOne(company2.getId());

        //Delete the test data created
        companyService.delete(company1.getId());
        companyService.delete(company2.getId());
        contactPersonService.delete(companyFromDB1.getContactPersons().get(0).getId());
        contactPersonService.delete(companyFromDB2.getContactPersons().get(0).getId());
    }

    @Test
    public void testGetCompanyByZipCode() throws Exception {
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
        ContactPerson contactPerson = new ContactPerson("Sjors", "Contact", "sjors@email.com", "06748484");
        contactPersonList.add(contactPerson);

        Company company1 = new Company("Tester's Bedrijf", "email@adres.nl", "Zwolle", "1274UE", "Straat 22", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company1);

        Company company2 = new Company("Sam's Bedrijf", "mijn@mail.nl", "Amsterdam", "9283QW", "Straat 23", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company2);

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/companies/search/zipCode/" + company1.getZipcode(), Map.class);

        //Assert the response from the API
        int totalCompanies = Integer.parseInt(apiResponse.get("totalCompanies").toString());
        assertTrue(totalCompanies == 1);

        List<Map<String, Object>> companies = (List<Map<String, Object>>) apiResponse.get("companies");
        assertTrue(companies.size() == 1);

//      Get the added company to delete the contact persons
        Company companyFromDB1 = companyService.findOne(company1.getId());
        Company companyFromDB2 = companyService.findOne(company2.getId());

        //Delete the test data created
        companyService.delete(company1.getId());
        companyService.delete(company2.getId());
        contactPersonService.delete(companyFromDB1.getContactPersons().get(0).getId());
        contactPersonService.delete(companyFromDB2.getContactPersons().get(0).getId());
    }

    @Test
    public void testGetCompanyByCity() throws Exception {
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
        ContactPerson contactPerson = new ContactPerson("Robert", "Contact", "robert@email.com", "03636363");
        contactPersonList.add(contactPerson);

        Company company1 = new Company("Peter's Bedrijf", "email@adres.nl", "Zwolle", "5848FD", "Straat 24", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company1);

        Company company2 = new Company("Heleen's Bedrijf", "mijn@mail.nl", "Amsterdam", "0937AE", "Straat 25", contactPersonList, "POSTBUS", "2ou349837");
        companyService.save(company2);

        //Invoke the API
        Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/companies/search/city/" + company1.getCity(), Map.class);

        //Assert the response from the API
        int totalCompanies = Integer.parseInt(apiResponse.get("totalCompanies").toString());
        assertTrue(totalCompanies == 1);

        List<Map<String, Object>> companies = (List<Map<String, Object>>) apiResponse.get("companies");
        assertTrue(companies.size() == 1);

//      Get the added company to delete the contact persons
        Company companyFromDB1 = companyService.findOne(company1.getId());
        Company companyFromDB2 = companyService.findOne(company2.getId());

        //Delete the test data created
        companyService.delete(company1.getId());
        companyService.delete(company2.getId());
        contactPersonService.delete(companyFromDB1.getContactPersons().get(0).getId());
        contactPersonService.delete(companyFromDB2.getContactPersons().get(0).getId());
    }
}
