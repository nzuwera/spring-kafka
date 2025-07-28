package io.github.nzuwera.kafkaconsumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessagingService implements  IMessagingService {
    @Override
    public void receiveMessage(String message) {
        log.info("Received Message: {}", message);
    }
}
