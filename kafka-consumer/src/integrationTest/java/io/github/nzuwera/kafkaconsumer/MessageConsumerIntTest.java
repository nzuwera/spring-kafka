package io.github.nzuwera.kafkaconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.TimeUnit;

import static io.github.nzuwera.kafkaconsumer.service.IMessagingService.MESSAGE_NAME;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@DirtiesContext
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.auto-offset-reset=earliest"
})
@EmbeddedKafka(
        topics = {MESSAGE_NAME},
        partitions = 1,
        ports = {9092},
        brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"}
)
public class MessageConsumerIntTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void shouldConsumeMessage() {
        // given
        String message = "Test message";

        // when
        kafkaTemplate.send(MESSAGE_NAME, message);

        // then
        await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    // Add assertions here to verify message processing

                    // For example, verify that message was processed by your consumer
                    // You might want to autowire your consumer service and verify its state
                });
    }
}