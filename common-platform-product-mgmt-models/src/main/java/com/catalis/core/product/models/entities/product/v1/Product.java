package com.catalis.core.product.models.entities.product.v1;

import com.catalis.core.product.interfaces.enums.product.v1.ProductStatusEnum;
import com.catalis.core.product.interfaces.enums.product.v1.ProductTypeEnum;
import com.catalis.core.product.models.entities.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    @Column("product_id")
    private Long productId;

    @Column("product_subtype_id")
    private Long productSubtypeId;

    @Column("product_type")
    private ProductTypeEnum productType;

    @Column("product_name")
    private String productName;

    @Column("product_code")
    private String productCode;

    @Column("product_description")
    private String productDescription;

    @Column("product_status")
    private ProductStatusEnum productStatus;

    @Column("launch_date")
    private LocalDate launchDate;

    @Column("end_date")
    private LocalDate endDate;
}
