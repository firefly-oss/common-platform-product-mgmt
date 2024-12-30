package com.catalis.core.product.interfaces.dtos.core.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import com.catalis.core.product.interfaces.enums.core.v1.ProductStatusEnum;
import com.catalis.core.product.interfaces.enums.core.v1.ProductTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends BaseDTO {
    private Long productId;
    private Long productSubtypeId;
    private ProductTypeEnum productType;
    private String productName;
    private String productCode;
    private String productDescription;
    private ProductStatusEnum productStatus;
    private LocalDate launchDate;
    private LocalDate endDate;
}