package com.firefly.core.product.core.mappers.lifecycle.v1;

import com.firefly.core.product.interfaces.dtos.lifecycle.v1.ProductLimitDTO;
import com.firefly.core.product.models.entities.lifecycle.v1.ProductLimit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductLimitMapper {
    ProductLimitDTO toDto(ProductLimit entity);
    ProductLimit toEntity(ProductLimitDTO dto);
}