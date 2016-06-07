package nl.avisi.ordermanagement.service.contactPerson;

import nl.avisi.ordermanagement.domain.ContactPerson;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


public interface ContactPersonService {
    List<ContactPerson> findAll();

    ContactPerson save(ContactPerson contactPerson);

    @Cacheable("contactPersons")
    ContactPerson findOne(String id);

    void delete(String id);

    List<ContactPerson> findByFirstName(String firstName);

    List<ContactPerson> findByLastName(String lastName);

    List<ContactPerson> findByEmail(String email);

    void dropCollection();

    void deleteAll();
}
