package com.firefly.core.product.interfaces.dtos.fee.v1;

import com.firefly.core.product.interfaces.dtos.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FeeApplicationRuleDTO extends BaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID feeApplicationRuleId;

    private UUID feeComponentId;
    private String ruleDescription;
    private String ruleConditions;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
}
