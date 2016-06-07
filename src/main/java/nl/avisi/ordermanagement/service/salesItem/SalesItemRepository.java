package nl.avisi.ordermanagement.service.salesItem;


import nl.avisi.ordermanagement.domain.SalesItem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * A repository for the sales items, extends the MongoRepository
 */
public interface SalesItemRepository extends MongoRepository<SalesItem, String> {
}
