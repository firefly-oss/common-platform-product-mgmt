package com.catalis.core.product.core.mappers.relationship.v1;

import com.catalis.core.product.models.entities.relationship.v1.ProductRelationship;
import com.catalis.core.product.interfaces.dtos.relationship.v1.ProductRelationshipDTO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRelationshipMapper {
    ProductRelationshipDTO toDto(ProductRelationship entity);
    ProductRelationship toEntity(ProductRelationshipDTO dto);
}