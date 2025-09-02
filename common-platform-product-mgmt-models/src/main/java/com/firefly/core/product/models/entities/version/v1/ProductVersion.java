package com.firefly.core.product.models.entities.version.v1;

import com.firefly.core.product.models.entities.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("product_version")
public class ProductVersion extends BaseEntity {

    @Id
    @Column("product_version_id")
    private UUID productVersionId;

    @Column("product_id")
    private UUID productId;

    @Column("version_number")
    private Long versionNumber;

    @Column("version_description")
    private String versionDescription;

    @Column("effective_date")
    private LocalDateTime effectiveDate;

}