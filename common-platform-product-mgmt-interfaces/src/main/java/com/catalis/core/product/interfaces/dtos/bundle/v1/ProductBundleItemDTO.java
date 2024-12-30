package com.catalis.core.product.interfaces.dtos.bundle.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBundleItemDTO extends BaseDTO {
    private Long productBundleItemId;
    private Long productBundleId;
    private Long productId;
    private String specialConditions;
}
