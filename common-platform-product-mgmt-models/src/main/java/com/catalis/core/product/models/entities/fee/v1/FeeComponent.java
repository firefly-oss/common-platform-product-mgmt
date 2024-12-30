package com.catalis.core.product.models.entities.fee.v1;

import com.catalis.core.product.interfaces.enums.fee.v1.FeeTypeEnum;
import com.catalis.core.product.interfaces.enums.fee.v1.FeeUnitEnum;
import com.catalis.core.product.models.entities.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("fee_component")
public class FeeComponent extends BaseEntity {
    @Id
    @Column("fee_component_id")
    private Long feeComponentId;

    @Column("fee_structure_id")
    private Long feeStructureId;

    @Column("fee_type")
    private FeeTypeEnum feeType;

    @Column("fee_description")
    private String feeDescription;

    @Column("fee_amount")
    private BigDecimal feeAmount;

    @Column("fee_unit")
    private FeeUnitEnum feeUnit;

    @Column("applicable_conditions")
    private String applicableConditions;
}
