package com.catalis.core.product.interfaces.dtos.pricing.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPricingLocalizationDTO extends BaseDTO {
    private Long productPricingLocalizationId;
    private Long productPricingId;
    private String currencyCode;
    private BigDecimal localizedAmountValue;
}