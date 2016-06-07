package nl.avisi.ordermanagement.service.type;


import nl.avisi.ordermanagement.domain.Type;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * A repository for the type, extends the MongoRepository
 */
public interface TypeRepository extends MongoRepository<Type, String> {
}
