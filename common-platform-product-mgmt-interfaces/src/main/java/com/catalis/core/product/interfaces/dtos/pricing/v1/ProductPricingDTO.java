package com.catalis.core.product.interfaces.dtos.pricing.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import com.catalis.core.product.interfaces.enums.pricing.v1.PricingTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPricingDTO extends BaseDTO {
    private Long productPricingId;
    private Long productId;
    private PricingTypeEnum pricingType;
    private BigDecimal amountValue;
    private String amountUnit;
    private String pricingCondition;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
}
