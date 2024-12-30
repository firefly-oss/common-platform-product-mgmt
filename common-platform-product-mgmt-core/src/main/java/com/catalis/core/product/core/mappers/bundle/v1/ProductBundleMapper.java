package com.catalis.core.product.core.mappers.bundle.v1;

import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
import com.catalis.core.product.models.entities.bundle.v1.ProductBundle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductBundleMapper {
    ProductBundleDTO toDto(ProductBundle entity);
    ProductBundle toEntity(ProductBundleDTO dto);
}