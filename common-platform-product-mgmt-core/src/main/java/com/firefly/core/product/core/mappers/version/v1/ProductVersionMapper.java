package com.firefly.core.product.core.mappers.version.v1;

import com.firefly.core.product.interfaces.dtos.version.v1.ProductVersionDTO;
import com.firefly.core.product.models.entities.version.v1.ProductVersion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductVersionMapper {
    ProductVersionDTO toDto(ProductVersion entity);
    ProductVersion toEntity(ProductVersionDTO dto);
}