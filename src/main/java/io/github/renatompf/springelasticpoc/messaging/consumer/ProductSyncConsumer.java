package io.github.renatompf.springelasticpoc.messaging.consumer;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.renatompf.springelasticpoc.dto.ProductCreatedEvent;
import io.github.renatompf.springelasticpoc.dto.ProductDeletedEvent;
import io.github.renatompf.springelasticpoc.dto.ProductEvent;
import io.github.renatompf.springelasticpoc.mapper.EntityDocumentMapper;
import io.github.renatompf.springelasticpoc.repository.elastic.ProductSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductSyncConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSyncConsumer.class);
    private final JsonMapper jsonMapper = new JsonMapper();

    private final EntityDocumentMapper entityDocumentMapper;
    private final ProductSearchRepository productSearchRepository;

    public ProductSyncConsumer(EntityDocumentMapper entityDocumentMapper, ProductSearchRepository productSearchRepository) {
        this.entityDocumentMapper = entityDocumentMapper;
        this.productSearchRepository = productSearchRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic.product-sync}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String productToSync) {
        LOGGER.info("Received new product: {}", productToSync);

        try {
            ProductEvent event =
                    jsonMapper.readValue(productToSync, ProductEvent.class);

            switch (event) {
                case ProductCreatedEvent(var product) ->{
                    LOGGER.info("Product to save: {}", product);
                    productSearchRepository.save(entityDocumentMapper.toDocument(product));
                    LOGGER.info("Product saved in Elasticsearch");
                }
                case ProductDeletedEvent(var id) ->{
                    LOGGER.info("Product to delete: {}", id);
                    productSearchRepository.deleteById(id);
                    LOGGER.info("Product deleted from Elasticsearch");
                }
                default ->
                        LOGGER.info("Received unhandled event type: {}", event.getClass());
            }
        } catch (Exception e) {
            // IMPORTANT:
            // Ideally, we should retry to save the product in Elasticsearch
            // Maybe sending to a dead letter queue and try to reprocess later
            LOGGER.error("Error saving product in Elasticsearch: {}", productToSync, e);
        }

    }

}
