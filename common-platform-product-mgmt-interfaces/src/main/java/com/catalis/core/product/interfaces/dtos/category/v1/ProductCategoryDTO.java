package com.catalis.core.product.interfaces.dtos.category.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDTO extends BaseDTO {
    private Long productCategoryId;
    private String categoryName;
    private String categoryDescription;
    private Long parentCategoryId;
}
