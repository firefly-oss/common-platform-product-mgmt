package com.firefly.core.product.interfaces.dtos.pricing.v1;

import com.firefly.core.product.interfaces.dtos.BaseDTO;
import com.firefly.core.product.interfaces.enums.pricing.v1.PricingTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPricingDTO extends BaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID productPricingId;

    private UUID productId;
    private PricingTypeEnum pricingType;
    private BigDecimal amountValue;
    private String amountUnit;
    private String pricingCondition;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
}
