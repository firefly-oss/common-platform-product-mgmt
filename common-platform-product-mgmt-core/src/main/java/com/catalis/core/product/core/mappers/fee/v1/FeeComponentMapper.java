package com.catalis.core.product.core.mappers.fee.v1;

import com.catalis.core.product.interfaces.dtos.fee.v1.FeeComponentDTO;
import com.catalis.core.product.models.entities.fee.v1.FeeComponent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeeComponentMapper {
    FeeComponentDTO toDto(FeeComponent entity);
    FeeComponent toEntity(FeeComponentDTO dto);
}
