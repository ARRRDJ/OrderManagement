package nl.avisi.ordermanagement.controller;

import nl.avisi.ordermanagement.domain.SalesItem;
import nl.avisi.ordermanagement.domain.Tax;
import nl.avisi.ordermanagement.domain.Type;
import nl.avisi.ordermanagement.service.salesItem.SalesItemService;
import nl.avisi.ordermanagement.service.tax.TaxService;
import nl.avisi.ordermanagement.service.type.TypeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/salesItems")
public class SalesItemController implements CRUDController {
    // Inject services
    @Autowired
    private SalesItemService salesItemService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TaxService taxService;

    /**
     * This method is used to create a salesItem when there's a POST request on /terms/
     *
     * @param body Contains the variables of the POST request, accessed via the JSONObject
     * @return The response containing a success message and the saved salesItem.
     */
    @Override
    public Map<String, Object> create(@RequestBody String body) {
        // Convert request body (which is a String) to an actual JSON object.
        JSONObject jsonObject = new JSONObject(body);
        // Getting the Type object from the database
        Type type = typeService.findOne(jsonObject.getString("typeId"));
        // Getting the Tax object from the database
        Tax tax = taxService.findOne(jsonObject.getString("taxId"));
        // Create a new salesItem object
        SalesItem salesItem = new SalesItem(jsonObject.getString("name"), jsonObject.getString("description"), jsonObject.getDouble("price"), jsonObject.getString("sku"), type, tax);
        // Save to the database
        salesItem = salesItemService.save(salesItem);
        // Build the JSON response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "SalesItem created successfully");
        response.put("salesItem", salesItem);
        // Send to the client
        return response;
    }

    /**
     * This function is used to get a specific salesItem and display it when there's a GET request on /terms/{id}
     *
     * @param id The ID of the salesItem that has to be found
     * @return The response message with the found salesItem or a message saying the salesItem has not been found
     */
    @Override
    public Map<String, Object> show(@PathVariable String id) {
        // Get the record
        SalesItem salesItem = salesItemService.findOne(id);
        // Create the response
        Map<String, Object> response = new LinkedHashMap<>();
        if (salesItem != null) {
            response.put("salesItem", salesItem);
        } else {
            response.put("message", "SalesItem not found");
        }
        // Send the client
        return response;
    }

    /**
     * This function is used to get all the salesItem and display them when there's a GET request on /terms/
     *
     * @return The response message with all the salesItem and a number depicting the amount of terms that have been found
     */
    @Override
    public Map<String, Object> showAll() {
        // Get the records
        List<SalesItem> salesItems = salesItemService.findAll();
        Map<String, Object> response = new LinkedHashMap<>();
        // Count the total records
        response.put("totalSalesItems", salesItems.size());
        // Send the object
        response.put("salesItems", salesItems);
        // Send to the client
        return response;
    }

    /**
     * This function is used to update a salesItem when there's a PUT request on /terms/{id}
     *
     * @param id   The ID of the salesItem that has to be updated
     * @param body Contains the variables of the PUT request, accessed via the JSONObject
     * @return The response message, which can either be a success message with the updated salesItem or a message saying the salesItem hasn't been found
     */
    @Override
    public Map<String, Object> update(@RequestBody String body, @PathVariable String id) {
        JSONObject jsonObject = new JSONObject(body);
        SalesItem salesItem = salesItemService.findOne(id);
        Map<String, Object> response = new LinkedHashMap<>();
        if (salesItem != null) {
            // Update the salesItem
            Type type = typeService.findOne(jsonObject.getString("typeId"));
            Tax tax = taxService.findOne(jsonObject.getString("taxId"));
            salesItem.setId(id);
            salesItem.setName(jsonObject.getString("name"));
            salesItem.setDescription(jsonObject.getString("description"));
            salesItem.setPrice(jsonObject.getDouble("price"));
            salesItem.setSku(jsonObject.getString("sku"));
            salesItem.setType(type);
            salesItem.setTax(tax);

            response.put("message", "SalesItem updated successfully");
            response.put("salesItem", salesItemService.save(salesItem));
        } else {
            response.put("message", "SalesItem not found");
        }
        // Send to the client
        return response;
    }

    /**
     * This function is used to delete a salesItem when there's a DELETE request on /terms/{id}
     *
     * @param id The ID of the salesItem that has to be deleted
     * @return The response message, which can either be a success message or a message saying the salesItem hasn't been found
     */
    @Override
    public Map<String, Object> delete(@PathVariable String id) {
        // check if exists
        SalesItem salesItem = salesItemService.findOne(id);
        // Build the response
        Map<String, Object> response = new HashMap<>();
        if (salesItem != null) {
            response.put("message", "SalesItem deleted successfully");
            salesItemService.delete(id);
        } else {
            response.put("message", "SalesItem not found");
        }
        // Send to the client
        return response;
    }

    /**
     * This function is used to get a salesItem with certain words in the name or description and display them when there's a GET request on /terms/search/nameOrDescription/{salesItem}
     *
     * @param term The search term which probably will be found in the description or name of  that have to be found
     * @return The response message with the found salesItem
     */
    @RequestMapping(value = "/search/nameOrDescription/{term}")
    public Map<String, Object> getSaleItemByNameOrDescription(@PathVariable String term) {
        // Get the matching records
        List<SalesItem> salesItems = salesItemService.findByNameOrDescription(term);
        // Build the response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("totalSalesItems", salesItems.size());
        response.put("salesItems", salesItems);
        // Send to the client
        return response;
    }
}
