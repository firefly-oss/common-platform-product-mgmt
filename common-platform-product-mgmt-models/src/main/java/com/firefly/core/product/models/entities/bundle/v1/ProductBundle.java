package com.firefly.core.product.models.entities.bundle.v1;

import com.firefly.core.product.interfaces.enums.bundle.v1.BundleStatusEnum;
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
@Table("product_bundle")
public class ProductBundle extends BaseEntity {
    @Id
    @Column("product_bundle_id")
    private Long productBundleId;

    @Column("bundle_name")
    private String bundleName;

    @Column("bundle_description")
    private String bundleDescription;

    @Column("bundle_status")
    private BundleStatusEnum bundleStatus;
}