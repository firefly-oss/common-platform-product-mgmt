package com.firefly.core.product.interfaces.dtos.lifecycle.v1;

import com.firefly.core.product.interfaces.dtos.BaseDTO;
import com.firefly.core.product.interfaces.enums.lifecycle.v1.LimitTypeEnum;
import com.firefly.core.product.interfaces.enums.lifecycle.v1.TimePeriodEnum;
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
public class ProductLimitDTO extends BaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private UUID productLimitId;
    private UUID productId;
    private LimitTypeEnum limitType;
    private BigDecimal limitValue;
    private String limitUnit;
    private TimePeriodEnum timePeriod;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
}