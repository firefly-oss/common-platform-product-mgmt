package com.firefly.core.product.core.mappers.localization.v1;

import com.firefly.core.product.interfaces.dtos.localization.v1.ProductLocalizationDTO;
import com.firefly.core.product.models.entities.localization.v1.ProductLocalization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductLocalizationMapper {
    ProductLocalizationDTO toDto(ProductLocalization entity);
    ProductLocalization toEntity(ProductLocalizationDTO dto);
}
