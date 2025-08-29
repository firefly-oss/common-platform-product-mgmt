package com.firefly.core.product.core.mappers.lifecycle.v1;

import com.firefly.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import com.firefly.core.product.models.entities.lifecycle.v1.ProductLifecycle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductLifecycleMapper {
    ProductLifecycleDTO toDto(ProductLifecycle entity);
    ProductLifecycle toEntity(ProductLifecycleDTO dto);
}