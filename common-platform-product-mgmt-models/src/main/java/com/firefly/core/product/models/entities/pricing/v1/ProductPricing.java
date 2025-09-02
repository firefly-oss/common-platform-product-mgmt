package com.firefly.core.product.models.entities.pricing.v1;

import com.firefly.core.product.interfaces.enums.pricing.v1.PricingTypeEnum;
import com.firefly.core.product.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("product_pricing")
public class ProductPricing extends BaseEntity {
    @Id
    @Column("product_pricing_id")
    private UUID productPricingId;

    @Column("product_id")
    private UUID productId;

    @Column("pricing_type")
    private PricingTypeEnum pricingType;

    @Column("amount_value")
    private BigDecimal amountValue;

    @Column("amount_unit")
    private String amountUnit;

    @Column("pricing_condition")
    private String pricingCondition;

    @Column("effective_date")
    private LocalDate effectiveDate;

    @Column("expiry_date")
    private LocalDate expiryDate;
}
