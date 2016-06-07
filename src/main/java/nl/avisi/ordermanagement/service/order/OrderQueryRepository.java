package nl.avisi.ordermanagement.service.order;

import nl.avisi.ordermanagement.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * A repository for the orders with queries, extends the MongoRepository
 */
public interface OrderQueryRepository extends MongoRepository<Order, String> {

    @Query(value = "{'name' : ?0 }")
    List<Order> findByName(String name);

    @Query(value = "{ 'active' : true}")
    List<Order> findAllActive();

}
