package nl.avisi.ordermanagement.service.orderLine;

import nl.avisi.ordermanagement.domain.OrderLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Luuk on 12-5-2016.
 */

@Service
@Transactional
public class OrderLineServiceImpl implements OrderLineService {
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private OrderLineQueryRepository orderLineQueryRepository;

    /**
     * This function finds all the order lines
     *
     * @return Returns the found order lines
     **/
    @Override
    public List<OrderLine> findAll() {
        return orderLineRepository.findAll();
    }

    /**
     * This function saves an order line and sets the IDs of the invoices
     *
     * @param orderLine The order line that has to be saved
     */
    @Override
    public void save(OrderLine orderLine) {
        orderLineRepository.save(orderLine);
    }

    /**
     * This function finds a specific order line
     *
     * @param id The id of the order line that has to be found
     * @return Returns the found order line
     */
    @Override
    public OrderLine findOne(String id) {
        return orderLineRepository.findOne(id);
    }

    /**
     * This function deletes an order line
     *
     * @param id The ID of the order line that has to be deleted
     */
    @Override
    public void delete(String id) {
        orderLineRepository.delete(id);
    }
}
