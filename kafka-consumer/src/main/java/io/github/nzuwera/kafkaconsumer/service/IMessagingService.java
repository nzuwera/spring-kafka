package io.github.nzuwera.kafkaconsumer.service;

public interface IMessagingService {
    static final String MESSAGE_NAME = "message-topic";
    void receiveMessage(String message);
}
