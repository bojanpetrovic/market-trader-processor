package lab.mp.service;

import lab.mp.model.Conversion;
import lab.mp.model.Message;

import java.util.List;

/**
 * Messages service.
 */
public interface MessagesService {

    /**
     * Gets all unprocessed messages.
     * @return unprocessed messages if there are any; empty list otherwise.
     */
    List<Message> getUnprocessedMessages();

    /**
     * Processes message.
     * @param message message to be processed.
     */
    void processMessage(Message message);

    /**
     * Gets all conversions.
     * @return conversions if there are any; empty list otherwise.
     */
    List<Conversion> getConversions();
}
