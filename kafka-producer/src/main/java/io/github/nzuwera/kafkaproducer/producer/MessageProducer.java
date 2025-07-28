package io.github.nzuwera.kafkaproducer.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    static final String MESSAGE_NAME = "message-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(MESSAGE_NAME, message);
        log.info("Message sent to topic: {} - '{}'", MESSAGE_NAME, message);
    }
}
