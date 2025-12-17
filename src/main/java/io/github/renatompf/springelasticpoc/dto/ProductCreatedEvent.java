package io.github.renatompf.springelasticpoc.dto;

import io.github.renatompf.springelasticpoc.model.ProductEntity;

public record ProductCreatedEvent(ProductEntity product)
    implements ProductEvent {}
