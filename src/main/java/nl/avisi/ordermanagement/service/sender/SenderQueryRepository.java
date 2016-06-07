package nl.avisi.ordermanagement.service.sender;


import nl.avisi.ordermanagement.domain.Sender;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * A repository for the sender with queries, extends the MongoRepository
 */
public interface SenderQueryRepository extends MongoRepository<Sender, String> {

    @Query(value = "{ 'name' : ?0 }")
    List<Sender> findByName(String name);

//    @Query(value = " { dropDatabase: 1 }")
//    void dropCollection();

}

