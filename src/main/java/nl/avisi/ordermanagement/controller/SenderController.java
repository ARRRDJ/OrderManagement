package nl.avisi.ordermanagement.controller;

import nl.avisi.ordermanagement.domain.Sender;
import nl.avisi.ordermanagement.service.sender.SenderService;
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
@RequestMapping(value = "/senders")
public class SenderController implements CRUDController {
    @Autowired
    private SenderService senderService;

    /**
     * This method is used to create a sender when there's a POST request on /senders/
     *
     * @param body Contains the variables of the POST request, accessed via the JSONObject
     * @return The response containing a success message and the saved sender.
     */
    @Override
    public Map<String, Object> create(@RequestBody String body) {
//      Convert request body (which is a String) to an actual JSON object.
        JSONObject jsonObject = new JSONObject(body);
//      Generate sender
        Sender sender = new Sender(
                jsonObject.getString("name"),
                jsonObject.getString("email"),
                jsonObject.getString("address"),
                jsonObject.getString("zipCode"),
                jsonObject.getString("city"),
                jsonObject.getString("kvknumber"),
                jsonObject.getString("bicnumber"),
                jsonObject.getString("ibannumber"),
                jsonObject.getString("btwnumber"),
                jsonObject.getString("website")
        );

//      Build the response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Sender created successfully");
        response.put("sender", senderService.save(sender));

        return response;
    }

    /**
     * This function is used to get a specific sender and display it when there's a GET request on /senders/{id}
     *
     * @param id The ID of the sender that has to be found
     * @return The response message with the found sender or a message saying the sender has not been found
     */
    @Override
    public Map<String, Object> show(@PathVariable String id) {
        Sender sender = senderService.findOne(id);

        Map<String, Object> response = new LinkedHashMap<>();
        if (sender != null) {
            response.put("sender", sender);
        } else {
            response.put("message", "Sender not found");
        }
        return response;
    }

    /**
     * This function is used to get all the senders and display them when there's a GET request on /senders/
     *
     * @return The response message with all the senders and a number depicting the amount of senders that have been found
     */
    @Override
    public Map<String, Object> showAll() {
        List<Sender> senders = senderService.findAll();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("totalSenders", senders.size());
        response.put("senders", senders);
        return response;
    }

    /**
     * This function is used to update a sender when there's a PUT request on /senders/{id}
     *
     * @param id   The ID of the sender that has to be updated
     * @param body Contains the variables of the PUT request, accessed via the JSONObject
     * @return The response message, which can either be a success message with the updated sender or a message saying the sender hasn't been found
     */
    @Override
    public Map<String, Object> update(@RequestBody String body, @PathVariable String id) {
        JSONObject jsonObject = new JSONObject(body);

        Map<String, Object> response = new LinkedHashMap<>();
//      Generate type
        Sender sender = senderService.findOne(id);
        if (sender != null){

            sender.setName(jsonObject.getString("name"));
            sender.setEmail(jsonObject.getString("email"));
            sender.setAddress(jsonObject.getString("address"));
            sender.setZipCode(jsonObject.getString("zipCode"));
            sender.setCity(jsonObject.getString("city"));
            sender.setKVKNumber(jsonObject.getString("kvknumber"));
            sender.setBICNumber(jsonObject.getString("bicnumber"));
            sender.setIBANNumber(jsonObject.getString("ibannumber"));
            sender.setBICNumber(jsonObject.getString("btwnumber"));
            sender.setWebsite(jsonObject.getString("website"));
            sender.setId(id);
            response.put("message", "Sender Updated successfully");
            response.put("sender", senderService.save(sender));
        }else {
            response.put("message", "Sender not found");
        }
        return response;
    }

    /**
     * This function is used to delete a sender when there's a DELETE request on /senders/{id}
     *
     * @param id The ID of the sender that has to be deleted
     * @return The response message, which can either be a success message or a message saying the sender hasn't been found
     */
    @Override
    public Map<String, Object> delete(@PathVariable String id) {
        Map<String, Object> response = new LinkedHashMap<>();
        Sender sender = senderService.findOne(id);

        if (sender != null) {
            response.put("message", "Sender deleted successfully");
            senderService.delete(id);
        } else {
            response.put("message", "Sender not found");
        }
        return response;
    }

    /**
     * This function is used to get a sender with certain words in the name or description and display them when there's a GET request on /search/name/{term}
     *
     * @param term The search term which probably will be found in the name of  that have to be found
     * @return The response message with the found sender
     */
    @RequestMapping(value = "/search/name/{term}")
    public Map<String, Object> findByName(@PathVariable String term) {
        // Get the matching records
        List<Sender> senders = senderService.findByName(term);
        // Build the response
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("totalSenders", senders.size());
        response.put("senders", senders);
        // Send to the client
        return response;
    }
}