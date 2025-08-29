package com.firefly.core.product.interfaces.dtos.pricing.v1;

import com.firefly.core.product.interfaces.dtos.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long productPricingLocalizationId;

    private Long productPricingId;
    private String currencyCode;
    private BigDecimal localizedAmountValue;
}