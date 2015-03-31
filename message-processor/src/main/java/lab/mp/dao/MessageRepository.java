package lab.mp.dao;

import lab.mp.model.Message;

import java.util.List;


/**
 * Repository for messages.
 */
public interface MessageRepository {

    /**
     * Finds all messages that are not processed yet.
     * @return list of messages if there are any; empty list otherwise.
     */
    List<Message> findUnprocessedMessages();

    /**
     * Save message.
     * @param message message to be saved.
     */
    void save(Message message);
}
