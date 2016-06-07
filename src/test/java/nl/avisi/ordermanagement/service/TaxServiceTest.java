package nl.avisi.ordermanagement.service;

import nl.avisi.ordermanagement.OrderManagement;
import nl.avisi.ordermanagement.domain.Tax;
import nl.avisi.ordermanagement.domain.Type;
import nl.avisi.ordermanagement.service.tax.TaxService;
import nl.avisi.ordermanagement.service.type.TypeService;
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
public class TaxServiceTest {
    @Autowired
    private TaxService taxService;

    private String id = "2";

    @Before
    public void setUp() {
//      remove all records
//        typeService.deleteAll();

        Tax tax1 = new Tax(5.25);
        tax1.setId("1");
        Tax tax2 = new Tax(3.25);
        tax1.setId("2");

//      Create 2 projects
        taxService.save(tax1);
        taxService.save(tax2);

    }

    @Test
    public void addTax() throws Exception {
        Tax tax3 = new Tax(1.25);
        tax3.setId("3");
        taxService.save(tax3);
        Tax taxRetrieved = taxService.findOne("3");
        assertNotNull(taxRetrieved);
        taxService.delete("3");
    }


    @Test
    public void deleteTax() throws Exception {
        taxService.delete(id);
        assertNull(taxService.findOne(id));
    }

    @Test
    public void getTaxes() throws Exception {
        List<Tax> taxes = taxService.findAll();
        assertNotNull("failure - Expected to retrieve taxes", taxes);
        assertTrue(taxes.size() >= 2);
    }

    @Test
    public void getTax() throws Exception {
        Tax tax = taxService.findOne(id);
        assertNotNull(tax);
    }

    @After
    public void cleanUp() {
        taxService.delete("1");
        taxService.delete("2");
    }
}