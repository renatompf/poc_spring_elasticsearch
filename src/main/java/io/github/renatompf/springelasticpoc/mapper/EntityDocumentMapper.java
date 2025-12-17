package io.github.renatompf.springelasticpoc.mapper;

import io.github.renatompf.springelasticpoc.model.ProductDocument;
import io.github.renatompf.springelasticpoc.model.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityDocumentMapper {

    ProductDocument toDocument(ProductEntity productEntity);

    ProductEntity toEntity(ProductDocument productDocument);

}
