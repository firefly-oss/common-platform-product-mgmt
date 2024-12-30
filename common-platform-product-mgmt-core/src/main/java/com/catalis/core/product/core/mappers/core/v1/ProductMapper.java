package com.catalis.core.product.core.mappers.core.v1;

import com.catalis.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.catalis.core.product.models.entities.core.v1.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product entity);
    Product toEntity(ProductDTO dto);
}