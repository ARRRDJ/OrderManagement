package nl.avisi.ordermanagement.service.sender;

import nl.avisi.ordermanagement.domain.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SenderServiceImpl implements SenderService {
    @Autowired
    private SenderRepository senderRepository;
    @Autowired
    private SenderQueryRepository senderQueryRepository;

    /**
     * This function finds all the senders
     *
     * @return Returns the found senders
     **/
    @Override
    public List<Sender> findAll() {
        return senderRepository.findAll();
    }

    /**
     * This function saves a sender
     *
     * @param sender The sender that has to be saved
     * @return Returns the saved sender
     */
    @Override
    public Sender save(Sender sender) {
        return senderRepository.save(sender);
    }

    /**
     * This function finds a specific sender
     *
     * @param id The id of the sender that has to be found
     * @return Returns the found sender
     */
    @Override
    public Sender findOne(String id) {
        return senderRepository.findOne(id);
    }

    /**
     * This function deletes a sender
     *
     * @param id The ID of the sender that has to be deleted
     */
    @Override
    public void delete(String id) {
        senderRepository.delete(id);
    }

    /**
     * This function deletes everything from the sedner database
     */
    @Override
    public void deleteAll() {
        senderQueryRepository.deleteAll();
    }

    /**
     * This function searches for a sender/senders by name
     *
     * @param query The name of the sender/senders that have to be found
     * @return Returns the found sender/senders
     */
    @Override
    public List<Sender> findByName(String query) {
        return senderQueryRepository.findByName(query);
    }
}
