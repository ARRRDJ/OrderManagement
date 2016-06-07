package nl.avisi.ordermanagement.service.invoice;

import nl.avisi.ordermanagement.domain.Invoice;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * An interface with the functions for the invoice service, implemented in InvoiceServiceImpl
 */
public interface InvoiceService {
    List<Invoice> findAll();

    Invoice save(Invoice invoice);

    @Cacheable("invoices")
    Invoice findOne(String id);

    List<Invoice> findByInvoiceNumber(String invoiceNumber);

    void delete(String id);
}
