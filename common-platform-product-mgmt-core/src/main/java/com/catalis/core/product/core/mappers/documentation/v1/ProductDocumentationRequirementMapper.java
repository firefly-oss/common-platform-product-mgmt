package com.catalis.core.product.core.mappers.documentation.v1;

import com.catalis.core.product.interfaces.dtos.documentation.v1.ProductDocumentationRequirementDTO;
import com.catalis.core.product.models.entities.documentation.v1.ProductDocumentationRequirement;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between ProductDocumentationRequirement entity and DTO.
 */
@Mapper(componentModel = "spring")
public interface ProductDocumentationRequirementMapper {
    /**
     * Convert entity to DTO.
     *
     * @param entity The entity to convert
     * @return The DTO
     */
    ProductDocumentationRequirementDTO toDto(ProductDocumentationRequirement entity);
    
    /**
     * Convert DTO to entity.
     *
     * @param dto The DTO to convert
     * @return The entity
     */
    ProductDocumentationRequirement toEntity(ProductDocumentationRequirementDTO dto);
}