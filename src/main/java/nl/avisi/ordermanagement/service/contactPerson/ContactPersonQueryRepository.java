package nl.avisi.ordermanagement.service.contactPerson;


import nl.avisi.ordermanagement.domain.ContactPerson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ContactPersonQueryRepository extends MongoRepository<ContactPerson, String> {

    @Query(value = "{ 'firstName' : ?0 }")
    List<ContactPerson> findByFirstName(String firstName);

    @Query(value = "{ 'lastName' : ?0 }")
    List<ContactPerson> findByLastName(String lastName);

    @Query(value = "{ 'email' : ?0 }")
    List<ContactPerson> findByEmail(String email);

    @Query(value = " { dropDatabase: 1 }")
    void dropCollection();

}

