package com.firefly.core.product.models.entities.feature.v1;

import com.firefly.core.product.interfaces.enums.feature.v1.FeatureTypeEnum;
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
@Table("product_feature")
public class ProductFeature extends BaseEntity {
    @Id
    @Column("product_feature_id")
    private Long productFeatureId;

    @Column("product_id")
    private Long productId;

    @Column("feature_name")
    private String featureName;

    @Column("feature_description")
    private String featureDescription;

    @Column("feature_type")
    private FeatureTypeEnum featureType;

    @Column("is_mandatory")
    private Boolean isMandatory;
}
