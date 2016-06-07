package nl.avisi.ordermanagement.service;

import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.SalesItem;
import nl.avisi.ordermanagement.service.salesItem.SalesItemService;
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
 * Created by robert on 5/13/16.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = OrderManagement.class)
@WebIntegrationTest
public class SalesItemServiceTest {

    @Autowired
    private SalesItemService salesItemService;

    private String id = "2";

    @Before
    public void setUp() {
        SalesItem salesItem1 = new SalesItem();
        salesItem1.setDescription("description1");
        salesItem1.setName("name1");
        salesItem1.setPrice(1.20);
        salesItem1.setId("1");


        SalesItem salesItem2 = new SalesItem();
        salesItem2.setDescription("description2");
        salesItem2.setName("name2");
        salesItem2.setPrice(22.00);
        salesItem1.setId("2");


//      Create 2 projects
        salesItemService.save(salesItem1);
        salesItemService.save(salesItem2);
    }

    @After
    public void tearDown() {
//        cleanup
    }

    @Test
    public void addProduct() throws Exception {
        SalesItem salesItem3 = new SalesItem();
        salesItem3.setDescription("description3");
        salesItem3.setName("name3");
        salesItem3.setPrice(33.00);
        salesItem3.setId("3");
        salesItemService.save(salesItem3);

        SalesItem salesItemRetrieved = salesItemService.findOne("3");
        assertNotNull(salesItemRetrieved);
        salesItemService.delete("3");
    }

    @Test
    public void getProducts() throws Exception {
        List<SalesItem> products = salesItemService.findAll();
        assertNotNull("failure - Expected to retrieve products", products);
        assertTrue(products.size() >= 2);
    }

    @Test
    public void getProduct() throws Exception {
        SalesItem salesItem = salesItemService.findOne(id);
        assertNotNull(salesItem);
    }

    @Test
    public void getProductByNameOrDescription() throws Exception {
        List<SalesItem> salesItems = salesItemService.findByNameOrDescription("name1");
        assertNotNull("Should find salesItems", salesItems);
        assertEquals("failure - Expected size", 1, salesItems.size());
    }

    @Test
    public void deleteProduct() throws Exception {
        salesItemService.delete(id);
        SalesItem salesItem = salesItemService.findOne(id);
        assertNull("Expected object to be deleted", salesItem);
    }

    @After
    public void cleanUp() {
        salesItemService.delete("1");
        salesItemService.delete("2");
    }
}