package nl.avisi.ordermanagement.service.salesItem;

import nl.avisi.ordermanagement.domain.SalesItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SalesItemServiceImpl implements SalesItemService {
    @Autowired
    private SalesItemRepository salesItemRepository;
    @Autowired
    private SalesItemQueryRepository salesItemQueryRepository;

    /**
     * This function finds all the salesItems
     *
     * @return Returns the found salesItems
     **/
    @Override
    public List<SalesItem> findAll() {
        return salesItemRepository.findAll();
    }

    /**
     * This function saves a sales item
     *
     * @param salesItem The salesItems that has to be saved
     * @return Returns the saved salesItems
     */
    @Override
    public SalesItem save(SalesItem salesItem) {
        return salesItemRepository.save(salesItem);
    }

    /**
     * This function finds a specific sales item
     *
     * @param id The id of the sales item that has to be found
     * @return Returns the found sales item
     */
    @Override
    public SalesItem findOne(String id) {
        return salesItemRepository.findOne(id);
    }

    /**
     * This function deletes a sales item
     *
     * @param id The ID of the sales item that has to be deleted
     */
    @Override
    public void delete(String id) {
        salesItemRepository.delete(id);
    }

    /**
     * This function deletes everything from the sales item database
     */
    @Override
    public void deleteAll() {
        salesItemRepository.deleteAll();
    }

    /**
     * This function searches for a sales item/sales items by address
     *
     * @param query The name of the sales item/sales items that have to be found
     * @return Returns the found sale item/sales items
     */
    @Override
    public List<SalesItem> findByNameOrDescription(String query) {
        return salesItemQueryRepository.findByName(query);
    }

    /**
     * This function drops the collection of sales item
     */
    @Override
    public void dropCollection() {
        salesItemQueryRepository.dropCollection();
    }
}
