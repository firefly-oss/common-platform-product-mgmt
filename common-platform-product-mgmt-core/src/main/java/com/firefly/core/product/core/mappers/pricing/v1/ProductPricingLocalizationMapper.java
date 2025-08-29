package com.firefly.core.product.core.mappers.pricing.v1;

import com.firefly.core.product.interfaces.dtos.pricing.v1.ProductPricingLocalizationDTO;
import com.firefly.core.product.models.entities.pricing.v1.ProductPricingLocalization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductPricingLocalizationMapper {
    ProductPricingLocalizationDTO toDto(ProductPricingLocalization entity);
    ProductPricingLocalization toEntity(ProductPricingLocalizationDTO dto);
}
