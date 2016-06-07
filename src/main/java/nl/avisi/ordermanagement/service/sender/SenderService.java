package nl.avisi.ordermanagement.service.sender;

import nl.avisi.ordermanagement.domain.Sender;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by sjors on 5/26/16.
 */

/**
 * An interface with the functions for the sender service, implemented in SenderServiceImpl
 */
public interface SenderService {
    List<Sender> findAll();

    Sender save(Sender sender);

    @Cacheable("senders")
    Sender findOne(String id);

    void delete(String id);

    void deleteAll();

    List<Sender> findByName(String query);
}
