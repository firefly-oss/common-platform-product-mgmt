package com.catalis.core.product.core.mappers.category.v1;

import com.catalis.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
import com.catalis.core.product.models.entities.category.v1.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {
    ProductCategoryDTO toDto(ProductCategory entity);
    ProductCategory toEntity(ProductCategoryDTO dto);
}
