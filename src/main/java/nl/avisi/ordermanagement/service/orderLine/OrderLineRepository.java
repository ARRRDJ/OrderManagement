package nl.avisi.ordermanagement.service.orderLine;


import nl.avisi.ordermanagement.domain.OrderLine;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Luuk on 12-5-2016.
 */

/**
 * A repository for the order lines, extends the MongoRepository
 */
public interface OrderLineRepository extends MongoRepository<OrderLine, String> {

}
