package com.firefly.core.product.interfaces.dtos.documentation.v1;

import com.firefly.core.product.interfaces.dtos.BaseDTO;
import com.firefly.core.product.interfaces.enums.documentation.v1.ContractingDocTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO for product documentation requirements during the contracting/opening phase.
 * This defines which documents are required from customers to complete the contracting process.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDocumentationRequirementDTO extends BaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long productDocRequirementId;
    
    private Long productId;
    private ContractingDocTypeEnum docType;
    private Boolean isMandatory;
    private String description;
}