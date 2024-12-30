package com.catalis.core.product.interfaces.dtos.relationship.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import com.catalis.core.product.interfaces.enums.relationship.v1.RelationshipTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRelationshipDTO extends BaseDTO {
    private Long productRelationshipId;
    private Long productId;
    private Long relatedProductId;
    private RelationshipTypeEnum relationshipType;
    private String description;
}