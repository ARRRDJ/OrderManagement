package nl.avisi.ordermanagement.service.tax;

import nl.avisi.ordermanagement.domain.Tax;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * An interface with the functions for the tax service, implemented in TaxServiceImpl
 */
public interface TaxService {
    List<Tax> findAll();

    Tax save(Tax tax);

    @Cacheable("taxes")
    Tax findOne(String id);

    void delete(String id);
}
