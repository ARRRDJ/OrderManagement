package nl.avisi.ordermanagement.controller;

import nl.avisi.ordermanagement.domain.Company;
import nl.avisi.ordermanagement.domain.ContactPerson;
import nl.avisi.ordermanagement.service.customer.CompanyService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Sam on 4/12/16.
 */
@RestController
@RequestMapping(value = "/companies")
public class CompanyController implements CRUDController {
    @Autowired
    private CompanyService companyService;

    /**
     * This method is used to create a company when there's a POST request on /customers/
     *
     * @param body Contains the variables of the POST request, accessed via the JSONObject
     * @return The response containing a success message and the saved company.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Map<String, Object> create(@RequestBody String body) {
//      Convert request body (which is a String) to an actual JSON object.
        JSONObject jsonObject = new JSONObject(body);
//      Creates the contact person list
        List<ContactPerson> contactPersonList = addContactPersons(jsonObject.getJSONArray("contactPersons"));
//      Creates the company
        Company company = new Company(jsonObject.getString("name"), jsonObject.getString("email"), jsonObject.getString("city"), jsonObject.getString("zipcode"), jsonObject.getString("address"), contactPersonList, jsonObject.getString("mailBox"), jsonObject.getString("number"));//      Build the response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("message", "Company created successfully");
        response.put("company", companyService.save(company));
//      Send to client
        return response;
    }

    /**
     * This function is used to get a specific company and display it when there's a GET request on /companies/{id}
     *
     * @param id The ID of the company that has to be found
     * @return The response message with the found company or a message saying the company has not been found
     */
    @RequestMapping(value = "/{id}")
    public Map<String, Object> show(@PathVariable String id) {
//      Get the company
        Company company = companyService.findOne(id);
//      Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        if (company != null) {
            response.put("company", company);
        } else {
            response.put("message", "Company not found");
        }
//      Send to client
        return response;
    }

    /**
     * This function is used to get all the companies and display them when there's a GET request on /companies/
     *
     * @return The response message with all the companies and a number depicting the amount of companies that have been found
     */
    @RequestMapping(value = "/")
    public Map<String, Object> showAll() {
//      Get all companies
        List<Company> companies = companyService.findAll();
//      Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("totalCompanies", companies.size());
        response.put("companies", companies);
//      Send to client
        return response;
    }

    /**
     * This function is used to update a company when there's a PUT request on /companies/{id}
     *
     * @param id   The ID of the company that has to be updated
     * @param body Contains the variables of the PUT request, accessed via the JSONObject
     * @return The response message, which can either be a success message with the updated company or a message saying the company hasn't been found
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(@RequestBody String body, @PathVariable String id) {
        JSONObject jsonObject = new JSONObject(body);
//      Get the company that has to be updated
        Company company = companyService.findOne(id);
//      Build response
        Map<String, Object> response = new HashMap<String, Object>();
        if (company != null) {
//          Create the contact person list
            List<ContactPerson> contactPersonList = addContactPersons(jsonObject.getJSONArray("contactPersons"));
//          Set the company values
            company.setId(id);
            company.setName(jsonObject.getString("name"));
            company.setInvoiceEmail(jsonObject.getString("email"));
            company.setCity(jsonObject.getString("city"));
            company.setZipcode(jsonObject.getString("zipcode"));
            company.setAddress(jsonObject.getString("address"));
            company.setContactPersons(contactPersonList);
//          Save company
            Company savedCompany = companyService.save(company);

            response.put("message", "Company updated successfully");
            response.put("company", savedCompany);
        } else {
            response.put("message", "Company not found");
        }
//      Send to client
        return response;
    }

    /**
     * This function is used to delete a company when there's a DELETE request on /companies/{id}
     *
     * @param id The ID of the company that has to be deleted
     * @return The response message, which can either be a success message or a message saying the company hasn't been found
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> delete(@PathVariable String id) {
//      Get the company that has to be deleted
        Company companyFromDB = companyService.findOne(id);
//      Build response
        Map<String, Object> response = new HashMap<String, Object>();
//      Checks if the company exists and deletes it
        if (companyFromDB != null) {
            companyService.delete(id);
            response.put("message", "Company deleted successfully");
        } else {
            response.put("message", "Company not found");
        }
//      Send to client
        return response;
    }

    /**
     * This function is used to get companies with a certain name and display them when there's a GET request on /companies/search/name/{name}
     *
     * @param name The name of the company/companies that have to be found
     * @return The response message with the found company/companies
     */
    @RequestMapping(value = "/search/name/{name}")
    public Map<String, Object> findByName(@PathVariable String name) {
//      Find the company
        List<Company> companies = companyService.findByName(name);
//      Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("totalCompanies", companies.size());
        response.put("companies", companies);
//      Send to client
        return response;
    }

    /**
     * This function is used to get companies with a certain email and display them when there's a GET request on /companies/search/email/{email}
     *
     * @param email The email of the company/companies that have to be found
     * @return The response message with the found company/companies
     */
    @RequestMapping(value = "/search/email/{email:.+}")
    public Map<String, Object> findByEmail(@PathVariable String email) {
//      Find the company
        List<Company> companies = companyService.findByEmail(email);
//      Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("totalCompanies", companies.size());
        response.put("companies", companies);
//      Send to client
        return response;
    }

    /**
     * This function is used to get companies with a certain email and display them when there's a GET request on /companies/search/email/{zipCode}
     *
     * @param zipCode The zipcode of the company/companies that have to be found
     * @return The response message with the found company/companies
     */
    @RequestMapping(value = "/search/zipCode/{zipCode}")
    public Map<String, Object> findByZipCode(@PathVariable String zipCode) {
//      Find the company
        List<Company> companies = companyService.findByZipCode(zipCode);
//      Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("totalCompanies", companies.size());
        response.put("companies", companies);
//      Send to client
        return response;
    }

    /**
     * This function is used to get companies with a certain city and display them when there's a GET request on /companies/search/city/{city}
     *
     * @param city The city of the company/companies that have to be found
     * @return The response message with the found company/companies
     */
    @RequestMapping(value = "/search/city/{city}")
    public Map<String, Object> findByCity(@PathVariable String city) {
//      Find the company
        List<Company> companies = companyService.findByCity(city);
//      Build response
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("totalCompanies", companies.size());
        response.put("companies", companies);
//      Send to client
        return response;
    }

    /**
     * Function used by the POST and UPDATE to create the contact persons list
     *
     * @param contactPersons The JSONArray containing the contact persons that have to be added
     * @return The created contact person list
     */
    private List<ContactPerson> addContactPersons(JSONArray contactPersons) {
        List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
        for (int i = 0; i < contactPersons.length(); i++) {
            JSONObject companyJSONObject = (JSONObject) contactPersons.get(i);
            ContactPerson contactPerson = new ContactPerson(companyJSONObject.getString("firstName"), companyJSONObject.getString("lastName"), companyJSONObject.getString("email"), companyJSONObject.getString("telephone"));
            contactPersonList.add(contactPerson);
        }
        return contactPersonList;
    }
}
