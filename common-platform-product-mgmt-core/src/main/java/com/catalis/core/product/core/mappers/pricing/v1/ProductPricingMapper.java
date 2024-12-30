package com.catalis.core.product.core.mappers.pricing.v1;

import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingDTO;
import com.catalis.core.product.models.entities.pricing.v1.ProductPricing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductPricingMapper {
    ProductPricingDTO toDto(ProductPricing entity);
    ProductPricing toEntity(ProductPricingDTO dto);
}
