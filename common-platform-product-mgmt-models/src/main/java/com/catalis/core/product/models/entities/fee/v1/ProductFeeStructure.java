package com.catalis.core.product.models.entities.fee.v1;

import com.catalis.core.product.models.entities.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("product_fee_structure")
public class ProductFeeStructure extends BaseEntity {
    @Id
    @Column("product_fee_structure_id")
    private Long productFeeStructureId;

    @Column("product_id")
    private Long productId;

    @Column("fee_structure_id")
    private Long feeStructureId;

    @Column("priority")
    private Integer priority;
}