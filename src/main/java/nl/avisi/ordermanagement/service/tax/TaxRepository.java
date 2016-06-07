package nl.avisi.ordermanagement.service.tax;


import nl.avisi.ordermanagement.domain.Tax;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * A repository for the tax, extends the MongoRepository
 */
public interface TaxRepository extends MongoRepository<Tax, String> {
}
