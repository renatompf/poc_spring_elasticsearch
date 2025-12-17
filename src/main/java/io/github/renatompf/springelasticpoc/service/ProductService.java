package io.github.renatompf.springelasticpoc.service;

import io.github.renatompf.springelasticpoc.dto.ProductCreateDTO;
import io.github.renatompf.springelasticpoc.dto.ProductCreatedEvent;
import io.github.renatompf.springelasticpoc.dto.ProductDeletedEvent;
import io.github.renatompf.springelasticpoc.dto.ProductEvent;
import io.github.renatompf.springelasticpoc.mapper.ProductEntityCreateDtoMapper;
import io.github.renatompf.springelasticpoc.messaging.producer.ProductSyncProducer;
import io.github.renatompf.springelasticpoc.model.ProductDocument;
import io.github.renatompf.springelasticpoc.model.ProductEntity;
import io.github.renatompf.springelasticpoc.repository.elastic.ProductSearchRepository;
import io.github.renatompf.springelasticpoc.repository.jpa.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;

import java.util.UUID;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final JsonMapper jsonMapper = new JsonMapper();

    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;
    private final ProductEntityCreateDtoMapper productEntityCreateDtoMapper;
    private final ProductSyncProducer productSyncProducer;

    public ProductService(ProductRepository productRepository, ProductSearchRepository productSearchRepository, ProductEntityCreateDtoMapper productEntityCreateDtoMapper, ProductSyncProducer productSyncProducer) {
        this.productRepository = productRepository;
        this.productSearchRepository = productSearchRepository;
        this.productEntityCreateDtoMapper = productEntityCreateDtoMapper;
        this.productSyncProducer = productSyncProducer;
    }

    @Transactional
    public void saveProduct(ProductCreateDTO product) {

        ProductEntity entityToSave = productEntityCreateDtoMapper.toEntity(product);
        LOGGER.info("Product to save: {}", entityToSave);

        ProductEntity saved = productRepository.save(entityToSave);
        LOGGER.info("Product saved in DB: {}", saved);

        // sync with ElasticSearch DB
        ProductEvent event = new ProductCreatedEvent(saved);
        productSyncProducer.send(jsonMapper.writeValueAsString(event));

        LOGGER.info("Product sent to sync: {}", saved);
    }

    public Page<ProductEntity> getAll(Pageable pageable) {
        return productRepository
                .findAll(pageable);
    }

    public Page<ProductDocument> getByNameAndDescription(String termo, Pageable pageable) {
        return productSearchRepository
                .findByNameContainingOrDescriptionContainingOrTagsContaining(termo, termo, termo, pageable);
    }

    @Transactional
    public void delete(UUID id) {
        productRepository.deleteById(id);

        // sync delete with ElasticSearch DB
        ProductEvent event = new ProductDeletedEvent(id);
        productSyncProducer.send(jsonMapper.writeValueAsString(event));
    }
}