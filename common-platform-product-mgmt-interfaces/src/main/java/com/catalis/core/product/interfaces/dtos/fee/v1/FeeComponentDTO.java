package com.catalis.core.product.interfaces.dtos.fee.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import com.catalis.core.product.interfaces.enums.fee.v1.FeeTypeEnum;
import com.catalis.core.product.interfaces.enums.fee.v1.FeeUnitEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FeeComponentDTO extends BaseDTO {
    private Long feeComponentId;
    private Long feeStructureId;
    private FeeTypeEnum feeType;
    private String feeDescription;
    private BigDecimal feeAmount;
    private FeeUnitEnum feeUnit;
    private String applicableConditions;
}
