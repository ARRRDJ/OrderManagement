package nl.avisi.ordermanagement.service.contactPerson;

import nl.avisi.ordermanagement.domain.ContactPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContactPersonServiceImpl implements ContactPersonService {
    @Autowired
    private ContactPersonRepository contactPersonRepository;
    @Autowired
    private ContactPersonQueryRepository contactPersonQueryRepository;


    public List<ContactPerson> findAll() {
        return contactPersonRepository.findAll();
    }

    @Override
    public ContactPerson save(ContactPerson contactPerson) {
        return contactPersonRepository.save(contactPerson);
    }

    @Override
    public ContactPerson findOne(String id) {
        return contactPersonRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        contactPersonRepository.delete(id);
    }

    @Override
    public List<ContactPerson> findByFirstName(String name) {
        return contactPersonQueryRepository.findByFirstName(name);
    }

    @Override
    public List<ContactPerson> findByLastName(String lastName) {
        return contactPersonQueryRepository.findByLastName(lastName);
    }

    @Override
    public List<ContactPerson> findByEmail(String email) {
        return contactPersonQueryRepository.findByEmail(email);
    }

    @Override
    public void dropCollection() {
        contactPersonQueryRepository.dropCollection();
    }

    @Override
    public void deleteAll() {
        contactPersonQueryRepository.deleteAll();
    }
}
