package com.catalis.core.product.models.entities.category.v1;

import com.catalis.core.product.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("product_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSubtype extends BaseEntity {
    @Id
    @Column("product_subtype_id")
    private Long productSubtypeId;

    @Column("product_category_id")
    private Long productCategoryId;

    @Column("subtype_name")
    private String subtypeName;

    @Column("subtype_description")
    private String subtypeDescription;
}
