package com.firefly.core.product.models.entities.localization.v1;

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
@NoArgsConstructor
@AllArgsConstructor
@Table("product_localization")
public class ProductLocalization extends BaseEntity {

    @Id
    @Column("product_localization_id")
    private UUID productLocalizationId;

    @Column("product_id")
    private UUID productId;

    @Column("language_code")
    private String languageCode;

    @Column("localized_name")
    private String localizedName;

    @Column("localized_description")
    private String localizedDescription;

}