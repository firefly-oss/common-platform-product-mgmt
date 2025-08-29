package com.firefly.core.product.core.mappers.fee.v1;

import com.firefly.core.product.interfaces.dtos.fee.v1.FeeComponentDTO;
import com.firefly.core.product.models.entities.fee.v1.FeeComponent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeeComponentMapper {
    FeeComponentDTO toDto(FeeComponent entity);
    FeeComponent toEntity(FeeComponentDTO dto);
}
