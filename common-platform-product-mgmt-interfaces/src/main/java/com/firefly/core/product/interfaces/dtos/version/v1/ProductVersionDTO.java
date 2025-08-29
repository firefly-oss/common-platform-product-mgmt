package com.firefly.core.product.interfaces.dtos.version.v1;

import com.firefly.core.product.interfaces.dtos.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductVersionDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long productVersionId;

    private Long productId;
    private Long versionNumber;
    private String versionDescription;
    private LocalDateTime effectiveDate;

}