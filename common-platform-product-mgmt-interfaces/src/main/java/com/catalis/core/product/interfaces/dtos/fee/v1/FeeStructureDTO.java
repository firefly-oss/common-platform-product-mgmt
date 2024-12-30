package com.catalis.core.product.interfaces.dtos.fee.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import com.catalis.core.product.interfaces.enums.fee.v1.FeeStructureTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FeeStructureDTO extends BaseDTO {
    private Long feeStructureId;
    private String feeStructureName;
    private String feeStructureDescription;
    private FeeStructureTypeEnum feeStructureType;
}
