package nl.avisi.ordermanagement.service;

import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.Company;
import nl.avisi.ordermanagement.domain.ContactPerson;
import nl.avisi.ordermanagement.service.contactPerson.ContactPersonService;
import nl.avisi.ordermanagement.service.customer.CompanyService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Sam on 5/12/16.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(OrderManagement.class)
@WebIntegrationTest
public class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private ContactPersonService contactPersonService;

    private String id = "testid";
    private String contactPersonID;
    private String contactPersonID2;

    @Before
    public void setUp(){
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
        ContactPerson contactPerson = new ContactPerson("Carlo", "Tact", "c@mail.nl", "063838321");
        contactPersonList.add(contactPerson);
        contactPersonService.save(contactPerson);
        contactPersonID = contactPerson.getId();

        List<ContactPerson> contactPersonList2 = new ArrayList<ContactPerson>();
        ContactPerson contactPerson2 = new ContactPerson("Carlos", "Tact", "cs@mail.nl", "067848484");
        contactPersonList2.add(contactPerson2);
        contactPersonService.save(contactPerson2);
        contactPersonID2 = contactPerson2.getId();

        Company companyA = new Company("Test", "mail@me.nl", "Arnhem", "6574YE", "De Straatlaan 15", contactPersonList, "POSTBUS", "2ou349837");
        companyA.setId(id);

        Company companyB = new Company("Test2", "me@mail.nl", "Nijmegen", "0965RE", "Het Laanstraat 51", contactPersonList2, "POSTBUS", "2ou349837");
        companyB.setId("testid2");

//      Create 2 costumers
        companyService.save(companyA);
        companyService.save(companyB);
    }

    @Test
    public void testSaveCompany() throws Exception {
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
        ContactPerson contactPerson = new ContactPerson("ABC", "123", "123@mail.nl", "032959595");
        contactPersonList.add(contactPerson);
        contactPersonService.save(contactPerson);

        Company company3 = new Company();
        company3.setZipcode("6758EW");
        company3.setName("Tester");
        company3.setCity("Nijmegen");
        company3.setInvoiceEmail("me@mail.nl");
        company3.setId("testid3");
        company3.setContactPersons(contactPersonList);
        companyService.save(company3);

        Company retrievedCompany = companyService.findOne("testid3");
        assertNotNull(retrievedCompany);
        companyService.delete("testid3");
    }

    @Test
    public void testGetCompanies() throws Exception {
        List<Company> companies = companyService.findAll();
        assertNotNull("failure - Expected to retrieve products", companies);
        assertTrue(companies.size() >= 2);
    }

    @Test
    public void testGetCompanyByID() throws Exception {
        Company company = companyService.findOne(id);
        assertNotNull(company);
    }

    @Test
    public void testGetCompanyByCompanyName() throws Exception {
        List<Company> companies = companyService.findByName("Test");
        assertNotNull("Should find companies", companies);
        assertEquals("failure - Expected size", 1, companies.size());
    }

    @Test
    public void testGetCompanyByZipCode() throws Exception {
        List<Company> companies = companyService.findByZipCode("6574YE");
        assertNotNull("Should find companies", companies);
        assertEquals("failure - Expected size", 1, companies.size());
    }

    @Test
    public void testGetCompanyByCity() throws Exception {
        List<Company> companies = companyService.findByCity("Arnhem");
        assertNotNull("Should find companies", companies);
        assertEquals("failure - Expected size", 1, companies.size());
    }

    @Test
    public void testGetCompanyByEmail() throws Exception {
        List<Company> companies = companyService.findByEmail("mail@me.nl");
        assertNotNull("Should find companies", companies);
        assertEquals("failure - Expected size", 1, companies.size());
    }

    @Test
    public void testGetCompanyByAddress() throws Exception {
        List<Company> companies = companyService.findByAddress("De Straatlaan 15");
        assertNotNull("Should find companies", companies);
        assertEquals("failure - Expected size", 1, companies.size());
    }

    @Test
    public void testGetCompanyContactPerson() throws Exception {
        Company company = companyService.findOne(id);
        List<ContactPerson> contactPersonList = company.getContactPersons();
        assertNotNull("Should find a contact person", contactPersonList);
    }

    @Test
    public void testDeleteCompany() throws Exception {
        companyService.delete(id);
        Company company = companyService.findOne(id);
        assertNull("Expected object to be deleted", company);
    }

    @After
    public void cleanUp() {
        companyService.delete("testid");
        companyService.delete("testid2");
        companyService.delete("testid3");
        contactPersonService.delete(contactPersonID);
        contactPersonService.delete(contactPersonID2);
    }

}