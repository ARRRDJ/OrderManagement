package nl.avisi.ordermanagement.service.customer;


import nl.avisi.ordermanagement.domain.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * A repository for the company, extends the MongoRepository
 */
public interface CompanyRepository extends MongoRepository<Company, String> {
}
