package com.catalis.core.product.core.mappers.documentation.v1;

import com.catalis.core.product.interfaces.dtos.documentation.v1.ProductDocumentationDTO;
import com.catalis.core.product.models.entities.documentation.v1.ProductDocumentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDocumentationMapper {
    ProductDocumentationDTO toDto(ProductDocumentation entity);
    ProductDocumentation toEntity(ProductDocumentationDTO dto);
}