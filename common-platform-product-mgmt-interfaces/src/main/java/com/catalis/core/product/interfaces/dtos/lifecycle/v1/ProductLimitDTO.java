package com.catalis.core.product.interfaces.dtos.lifecycle.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import com.catalis.core.product.interfaces.enums.lifecycle.v1.LimitTypeEnum;
import com.catalis.core.product.interfaces.enums.lifecycle.v1.TimePeriodEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ProductLimitDTO extends BaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private Long productLimitId;
    private Long productId;
    private LimitTypeEnum limitType;
    private BigDecimal limitValue;
    private String limitUnit;
    private TimePeriodEnum timePeriod;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
}