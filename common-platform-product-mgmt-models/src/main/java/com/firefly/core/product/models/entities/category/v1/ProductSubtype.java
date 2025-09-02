package com.firefly.core.product.models.entities.category.v1;

import com.firefly.core.product.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;


@Table("product_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSubtype extends BaseEntity {
    @Id
    @Column("product_subtype_id")
    private UUID productSubtypeId;

    @Column("product_category_id")
    private UUID productCategoryId;

    @Column("subtype_name")
    private String subtypeName;

    @Column("subtype_description")
    private String subtypeDescription;
}
