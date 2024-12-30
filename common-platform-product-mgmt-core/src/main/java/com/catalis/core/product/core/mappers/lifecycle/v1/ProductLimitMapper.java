package com.catalis.core.product.core.mappers.lifecycle.v1;

import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLimitDTO;
import com.catalis.core.product.models.entities.lifecycle.v1.ProductLimit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductLimitMapper {
    ProductLimitDTO toDto(ProductLimit entity);
    ProductLimit toEntity(ProductLimitDTO dto);
}