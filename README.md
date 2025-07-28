# Spring Kafka Tutorial

This project demonstrates how to use Spring Kafka for producing and consuming messages with Spring Boot applications.

## Overview

The project consists of two Spring Boot applications:
- **kafka-producer**: A service that produces messages to a Kafka topic
- **kafka-consumer**: A service that consumes messages from the same Kafka topic

## Getting Started

### Prerequisites

- Java 21
- Docker and Docker Compose
- Gradle

### Setting Up Kafka

The project includes a Docker Compose file to set up Kafka, Zookeeper, and Kafka UI:

1. Navigate to the docker-compose directory:
   ```
   cd docker-compose
   ```

2. Start the Kafka environment:
   ```
   docker-compose -f kafka.yaml up -d
   ```

3. Verify that all services are running:
   ```
   docker-compose -f kafka.yaml ps
   ```

4. You can access the Kafka UI at http://localhost:8080 to monitor topics and messages.

### Building and Running the Applications

#### Kafka Producer

1. Navigate to the kafka-producer directory:
   ```
   cd kafka-producer
   ```

2. Build the application:
   ```
   ./gradlew build
   ```

3. Run the application:
   ```
   ./gradlew bootRun
   ```

The producer will start on port 8081.

#### Kafka Consumer

1. Navigate to the kafka-consumer directory:
   ```
   cd kafka-consumer
   ```

2. Build the application:
   ```
   ./gradlew build
   ```

3. Run the application:
   ```
   ./gradlew bootRun
   ```

The consumer will start on port 8082.

### Using the Applications

1. Send a message using the producer:
   ```
   curl -X POST "http://localhost:8081/messages/send?message=Hello%20Kafka"
   ```

   Or send a default "Hello World!" message:
   ```
   curl -X POST "http://localhost:8081/messages/send"
   ```

2. The consumer will automatically receive the message and log it.

3. You can verify message delivery in the Kafka UI at http://localhost:8080.

## Integration Tests

The project includes integration tests for both the producer and consumer applications. These tests use Spring's embedded Kafka broker to test the Kafka integration without requiring an external Kafka instance.

### Running Integration Tests

#### Producer Integration Tests

1. Navigate to the kafka-producer directory:
   ```
   cd kafka-producer
   ```

2. Run the integration tests:
   ```
   ./gradlew integrationTest
   ```

The tests verify that:
- The producer correctly sends messages to the Kafka topic
- Both custom messages and the default message work correctly

#### Consumer Integration Tests

1. Navigate to the kafka-consumer directory:
   ```
   cd kafka-consumer
   ```

2. Run the integration tests:
   ```
   ./gradlew integrationTest
   ```

The tests verify that:
- The consumer correctly receives messages from the Kafka topic
- The received messages are properly processed and logged

### Test Implementation Details

- The integration tests use `@EmbeddedKafka` to set up an in-memory Kafka broker
- Tests run with the "test" profile, which may have specific configuration for testing
- The producer tests use MockMvc to simulate HTTP requests
- The consumer tests send messages directly to Kafka and verify log output
