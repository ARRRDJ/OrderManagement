package nl.avisi.ordermanagement.service.orderLine;

import nl.avisi.ordermanagement.domain.OrderLine;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by Luuk on 12-5-2016.
 */

/**
 * An interface with the functions for the order line service, implemented in OrderLineServiceImpl
 */
public interface OrderLineService {
    public List<OrderLine> findAll();

    public void save(OrderLine order);

    @Cacheable("orders")
    public OrderLine findOne(String id);

    public void delete(String id);
}
