package lab.mp.service;

import lab.mp.dao.ConversionRepository;
import lab.mp.dao.MessageRepository;
import lab.mp.model.Conversion;
import lab.mp.model.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Tests for message service.
 */
@RunWith(PowerMockRunner.class)
public class MessageServiceImplTest {

    // Class under test
    private MessageServiceImpl messagesService;

    // Mocked classes
    private MessageRepository messageRepository = mock(MessageRepository.class);
    private ConversionRepository conversionRepository = mock(ConversionRepository.class);

    @Before
    public void setup() {
        messagesService = new MessageServiceImpl();
        messagesService.setMessageRepository(messageRepository);
        messagesService.setConversionRepository(conversionRepository);
    }


    /**
     * When there are no messages to processed, empty list should be returned.
     */
    @Test
    public void testGetUnprocessedMessagesEmpty() {

        // Prepare
        when(messageRepository.findUnprocessedMessages()).thenReturn(new ArrayList<>());

        // Execute
        List<Message> messages = messagesService.getUnprocessedMessages();

        // Verify
        Assert.notNull(messages);
        Assert.isTrue(messages.size() == 0);

        verify(messageRepository, times(1)).findUnprocessedMessages();
    }


    /**
     * When there are some messages to processed, these messages should be returned.
     */
    @Test
    public void testGetUnprocessedMessagesNotEmpty() {

        // Prepare
        List<Message> repositoryMessages = new ArrayList<>();
        repositoryMessages.add(
                new Message(
                        "12345",
                        "EUR",
                        "USD",
                        BigDecimal.valueOf(3000),
                        BigDecimal.valueOf(2000),
                        3.2,
                        new Date(),
                        "CRO",
                        false));
        when(messageRepository.findUnprocessedMessages()).thenReturn(repositoryMessages);

        // Execute
        List<Message> messages = messagesService.getUnprocessedMessages();

        // Verify
        Assert.notNull(messages);
        Assert.isTrue(messages.size() == 1);

        verify(messageRepository, times(1)).findUnprocessedMessages();
    }


    /**
     * When message is processed for which conversion does not exist.
     */
    @Test
    public void testProcessMessageForNonexistingConversion() {

        // Prepare
        Message message = new Message(
                                        "12345",
                                        "EUR",
                                        "USD",
                                        BigDecimal.valueOf(3000),
                                        BigDecimal.valueOf(2000),
                                        3.2,
                                        new Date(),
                                        "CRO",
                                        false);
        when(conversionRepository.find("EUR", "USD")).thenReturn(null);

        // Execute
        messagesService.processMessage(message);

        // Verify
        Assert.isTrue(message.isProcessed());

        verify(messageRepository, times(1)).save(message);
        verify(conversionRepository, times(1)).save(any(Conversion.class));
    }


    /**
     * When message is processed for existing conversion.
     */
    @Test
    public void testProcessMessageForExistingConversion() {

        // Prepare
        Message message = new Message(
                "12345",
                "EUR",
                "USD",
                BigDecimal.valueOf(3000),
                BigDecimal.valueOf(2000),
                3.2,
                new Date(),
                "CRO",
                false);
        Conversion conversion = new Conversion("EUR", "USD", 1);

        when(conversionRepository.find("EUR", "USD")).thenReturn(conversion);

        // Execute
        messagesService.processMessage(message);

        // Verify
        Assert.isTrue(message.isProcessed());
        Assert.isTrue(conversion.getCount() == 2);

        verify(messageRepository, times(1)).save(message);
        verify(conversionRepository, times(1)).save(conversion);
    }
}
