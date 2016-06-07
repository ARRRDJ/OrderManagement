package nl.avisi.ordermanagement.service.salesItem;


import nl.avisi.ordermanagement.domain.SalesItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * A repository for the sales items with queries, extends the MongoRepository
 */
public interface SalesItemQueryRepository extends MongoRepository<SalesItem, String> {

    @Query(value = "{$or : [{ 'name' : ?0 }, {'description': {$regex : ?0} }] }")
    List<SalesItem> findByName(String name);

    @Query(value = " { dropDatabase: 1 }")
    void dropCollection();

}

