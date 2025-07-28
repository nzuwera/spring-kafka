package io.github.nzuwera.kafkaconsumer.consumer;

import io.github.nzuwera.kafkaconsumer.service.IMessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static io.github.nzuwera.kafkaconsumer.service.IMessagingService.MESSAGE_NAME;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {
    private final IMessagingService messagingService;


    @KafkaListener(topics = MESSAGE_NAME,groupId = "messaging-id")
    public void receiveMessage(String message) {
        messagingService.receiveMessage(message);
    }
}
