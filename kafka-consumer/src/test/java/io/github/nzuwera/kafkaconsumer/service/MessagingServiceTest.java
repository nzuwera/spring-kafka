package io.github.nzuwera.kafkaconsumer.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessagingServiceTest {

    private MessagingService messagingService;
    private ListAppender<ILoggingEvent> listAppender;
    private Logger logger;

    @BeforeEach
    void setUp() {
        messagingService = new MessagingService();
        
        // Get the logger for MessagingService
        logger = (Logger) LoggerFactory.getLogger(MessagingService.class);
        
        // Create and start a ListAppender to capture log messages
        listAppender = new ListAppender<>();
        listAppender.start();
        
        // Add the appender to the logger
        logger.addAppender(listAppender);
    }

    @Test
    void receiveMessage_shouldLogMessage() {
        // Arrange
        String testMessage = "Test message";
        
        // Act
        messagingService.receiveMessage(testMessage);
        
        // Assert
        // Verify that a log message was created
        assertEquals(1, listAppender.list.size());
        
        // Verify the log level
        assertEquals(Level.INFO, listAppender.list.get(0).getLevel());
        
        // Verify the log message content
        String logMessage = listAppender.list.get(0).getFormattedMessage();
        assertTrue(logMessage.contains("Received Message: " + testMessage));
    }
}