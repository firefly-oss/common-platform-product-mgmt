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
public class ProductCategory extends BaseEntity {
    @Id
    @Column("product_category_id")
    private Long productCategoryId;

    @Column("category_name")
    private String categoryName;

    @Column("category_description")
    private String categoryDescription;

    @Column("parent_category_id")
    private Long parentCategoryId;
}