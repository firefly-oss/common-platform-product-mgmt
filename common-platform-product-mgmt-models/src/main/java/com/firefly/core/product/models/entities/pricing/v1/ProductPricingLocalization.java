package com.firefly.core.product.models.entities.pricing.v1;

import com.firefly.core.product.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("product_pricing_localization")
public class ProductPricingLocalization extends BaseEntity {
    @Id
    @Column("product_pricing_localization_id")
    private Long productPricingLocalizationId;

    @Column("product_pricing_id")
    private Long productPricingId;

    @Column("currency_code")
    private String currencyCode;

    @Column("localized_amount_value")
    private BigDecimal localizedAmountValue;
}