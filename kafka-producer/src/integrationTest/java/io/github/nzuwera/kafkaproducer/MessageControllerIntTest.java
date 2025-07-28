package io.github.nzuwera.kafkaproducer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class MessageControllerIntTest {

    private static final String TOPIC = "message-topic";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ConsumerFactory<String, String> consumerFactory;


    @Test
    void whenMessageParameterProvided_thenSendSpecifiedMessage() throws Exception {
        // Arrange
        BlockingQueue<ConsumerRecord<String, String>> records = new LinkedBlockingQueue<>();
        KafkaMessageListenerContainer<String, String> container = createMessageListenerContainer(records);
        container.start();
        ContainerTestUtils.waitForAssignment(container, 1);

        String testMessage = "Test message content";

        Consumer<String, String> testConsumer = consumerFactory.createConsumer("messaging-id", "test");
        testConsumer.subscribe(List.of("Test message content"));

        // Act
        mockMvc.perform(post("/messages/send")
                        .param("message", testMessage))
                .andExpect(status().isNoContent());

        // Assert
        ConsumerRecord<String, String> received = records.poll(5, TimeUnit.SECONDS);
        assertNotNull(received);
        assertEquals(testMessage, received.value());

        // Cleanup
        container.stop();
    }

    @Test
    void whenMessageParameterNotProvided_thenSendDefaultMessage() throws Exception {
        // Arrange
        BlockingQueue<ConsumerRecord<String, String>> records = new LinkedBlockingQueue<>();
        KafkaMessageListenerContainer<String, String> container = createMessageListenerContainer(records);
        container.start();
        ContainerTestUtils.waitForAssignment(container, 1);

        // Act
        mockMvc.perform(post("/messages/send"))
                .andExpect(status().isNoContent());

        // Assert
        ConsumerRecord<String, String> received = records.poll(5, TimeUnit.SECONDS);
//        assertNotNull(received);
        assertEquals("Hello World!", received.value());

        // Cleanup
        container.stop();
    }

    private KafkaMessageListenerContainer<String, String> createMessageListenerContainer(
            BlockingQueue<ConsumerRecord<String, String>> records) {
        ContainerProperties containerProperties = new ContainerProperties(TOPIC);
        containerProperties.setMessageListener((MessageListener<String, String>) records::add);
        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }
}
