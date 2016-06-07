package nl.avisi.ordermanagement.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by robert on 5/26/16.
 */

/**
 * An interface that is implemented by the controllers, contains the standard CRUD functions
 */

public interface CRUDController {
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Map<String, Object> create(@RequestBody String body);

    @RequestMapping(value = "/{id}")
    Map<String, Object> show(@PathVariable String id);

    @RequestMapping(value = "/")
    Map<String, Object> showAll();

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    Map<String, Object> update(@RequestBody String body, @PathVariable String id);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    Map<String, Object> delete(@PathVariable String id);
}
