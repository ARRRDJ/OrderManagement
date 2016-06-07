package nl.avisi.ordermanagement.service.invoice;

import nl.avisi.ordermanagement.domain.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Sam on 1-6-2016.
 */

/**
 * A repository for the company, extends the MongoRepository
 */
public interface InvoiceRepository extends MongoRepository<Invoice, String> {
}
