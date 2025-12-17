package io.github.renatompf.springelasticpoc.repository.elastic;

import io.github.renatompf.springelasticpoc.model.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, UUID> {
    Page<ProductDocument> findByNameContainingOrDescriptionContainingOrTagsContaining(String name, String description, String tag, Pageable pageable);
}