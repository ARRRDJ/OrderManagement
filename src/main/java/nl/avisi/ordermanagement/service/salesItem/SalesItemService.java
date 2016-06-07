package nl.avisi.ordermanagement.service.salesItem;

import nl.avisi.ordermanagement.domain.SalesItem;

import java.util.List;

/**
 * An interface with the functions for the sales item service, implemented in SalesItemImpl
 */
public interface SalesItemService {
    List<SalesItem> findAll();

    SalesItem save(SalesItem salesItem);

    SalesItem findOne(String id);

    void delete(String id);

    void deleteAll();

    List<SalesItem> findByNameOrDescription(String query);

    void dropCollection();
}
