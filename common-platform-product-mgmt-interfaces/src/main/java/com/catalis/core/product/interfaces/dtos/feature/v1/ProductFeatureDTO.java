package com.catalis.core.product.interfaces.dtos.feature.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import com.catalis.core.product.interfaces.enums.feature.v1.FeatureTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFeatureDTO extends BaseDTO {
    private Long productFeatureId;
    private Long productId;
    private String featureName;
    private String featureDescription;
    private FeatureTypeEnum featureType;
    private Boolean isMandatory;
}
