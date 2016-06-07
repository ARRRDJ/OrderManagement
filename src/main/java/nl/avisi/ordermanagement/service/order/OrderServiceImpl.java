package nl.avisi.ordermanagement.service.order;

import nl.avisi.ordermanagement.domain.Invoice;
import nl.avisi.ordermanagement.domain.Order;
import nl.avisi.ordermanagement.service.invoice.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderQueryRepository orderQueryRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * This function finds all the orders
     *
     * @return Returns the found orders
     **/
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * This function saves an order and sets the IDs of the invoices
     *
     * @param order The order that has to be saved
     * @return Returns the saved order
     */
    @Override
    public Order save(Order order) {
        for (Invoice invoice : order.getInvoices()) {
            invoice.setId(UUID.randomUUID().toString());
            invoiceRepository.save(invoice);
        }
        return orderRepository.save(order);
    }

    /**
     * This function finds a specific order
     *
     * @param id The id of the order that has to be found
     * @return Returns the found order
     */
    @Override
    public Order findOne(String id) {
        return orderRepository.findOne(id);
    }

    /**
     * This function deletes an order
     *
     * @param id The ID of the order that has to be deleted
     */
    @Override
    public void delete(String id) {
        orderRepository.delete(id);
    }

    /**
     * This function deletes everything from the order database
     */
    @Override
    public void deleteAll() {
        orderQueryRepository.deleteAll();
    }

    /**
     * This function searches for orders by name
     *
     * @param name The name of the order/orders that have to be found
     * @return Returns the found order/orders
     */
    @Override
    public List<Order> findByName(String name) {
        return orderQueryRepository.findByName(name);
    }

    /**
     * This function searches for al the active orders
     *
     * @return Returns the found order/orders
     */
    @Override
    public List<Order> findAllActive() {
        return orderQueryRepository.findAllActive();
    }
}
