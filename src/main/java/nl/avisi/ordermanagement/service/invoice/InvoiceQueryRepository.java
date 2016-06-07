package nl.avisi.ordermanagement.service.invoice;

import nl.avisi.ordermanagement.domain.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by robert on 6/2/16.
 */

/**
 * A repository for the invoice with queries, extends the MongoRepository
 */
public interface InvoiceQueryRepository extends MongoRepository<Invoice, String> {
    @Query(value = "{ 'number' : ?0 }")
    List<Invoice> findByInvoiceNumber(String invoiceNumber);
}
