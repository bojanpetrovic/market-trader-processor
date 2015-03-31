package lab.mp.tasks;

import lab.mp.model.Conversion;
import lab.mp.model.Message;
import lab.mp.observer.MessageObserver;
import lab.mp.service.MessagesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

/**
 * Tests for task involved in processing messages.
 */
@RunWith(PowerMockRunner.class)
public class ProcessMessagesTaskTest {

    // Class under test
    private ProcessMessagesTask processMessagesTask;

    // Mocked classes
    private MessagesService messagesService = mock(MessagesService.class);
    private MessageObserver messageObserver = mock(MessageObserver.class);

    @Before
    public void setup() {
        processMessagesTask = new ProcessMessagesTask();

        when(messagesService.getConversions()).thenReturn(Arrays.asList(new Conversion("USD", "EUR", 2)));

        processMessagesTask.setMessagesService(messagesService);
        processMessagesTask.setMessageObservers(Arrays.asList(messageObserver));
    }


    /**
     * When there are no messages to be processed, nothing should be processed.
     */
    @Test
    public void testProcessMessagesWhenNoMessagesAreThere() {

        // Prepare
        when(messagesService.getUnprocessedMessages())
                .thenReturn(new ArrayList<>());

        // Execute
        processMessagesTask.processMessages();

        // Verify
        verify(messagesService, times(1)).getUnprocessedMessages();
        verify(messagesService, times(0)).processMessage(any(Message.class));
        verify(messagesService, times(1)).getConversions();
        verify(messageObserver, times(1)).report(anyList());
    }


    /**
     * When there are is one message to be processed.
     */
    @Test
    public void testProcessOneMessage() {

        // Prepare
        when(messagesService.getUnprocessedMessages())
                .thenReturn(
                        Arrays.asList(
                                new Message(
                                        "12345",
                                        "EUR",
                                        "USD",
                                        BigDecimal.valueOf(3000),
                                        BigDecimal.valueOf(2000),
                                        3.2,
                                        new Date(),
                                        "CRO",
                                        false)));

        // Execute
        processMessagesTask.processMessages();

        // Verify
        verify(messagesService, times(1)).getUnprocessedMessages();
        verify(messagesService, times(1)).processMessage(any(Message.class));
        verify(messagesService, times(1)).getConversions();
        verify(messageObserver, times(1)).report(anyList());
    }
}
