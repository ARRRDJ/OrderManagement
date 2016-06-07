package nl.avisi.ordermanagement.service.contactPerson;

import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.ContactPerson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Sam on 18-5-2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(OrderManagement.class)
@WebIntegrationTest
public class ContactPersonTest {
    @Autowired
    private ContactPersonService contactPersonService;

    private String id = "testid";

    @Before
    public void setUp() {
//      remove all records
        contactPersonService.deleteAll();

        ContactPerson contactPerson1 = new ContactPerson("Carlo", "Tact", "c@mail.nl", "06484848");
        contactPerson1.setId(id);
        contactPersonService.save(contactPerson1);

        ContactPerson contactPerson2 = new ContactPerson("Cont", "Act", "cs@mail.nl", "03884594");
        contactPerson1.setId("testid3");
        contactPersonService.save(contactPerson2);

    }

    @After
    public void tearDown() {
//        cleanup
    }

    @Test
    public void saveContactPerson() throws Exception {
        ContactPerson contactPerson = new ContactPerson("ABC", "123", "123@mail.nl", "0575757");
        contactPerson.setId("testid2");
        contactPersonService.save(contactPerson);

        ContactPerson retrievedContactPerson = contactPersonService.findOne("testid2");
        assertNotNull(retrievedContactPerson);
        contactPersonService.delete("testid2");
    }

    @Test
    public void getContactPersons() throws Exception {
        List<ContactPerson> contactPersons = contactPersonService.findAll();
        assertNotNull("failure - Expected to retrieve contact persons", contactPersons);
        assertTrue(contactPersons.size() >= 2);
    }

    @Test
    public void getContactPersonByID() throws Exception {
        ContactPerson contactPerson = contactPersonService.findOne(id);
        assertNotNull(contactPerson);
    }

    @Test
    public void getContactPersonByFirstName() throws Exception {
        List<ContactPerson> contactPersons = contactPersonService.findByFirstName("Carlo");
        assertNotNull("Should find customers", contactPersons);
        assertEquals("failure - Expected size", 1, contactPersons.size());
    }

    @Test
    public void getContactPersonByLastName() throws Exception {
        List<ContactPerson> contactPersons = contactPersonService.findByLastName("Tact");
        assertNotNull("Should find customers", contactPersons);
        assertEquals("failure - Expected size", 1, contactPersons.size());
    }

    @Test
    public void getContactPersonByEmail() throws Exception {
        List<ContactPerson> contactPersons = contactPersonService.findByEmail("c@mail.nl");
        assertNotNull("Should find customers", contactPersons);
        assertEquals("failure - Expected size", 1, contactPersons.size());
    }

    @Test
    public void deleteContactPerson() throws Exception {
        contactPersonService.delete(id);
        ContactPerson contactPerson = contactPersonService.findOne(id);
        assertNull("Expected object to be deleted", contactPerson);
    }

    @After
    public void cleanUp() {
        contactPersonService.delete("testid");
        contactPersonService.delete("testid2");
        contactPersonService.delete("testid3");
    }
}
