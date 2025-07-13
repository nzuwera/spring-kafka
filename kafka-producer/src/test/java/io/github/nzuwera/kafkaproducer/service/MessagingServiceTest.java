package io.github.nzuwera.kafkaproducer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessagingServiceTest {

    @Mock
    private IMessagingService messagingServiceMock;


    @Test
    void send() {
        messagingServiceMock.send("Hello World!");
        Mockito.verify(messagingServiceMock).send("Hello World!");
    }

}