package com.firefly.core.product.core.mappers.fee.v1;

import com.firefly.core.product.interfaces.dtos.fee.v1.FeeStructureDTO;
import com.firefly.core.product.models.entities.fee.v1.FeeStructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeeStructureMapper {
    FeeStructureDTO toDto(FeeStructure entity);
    FeeStructure toEntity(FeeStructureDTO dto);
}
