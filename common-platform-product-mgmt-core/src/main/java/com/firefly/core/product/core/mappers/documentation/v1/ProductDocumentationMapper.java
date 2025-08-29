package com.firefly.core.product.core.mappers.documentation.v1;

import com.firefly.core.product.interfaces.dtos.documentation.v1.ProductDocumentationDTO;
import com.firefly.core.product.models.entities.documentation.v1.ProductDocumentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDocumentationMapper {
    ProductDocumentationDTO toDto(ProductDocumentation entity);
    ProductDocumentation toEntity(ProductDocumentationDTO dto);
}