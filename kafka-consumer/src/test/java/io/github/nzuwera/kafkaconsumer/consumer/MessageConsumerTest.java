package io.github.nzuwera.kafkaconsumer.consumer;

import io.github.nzuwera.kafkaconsumer.service.IMessagingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageConsumerTest {

    @Mock
    private IMessagingService messagingService;

    @InjectMocks
    private MessageConsumer messageConsumer;

    @Test
    void receiveMessage_shouldDelegateToMessagingService() {
        // Arrange
        String testMessage = "Test message";

        // Act
        messageConsumer.receiveMessage(testMessage);

        // Assert
        Mockito.verify(messagingService).receiveMessage(testMessage);
    }
}