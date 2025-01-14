package com.catalis.core.product.interfaces.dtos.localization.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductLocalizationDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long productLocalizationId;

    private Long productId;
    private String languageCode;
    private String localizedName;
    private String localizedDescription;

}