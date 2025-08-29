package com.firefly.core.product.core.mappers.category.v1;

import com.firefly.core.product.interfaces.dtos.category.v1.ProductCategorySubtypeDTO;
import com.firefly.core.product.models.entities.category.v1.ProductSubtype;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductSubtypeMapper {
    ProductCategorySubtypeDTO toDto(ProductSubtype entity);
    ProductSubtype toEntity(ProductCategorySubtypeDTO dto);
}
