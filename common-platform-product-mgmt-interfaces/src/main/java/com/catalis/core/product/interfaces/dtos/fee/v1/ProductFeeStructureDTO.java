package com.catalis.core.product.interfaces.dtos.fee.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFeeStructureDTO extends BaseDTO {
    private Long productFeeStructureId;
    private Long productId;
    private Long feeStructureId;
    private Integer priority;
}
