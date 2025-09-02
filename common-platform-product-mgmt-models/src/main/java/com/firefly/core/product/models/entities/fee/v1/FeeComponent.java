package com.firefly.core.product.models.entities.fee.v1;

import com.firefly.core.product.interfaces.enums.fee.v1.FeeTypeEnum;
import com.firefly.core.product.interfaces.enums.fee.v1.FeeUnitEnum;
import com.firefly.core.product.models.entities.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("fee_component")
public class FeeComponent extends BaseEntity {
    @Id
    @Column("fee_component_id")
    private UUID feeComponentId;

    @Column("fee_structure_id")
    private UUID feeStructureId;

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
