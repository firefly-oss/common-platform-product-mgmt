package com.catalis.core.product.core.mappers.feature.v1;

import com.catalis.core.product.interfaces.dtos.feature.v1.ProductFeatureDTO;
import com.catalis.core.product.models.entities.feature.v1.ProductFeature;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductFeatureMapper {
    ProductFeatureDTO toDto(ProductFeature entity);
    ProductFeature toEntity(ProductFeatureDTO dto);
}
