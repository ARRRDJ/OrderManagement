package nl.avisi.ordermanagement.service.order;

import nl.avisi.ordermanagement.domain.Order;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * An interface with the functions for the invoice service, implemented in InvoiceServiceImpl
 */
public interface OrderService {
    List<Order> findAll();

    Order save(Order order);

    @Cacheable("orders")
    Order findOne(String id);

    void delete(String id);

    void deleteAll();

    List<Order> findByName(String name);

    List<Order> findAllActive();
}
