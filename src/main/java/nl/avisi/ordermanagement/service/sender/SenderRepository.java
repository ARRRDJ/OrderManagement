package nl.avisi.ordermanagement.service.sender;


import nl.avisi.ordermanagement.domain.Sender;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * A repository for the sender, extends the MongoRepository
 */
public interface SenderRepository extends MongoRepository<Sender, String> {
}
