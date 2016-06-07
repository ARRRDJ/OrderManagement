package nl.avisi.ordermanagement.controller;

import nl.avisi.ordermanagement.domain.Tax;
import nl.avisi.ordermanagement.service.tax.TaxService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/taxes")
public class TaxController implements CRUDController {
    @Autowired
    private TaxService taxService;

    /**
     * This method is used to create a tax when there's a POST request on /taxes/
     *
     * @param body Contains the variables of the POST request, accessed via the JSONObject
     * @return The response containing a success message and the saved tax.
     */
    @Override
    public Map<String, Object> create(@RequestBody String body) {
        // Convert request body (which is a String) to an actual JSON object.
        JSONObject jsonObject = new JSONObject(body);
        // Generate Tax
        Tax tax = new Tax(jsonObject.getDouble("rate"));
        tax = taxService.save(tax);
        // Build the response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("message", "Tax created successfully");
        response.put("tax", tax);
        // Send to client
        return response;
    }

    /**
     * This function is used to get a specific tax and display it when there's a GET request on /taxes/{id}
     *
     * @param id The ID of the tax that has to be found
     * @return The response message with the found tax or a message saying the tax has not been found
     */
    @Override
    public Map<String, Object> show(@PathVariable String id) {
        // Get the tax from db
        Tax tax = taxService.findOne(id);
        // Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        if (tax != null) {
            response.put("tax", tax);
        } else {
            response.put("message", "Tax not found");
        }
        // Send to client
        return response;
    }

    /**
     * This function is used to get all the taxes and display them when there's a GET request on /taxes/
     *
     * @return The response message with all the taxes and a number depicting the amount of taxes that have been found
     */
    @Override
    public Map<String, Object> showAll() {
        List<Tax> taxes = taxService.findAll();

        // Build the response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("taxes", taxes);
        response.put("totalTaxes", taxes.size());

        return response;
    }

    /**
     * This function is used to update a tax when there's a PUT request on /taxes/{id}
     *
     * @param id   The ID of the tax that has to be updated
     * @param body Contains the variables of the PUT request, accessed via the JSONObject
     * @return The response message, which can either be a success message with the updated tax or a message saying the tax hasn't been found
     */
    @Override
    public Map<String, Object> update(@RequestBody String body, @PathVariable String id) {
        JSONObject jsonObject = new JSONObject(body);
        // Get tax
        Tax tax = taxService.findOne(id);
        // Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        if (tax != null) {
            // Update and save
            tax.setRate(jsonObject.getDouble("rate"));
            tax.setId(id);
            taxService.save(tax);
            response.put("message", "Tax updated successfully");
            response.put("tax", taxService.save(tax));
        } else {
            response.put("message", "Tax not found");
        }
        // Send to client
        return response;
    }

    /**
     * This function is used to delete a tax when there's a DELETE request on /taxes/{id}
     *
     * @param id The ID of the tax that has to be deleted
     * @return The response message, which can either be a success message or a message saying the tax hasn't been found
     */
    @Override
    public Map<String, Object> delete(@PathVariable String id) {
        // Get tax
        Tax tax = taxService.findOne(id);
        // Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        if (tax != null) {
            taxService.delete(id);
            response.put("message", "Tax deleted successfully");
        } else {
            response.put("message", "Tax not found");
        }
        // Send to client
        return response;
    }
}