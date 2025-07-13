package io.github.nzuwera.kafkaproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessagingService implements IMessagingService{
    @Override
    public void send(String message) {
        log.info("Sending message: {}", message);
    }
}
