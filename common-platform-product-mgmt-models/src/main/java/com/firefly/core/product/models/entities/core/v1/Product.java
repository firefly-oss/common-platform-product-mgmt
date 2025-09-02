package com.firefly.core.product.models.entities.core.v1;

import com.firefly.core.product.interfaces.enums.core.v1.ProductStatusEnum;
import com.firefly.core.product.interfaces.enums.core.v1.ProductTypeEnum;
import com.firefly.core.product.models.entities.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    @Column("product_id")
    private UUID productId;

    @Column("product_subtype_id")
    private UUID productSubtypeId;

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
