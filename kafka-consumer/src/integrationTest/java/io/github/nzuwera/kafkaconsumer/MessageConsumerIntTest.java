package io.github.nzuwera.kafkaconsumer;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.github.nzuwera.kafkaconsumer.service.MessagingService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.github.nzuwera.kafkaconsumer.service.IMessagingService.MESSAGE_NAME;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the Kafka consumer.
 * This test verifies that messages sent to the Kafka topic are properly consumed and logged.
 */
@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, topics = MESSAGE_NAME, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class MessageConsumerIntTest {

    private KafkaTemplate<String, String> kafkaTemplate;
    private ListAppender<ILoggingEvent> listAppender;
    private Logger logger;

    @BeforeEach
    void setUp() {
        // Get the logger for MessagingService
        logger = (Logger) LoggerFactory.getLogger(MessagingService.class);

        // Create and start a ListAppender to capture log messages
        listAppender = new ListAppender<>();
        listAppender.start();

        // Add the appender to the logger
        logger.addAppender(listAppender);

        // Create a KafkaTemplate manually
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(configProps);
        kafkaTemplate = new KafkaTemplate<>(producerFactory);
    }

    @AfterEach
    void tearDown() {
        // Remove the appender from the logger
        logger.detachAppender(listAppender);
    }

    @Test
    void testMessageConsumerIntegration() {
        // Arrange
        String testMessage = "Test message for Kafka consumer";

        // Act - Send a message to the Kafka topic
        kafkaTemplate.send(MESSAGE_NAME, testMessage);

        // Assert - Verify that the message is consumed and logged
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            // Check that we have at least one log message
            assertFalse(listAppender.list.isEmpty(), "No log messages were captured");

            // Find the log message containing our test message
            boolean foundMessage = false;
            for (ILoggingEvent event : listAppender.list) {
                if (event.getFormattedMessage().contains(testMessage)) {
                    assertEquals(Level.INFO, event.getLevel(), "Log level should be INFO");
                    foundMessage = true;
                    break;
                }
            }

            assertTrue(foundMessage, "Expected log message was not found");
        });
    }
}
