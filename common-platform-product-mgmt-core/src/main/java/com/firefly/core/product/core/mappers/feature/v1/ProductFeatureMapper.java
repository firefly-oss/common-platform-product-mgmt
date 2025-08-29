package com.firefly.core.product.core.mappers.feature.v1;

import com.firefly.core.product.interfaces.dtos.feature.v1.ProductFeatureDTO;
import com.firefly.core.product.models.entities.feature.v1.ProductFeature;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductFeatureMapper {
    ProductFeatureDTO toDto(ProductFeature entity);
    ProductFeature toEntity(ProductFeatureDTO dto);
}
