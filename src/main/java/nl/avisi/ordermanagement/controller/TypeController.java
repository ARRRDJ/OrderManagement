package nl.avisi.ordermanagement.controller;

import nl.avisi.ordermanagement.domain.Type;
import nl.avisi.ordermanagement.service.type.TypeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/types")
public class TypeController implements CRUDController {
    // Inject services
    @Autowired
    private TypeService typeService;

    /**
     * This method is used to create a type when there's a POST request on /types/
     *
     * @param body Contains the variables of the POST request, accessed via the JSONObject
     * @return The response containing a success message and the saved type.
     */
    @Override
    public Map<String, Object> create(@RequestBody String body) {
//      Convert request body (which is a String) to an actual JSON object.
        JSONObject jsonObject = new JSONObject(body);
//      Generate type
        Type type = new Type(jsonObject.getString("name"));
        type = typeService.save(type);
//      Build the response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Type created successfully");
        response.put("type", type);
//      Send to client
        return response;
    }

    /**
     * This function is used to get a specific type and display it when there's a GET request on /types/{id}
     *
     * @param id The ID of the type that has to be found
     * @return The response message with the found type or a message saying the type has not been found
     */
    @Override
    public Map<String, Object> show(@PathVariable String id) {
        // Get the type from db
        Type type = typeService.findOne(id);
        // Build response
        Map<String, Object> response = new LinkedHashMap<>();
        if (type != null) {
            response.put("type", type);
        } else {
            response.put("message", "Type not found");
        }
        // Send to client
        return response;
    }

    /**
     * This function is used to get all the types and display them when there's a GET request on /types/
     *
     * @return The response message with all the types and a number depicting the amount of types that have been found
     */
    @Override
    public Map<String, Object> showAll() {
        // Get all types
        List<Type> types = typeService.findAll();
        // Build the response
        Map<String, Object> response = new LinkedHashMap<>();
        // Count the total records
        response.put("totalTypes", types.size());
        response.put("types", types);
        // Send to client
        return response;
    }

    /**
     * This function is used to update a type when there's a PUT request on /types/{id}
     *
     * @param id   The ID of the type that has to be updated
     * @param body Contains the variables of the PUT request, accessed via the JSONObject
     * @return The response message, which can either be a success message with the updated type or a message saying the type hasn't been found
     */
    @Override
    public Map<String, Object> update(@RequestBody String body, @PathVariable String id) {
        JSONObject jsonObject = new JSONObject(body);
//      Generate type
        Type type = typeService.findOne(id);
        Map<String, Object> response = new LinkedHashMap<>();
        if (type != null) {
            // Update properties
            type.setName(jsonObject.getString("name"));
            type.setId(id);
            response.put("message", "Type Updated successfully");
            response.put("type", typeService.save(type));
        } else {
            response.put("message", "Type not found");
        }

        // Send to client
        return response;
    }

    /**
     * This function is used to delete a type when there's a DELETE request on /types/{id}
     *
     * @param id The ID of the type that has to be deleted
     * @return The response message, which can either be a success message or a message saying the type hasn't been found
     */
    @Override
    public Map<String, Object> delete(@PathVariable String id) {
        // Get type from db
        Type type = typeService.findOne(id);
        // Build response
        Map<String, Object> response = new HashMap<>();
        if (type != null) {
            typeService.delete(id);
            response.put("message", "Type deleted successfully");
        } else {
            response.put("message", "Type not found");
        }
        // Send to client
        return response;
    }
}