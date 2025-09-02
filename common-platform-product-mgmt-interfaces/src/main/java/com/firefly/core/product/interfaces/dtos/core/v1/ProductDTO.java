package com.firefly.core.product.interfaces.dtos.core.v1;

import com.firefly.core.product.interfaces.dtos.BaseDTO;
import com.firefly.core.product.interfaces.enums.core.v1.ProductStatusEnum;
import com.firefly.core.product.interfaces.enums.core.v1.ProductTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends BaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID productId;

    private UUID productSubtypeId;
    private ProductTypeEnum productType;
    private String productName;
    private String productCode;
    private String productDescription;
    private ProductStatusEnum productStatus;
    private LocalDate launchDate;
    private LocalDate endDate;
}