package nl.avisi.ordermanagement.service.order;


import nl.avisi.ordermanagement.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * A repository for the orders, extends the MongoRepository
 */
public interface OrderRepository extends MongoRepository<Order, String> {
}
