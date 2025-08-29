package com.firefly.core.product.models.entities.fee.v1;

import com.firefly.core.product.interfaces.enums.fee.v1.FeeStructureTypeEnum;
import com.firefly.core.product.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table("fee_structure")
public class FeeStructure extends BaseEntity {
    @Id
    @Column("fee_structure_id")
    private Long feeStructureId;

    @Column("fee_structure_name")
    private String feeStructureName;

    @Column("fee_structure_description")
    private String feeStructureDescription;

    @Column("fee_structure_type")
    private FeeStructureTypeEnum feeStructureType;
}
