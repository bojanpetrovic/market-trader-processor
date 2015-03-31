package lab.mp.tasks;

import java.util.List;

import lab.mp.model.Conversion;
import lab.mp.observer.MessageObserver;
import lab.mp.model.Message;
import lab.mp.service.MessagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProcessMessagesTask {

    Logger logger = LoggerFactory.getLogger(ProcessMessagesTask.class);

    @Scheduled(fixedRate = 5000)
    public void processMessages() {

        logger.debug("Started processing messages.");

        // Get all unprocessed messages
        List<Message> messages = messagesService.getUnprocessedMessages();

        logger.debug("Messages to process are: {}", messages);

        // Process messages
        for (Message message : messages) {

            // Process messages
            messagesService.processMessage(message);
        }

        logger.debug("Processing done.");

        // Send notifications
        List<Conversion> conversions = messagesService.getConversions();

        logger.debug("Sending these conversions to observers: {}", conversions);

        for (MessageObserver observer : messageObservers) {
            try {
                observer.report(conversions);
            }
            catch (Exception e) {
                logger.error("Something is wrong with observer: {}", e.getMessage());
                logger.debug("{}", e);
            }
        }
    }

    public List<MessageObserver> getMessageObservers() {
        return messageObservers;
    }

    public void setMessageObservers(List<MessageObserver> messageObservers) {
        this.messageObservers = messageObservers;
    }

    public MessagesService getMessagesService() {
        return messagesService;
    }

    public void setMessagesService(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @Autowired
    private List<MessageObserver> messageObservers;

    @Autowired
    private MessagesService messagesService;
}
