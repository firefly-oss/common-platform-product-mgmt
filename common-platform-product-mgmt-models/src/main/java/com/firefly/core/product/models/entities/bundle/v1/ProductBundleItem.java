package com.firefly.core.product.models.entities.bundle.v1;

import com.firefly.core.product.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("product_bundle_item")
public class ProductBundleItem extends BaseEntity {
    @Id
    @Column("product_bundle_item_id")
    private Long productBundleItemId;

    @Column("product_bundle_id")
    private Long productBundleId;

    @Column("product_id")
    private Long productId;

    @Column("special_conditions")
    private String specialConditions;
}
