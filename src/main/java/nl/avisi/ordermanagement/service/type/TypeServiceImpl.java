package nl.avisi.ordermanagement.service.type;

import nl.avisi.ordermanagement.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    /**
     * This function finds all the types
     *
     * @return Returns the found types
     **/
    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    /**
     * This function saves a type
     *
     * @param type The type that has to be saved
     * @return Returns the saved type
     */
    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    /**
     * This function finds a specific type
     *
     * @param id The id of the type that has to be found
     * @return Returns the found type
     */
    @Override
    public Type findOne(String id) {
        return typeRepository.findOne(id);
    }

    /**
     * This function deletes a type
     *
     * @param id The ID of the type that has to be deleted
     */
    @Override
    public void delete(String id) {
        typeRepository.delete(id);
    }

}
