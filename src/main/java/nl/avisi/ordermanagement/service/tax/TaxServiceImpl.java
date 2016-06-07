package nl.avisi.ordermanagement.service.tax;

import nl.avisi.ordermanagement.domain.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaxServiceImpl implements TaxService {
    @Autowired
    private TaxRepository taxRepository;

    /**
     * This function finds all the taxes
     *
     * @return Returns the found taxes
     **/
    @Override
    public List<Tax> findAll() {
        return taxRepository.findAll();
    }

    /**
     * This function saves a tax
     *
     * @param tax The tax that has to be saved
     * @return Returns the saved tax
     */
    @Override
    public Tax save(Tax tax) {
        return taxRepository.save(tax);
    }

    /**
     * This function finds a specific tax
     *
     * @param id The id of the tax that has to be found
     * @return Returns the found tax
     */
    @Override
    public Tax findOne(String id) {
        return taxRepository.findOne(id);
    }

    /**
     * This function deletes a tax
     *
     * @param id The ID of the tax that has to be deleted
     */
    @Override
    public void delete(String id) {
        taxRepository.delete(id);
    }
}
