package nl.avisi.ordermanagement.service.sender;

import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.Sender;
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
 * Created by sjors on 5/27/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OrderManagement.class)
@WebIntegrationTest
public class SenderServiceTest {

    private String id = "2";

    @Autowired
    private SenderService senderService;

    @Before
    public void SetUp() {
        // empty collection
        senderService.deleteAll();

        Sender sender1 = new Sender(
                "avisi1",
                "email@email.nl",
                "Meander 251",
                "6825 MC",
                "Arnhem",
                "test",
                "test",
                "test",
                "test",
                "http://www.avisi.nl"
        ); sender1.setId("1");

        Sender sender2 = new Sender(
                "avisi2",
                "email@email.nl",
                "Meander 251",
                "6825 MC",
                "Arnhem",
                "test",
                "test",
                "test",
                "test",
                "http://www.avisi.nl"
        ); sender1.setId("2");

        // Create 2 projects
        senderService.save(sender1);
        senderService.save(sender2);
    }

    @Test
    public void create() {
        Sender sender = new Sender(
                "avisi3",
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
        sender.setId("3");

        senderService.save(sender);

        Sender senderRetrieved = senderService.findOne("3");
        assertNotNull(senderRetrieved);
        senderService.delete("3");
    }

    @Test
    public void getSenders() throws Exception {
        List<Sender> senders = senderService.findAll();
        assertNotNull("failure - Expected to retrieve types", senders);
        assertTrue(senders.size() >= 2);
    }

    @Test
    public void getSender() throws Exception {
        Sender sender = senderService.findOne(id);
        assertNotNull(sender);
    }

    @Test
    public void delete() {
        senderService.delete(id);
        assertNull(senderService.findOne(id));
    }

    @After
    public void cleanUp() {
        senderService.delete("1");
        senderService.delete("2");
    }

}
