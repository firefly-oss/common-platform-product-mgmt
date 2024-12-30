package com.catalis.core.product.interfaces.dtos.fee.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FeeApplicationRuleDTO extends BaseDTO {
    private Long feeApplicationRuleId;
    private Long feeComponentId;
    private String ruleDescription;
    private String ruleConditions;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
}
