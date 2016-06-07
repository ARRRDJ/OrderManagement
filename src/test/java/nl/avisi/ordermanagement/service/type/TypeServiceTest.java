package nl.avisi.ordermanagement.service.type;

import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by robert on 5/13/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OrderManagement.class)
@WebIntegrationTest
public class TypeServiceTest {
    @Autowired
    private TypeService typeService;

    private String id = "2";

    @Before
    public void setUp() {
//      remove all records
//        typeService.deleteAll();

        Type type1 = new Type("name1");
        type1.setId("1");
        Type type2 = new Type("name2");
        type2.setId("2");

//      Create 2 projects
        typeService.save(type1);
        typeService.save(type2);

    }

    @Test
    public void addType() throws Exception {
        Type type3 = new Type("type3");
        type3.setId("3");
        typeService.save(type3);
        Type typeRetrieved = typeService.findOne("3");
        assertNotNull(typeRetrieved);
        typeService.delete("3");
    }


    @Test
    public void deleteType() throws Exception {
        typeService.delete(id);
        assertNull(typeService.findOne(id));
    }

    @Test
    public void getTypes() throws Exception {
        List<Type> types = typeService.findAll();
        assertNotNull("failure - Expected to retrieve types", types);
        assertTrue(types.size() >= 2);
    }

    @Test
    public void getType() throws Exception {
        Type type = typeService.findOne(id);
        assertNotNull(type);
    }

    @After
    public void cleanUp() {
        typeService.delete("1");
        typeService.delete("2");
    }
}