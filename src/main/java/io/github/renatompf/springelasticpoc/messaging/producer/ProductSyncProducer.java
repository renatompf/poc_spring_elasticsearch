package io.github.renatompf.springelasticpoc.messaging.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductSyncProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSyncProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.product-sync}")
    private String topic;

    public ProductSyncProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message) {

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                LOGGER.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            } else {
                LOGGER.info("Unable to send message=[{}] due to : {}", message, ex.getMessage());
            }
        });

    }

}
