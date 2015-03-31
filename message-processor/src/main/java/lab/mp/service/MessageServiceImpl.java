package lab.mp.service;

import lab.mp.dao.ConversionRepository;
import lab.mp.dao.MessageRepository;
import lab.mp.model.Conversion;
import lab.mp.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MessageServiceImpl implements MessagesService {
    @Override
    public List<Message> getUnprocessedMessages() {

        return messageRepository.findUnprocessedMessages();
    }

    @Override
    public void processMessage(Message message) {

        // 1. get conversion for currencies in message.
        Conversion conversion = conversionRepository.find(message.getCurrencyFrom(), message.getCurrencyTo());

        // 2. if none is found, create new conversion
        if (conversion == null) {
            conversion = new Conversion(message.getCurrencyFrom(), message.getCurrencyTo(), 0);
        }

        // 3. increase count for conversion
        conversion.setCount(conversion.getCount() + 1);

        // 4. mark message as processed, so it won't be processed again
        message.setProcessed(true);

        // 5. save updated message
        messageRepository.save(message);

        // 6. save new/updated conversion
        conversionRepository.save(conversion);
    }

    public MessageRepository getMessageRepository() {
        return messageRepository;
    }

    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public ConversionRepository getConversionRepository() {
        return conversionRepository;
    }

    public void setConversionRepository(ConversionRepository conversionRepository) {
        this.conversionRepository = conversionRepository;
    }

    @Override
    public List<Conversion> getConversions() {
        return conversionRepository.getConversions();
    }

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversionRepository conversionRepository;
}
