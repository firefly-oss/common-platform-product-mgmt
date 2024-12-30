package com.catalis.core.product.core.mappers.fee.v1;

import com.catalis.core.product.interfaces.dtos.fee.v1.FeeStructureDTO;
import com.catalis.core.product.models.entities.fee.v1.FeeStructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeeStructureMapper {
    FeeStructureDTO toDto(FeeStructure entity);
    FeeStructure toEntity(FeeStructureDTO dto);
}
