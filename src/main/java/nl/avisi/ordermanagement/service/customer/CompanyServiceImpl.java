package nl.avisi.ordermanagement.service.customer;

import nl.avisi.ordermanagement.domain.Company;
import nl.avisi.ordermanagement.domain.ContactPerson;
import nl.avisi.ordermanagement.service.contactPerson.ContactPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyQueryRepository companyQueryRepository;
    @Autowired
    private ContactPersonRepository contactPersonRepository;

     /**
     * This function finds all the companies
     * @return Returns the found companies
     **/
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    /**
     * This function saves a company and sets the IDs of the contact persons
     * @param company The company that has to be saved
     * @return Returns the saved company
     */
    @Override
    public Company save(Company company) {
        for(ContactPerson contactPerson : company.getContactPersons()) {
            contactPerson.setId(UUID.randomUUID().toString());
            contactPersonRepository.save(contactPerson);
        }
        return companyRepository.save(company);
    }

    /**
     * This function finds a specific company
     * @param id The id of the company that has to be found
     * @return Returns the found company
     */
    @Override
    public Company findOne(String id) {
        return companyRepository.findOne(id);
    }

    /**
     * This function deletes a company
     * @param id The ID of the company that has to be deleted
     */
    @Override
    public void delete(String id) {
        companyRepository.delete(id);
    }

    /**
     * This function searches for a company by name
     * @param companyName The name of the company/companies that have to be found
     * @return Returns the found company/companies
     */
    @Override
    public List<Company> findByName(String companyName) {
        return companyQueryRepository.findByName(companyName);
    }

    /**
     * This function searches for a company by email
     * @param email The email of the company/companies that have to be found
     * @return Returns the found company/companies
     */
    @Override
    public List<Company> findByEmail(String email) {
        return companyQueryRepository.findByEmail(email);
    }

    /**
     * This function searches for a city by name
     * @param city The city of the company/companies that have to be found
     * @return Returns the found company/companies
     */
    @Override
    public List<Company> findByCity(String city) {
        return companyQueryRepository.findByCity(city);
    }

    /**
     * This function searches for a company by zipcode
     * @param zipCode The zipcode of the company/companies that have to be found
     * @return Returns the found company/companies
     */
    @Override
    public List<Company> findByZipCode(String zipCode) {
        return companyQueryRepository.findByZipCode(zipCode);
    }

    /**
     * This function searches for a company by address
     * @param address The address of the company/companies that have to be found
     * @return Returns the found company/companies
     */
    @Override
    public List<Company> findByAddress(String address) {
        return companyQueryRepository.findByAddress(address);
    }

    /**
     * This function drops the collection of company
     */
    @Override
    public void dropCollection() {
        companyQueryRepository.dropCollection();
    }

    /**
     * This function deletes everything from the company database
     */
    @Override
    public void deleteAll() {
        companyQueryRepository.deleteAll();
    }
}
