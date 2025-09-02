package com.firefly.core.product.models.entities.fee.v1;

import com.firefly.core.product.models.entities.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("product_fee_structure")
public class ProductFeeStructure extends BaseEntity {
    @Id
    @Column("product_fee_structure_id")
    private UUID productFeeStructureId;

    @Column("product_id")
    private UUID productId;

    @Column("fee_structure_id")
    private UUID feeStructureId;

    @Column("priority")
    private Integer priority;
}