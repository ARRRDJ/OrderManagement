package nl.avisi.ordermanagement.service.customer;


import nl.avisi.ordermanagement.domain.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * A repository for the company with queries, extends the MongoRepository
 */
public interface CompanyQueryRepository extends MongoRepository<Company, String> {
    @Query(value = "{ 'name' : ?0 }")
    List<Company> findByName(String companyName);

    @Query(value = "{ 'invoiceEmail' : ?0 }")
    List<Company> findByEmail(String email);

    @Query(value = "{ 'city' : ?0 }")
    List<Company> findByCity(String city);

    @Query(value = "{ 'zipcode' : ?0 }")
    List<Company> findByZipCode(String zipCode);

    @Query(value = "{ 'address' : ?0 }")
    List<Company> findByAddress(String address);

    @Query(value = " { dropDatabase: 1 }")
    void dropCollection();

}

