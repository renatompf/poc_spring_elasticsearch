package io.github.renatompf.springelasticpoc.mapper;

import io.github.renatompf.springelasticpoc.dto.ProductCreateDTO;
import io.github.renatompf.springelasticpoc.model.ProductEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductEntityCreateDtoMapper {
    ProductEntity toEntity(ProductCreateDTO productCreateDTO);

    ProductCreateDTO toDto(ProductEntity productEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductEntity partialUpdate(ProductCreateDTO productCreateDTO, @MappingTarget ProductEntity productEntity);
}