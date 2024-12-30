package com.catalis.core.product.core.mappers.category.v1;

import com.catalis.core.product.interfaces.dtos.category.v1.ProductSubtypeDTO;
import com.catalis.core.product.models.entities.category.v1.ProductSubtype;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductSubtypeMapper {
    ProductSubtypeDTO toDto(ProductSubtype entity);
    ProductSubtype toEntity(ProductSubtypeDTO dto);
}
