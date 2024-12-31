package com.catalis.core.product.interfaces.dtos.fee.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long feeApplicationRuleId;

    private Long feeComponentId;
    private String ruleDescription;
    private String ruleConditions;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
}
