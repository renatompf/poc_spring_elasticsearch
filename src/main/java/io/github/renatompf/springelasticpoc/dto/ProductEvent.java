package io.github.renatompf.springelasticpoc.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ProductCreatedEvent.class, name = "PRODUCT_CREATED"),
    @JsonSubTypes.Type(value = ProductDeletedEvent.class, name = "PRODUCT_DELETED")
})
public sealed interface ProductEvent
    permits ProductCreatedEvent, ProductDeletedEvent {}