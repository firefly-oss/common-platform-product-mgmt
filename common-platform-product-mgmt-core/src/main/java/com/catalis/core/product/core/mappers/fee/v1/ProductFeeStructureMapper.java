package com.catalis.core.product.core.mappers.fee.v1;

import com.catalis.core.product.interfaces.dtos.fee.v1.ProductFeeStructureDTO;
import com.catalis.core.product.models.entities.fee.v1.ProductFeeStructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductFeeStructureMapper {
    ProductFeeStructureDTO toDto(ProductFeeStructure entity);
    ProductFeeStructure toEntity(ProductFeeStructureDTO dto);
}