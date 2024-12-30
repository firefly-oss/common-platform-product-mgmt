package com.catalis.core.product.core.mappers.bundle.v1;

import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import com.catalis.core.product.models.entities.bundle.v1.ProductBundle;
import com.catalis.core.product.models.entities.bundle.v1.ProductBundleItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductBundleItemMapper {
    ProductBundleItemDTO toDto(ProductBundleItem entity);
    ProductBundleItem toEntity(ProductBundleItemDTO dto);
}