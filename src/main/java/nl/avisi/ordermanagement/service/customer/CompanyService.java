package nl.avisi.ordermanagement.service.customer;

import nl.avisi.ordermanagement.domain.Company;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * An interface with the functions for the company service, implemented in CompanyServiceImpl
 */
public interface CompanyService {
    List<Company> findAll();

    Company save(Company company);

    @Cacheable("companies")
    Company findOne(String id);

    void delete(String id);

    List<Company> findByName(String companyName);

    List<Company> findByEmail(String email);

    List<Company> findByCity(String city);

    List<Company> findByZipCode(String zipCode);

    List<Company> findByAddress(String address);

    void dropCollection();

    void deleteAll();
}
