package io.github.nzuwera.kafkaconsumer;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import static io.github.nzuwera.kafkaconsumer.service.IMessagingService.MESSAGE_NAME;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("intTest")
@AutoConfigureMockMvc(addFilters = false)
@EmbeddedKafka(
        topics = {MESSAGE_NAME},
        partitions = 1)
public class MessageControllerIntTest {
}
