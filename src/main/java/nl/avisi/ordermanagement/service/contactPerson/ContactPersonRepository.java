package nl.avisi.ordermanagement.service.contactPerson;


import nl.avisi.ordermanagement.domain.ContactPerson;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactPersonRepository extends MongoRepository<ContactPerson, String> {
}
