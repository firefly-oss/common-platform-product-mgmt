package com.catalis.core.product.core.mappers.lifecycle.v1;

import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import com.catalis.core.product.models.entities.lifecycle.v1.ProductLifecycle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductLifecycleMapper {
    ProductLifecycleDTO toDto(ProductLifecycle entity);
    ProductLifecycle toEntity(ProductLifecycleDTO dto);
}