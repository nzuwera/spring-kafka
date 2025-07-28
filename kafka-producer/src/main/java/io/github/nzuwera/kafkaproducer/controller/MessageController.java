package io.github.nzuwera.kafkaproducer.controller;

import io.github.nzuwera.kafkaproducer.service.IMessagingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final IMessagingService messagingService;

    public MessageController(IMessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestParam(name = "message", required = false) String message) {
        messagingService.send(message != null ? message : "Hello World!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
