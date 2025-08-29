package com.firefly.core.product.interfaces.dtos.fee.v1;

import com.firefly.core.product.interfaces.dtos.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFeeStructureDTO extends BaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long productFeeStructureId;

    private Long productId;
    private Long feeStructureId;
    private Integer priority;
}
