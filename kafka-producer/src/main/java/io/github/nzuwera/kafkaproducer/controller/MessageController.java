package io.github.nzuwera.kafkaproducer.controller;

import io.github.nzuwera.kafkaproducer.service.IMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final IMessagingService messagingService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestParam Optional<String> message) {
        message.ifPresentOrElse(
                messagingService::send,
                () -> messagingService.send("Hello World!")
        );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
