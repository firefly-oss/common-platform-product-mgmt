package com.firefly.core.product.interfaces.dtos.relationship.v1;

import com.firefly.core.product.interfaces.dtos.BaseDTO;
import com.firefly.core.product.interfaces.enums.relationship.v1.RelationshipTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRelationshipDTO extends BaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long productRelationshipId;

    private Long productId;
    private Long relatedProductId;
    private RelationshipTypeEnum relationshipType;
    private String description;
}