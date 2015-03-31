package lab.mp.dao;

import lab.mp.model.Conversion;
import lab.mp.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Mongo repository for messages.
 */
@Repository
public class MongoMessageRepository implements MessageRepository {

    @Override
    public List<Message> findUnprocessedMessages() {
        return messageRepo.findUnprocessedMessages();
    }

    @Override
    public void save(Message message) {
        messageRepo.save(message);
    }


    @Autowired private MessageRepo messageRepo;
}

interface MessageRepo extends MongoRepository<Message, String>{

    @Query(value="{ 'processed' : false }")
    public List<Message> findUnprocessedMessages();
}
