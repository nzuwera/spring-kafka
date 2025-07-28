package io.github.nzuwera.kafkaproducer.service;

import io.github.nzuwera.kafkaproducer.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessagingService implements IMessagingService{

    private final MessageProducer messageProducer;

    @Override
    public void send(String message) {
        messageProducer.sendMessage(message);
    }
}
