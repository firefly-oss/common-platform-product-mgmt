package com.firefly.core.product.models.entities.relationship.v1;

import com.firefly.core.product.interfaces.enums.relationship.v1.RelationshipTypeEnum;
import com.firefly.core.product.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("product_relationship")
public class ProductRelationship extends BaseEntity {
    @Id
    @Column("product_relationship_id")
    private UUID productRelationshipId;

    @Column("product_id")
    private UUID productId;

    @Column("related_product_id")
    private UUID relatedProductId;

    @Column("relationship_type")
    private RelationshipTypeEnum relationshipType;

    @Column("description")
    private String description;
}