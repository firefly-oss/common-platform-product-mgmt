package com.catalis.core.product.interfaces.dtos.category.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategorySubtypeDTO extends BaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long productSubtypeId;

    private Long productCategoryId;
    private String subtypeName;
    private String subtypeDescription;
}
