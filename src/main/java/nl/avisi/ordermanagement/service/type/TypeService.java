package nl.avisi.ordermanagement.service.type;

import nl.avisi.ordermanagement.domain.Type;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * An interface with the functions for the type service, implemented in TypeServiceImpl
 */
public interface TypeService {
    @Cacheable("types")
    Type findOne(String id);

    List<Type> findAll();

    Type save(Type type);

    void delete(String id);

}
