package nl.avisi.ordermanagement.service.orderLine;

import nl.avisi.ordermanagement.domain.OrderLine;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Luuk on 12-5-2016.
 */

/**
 * A repository for the order lines with queries, extends the MongoRepository
 */
public interface OrderLineQueryRepository extends MongoRepository<OrderLine, String> {
}
