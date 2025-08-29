package com.firefly.core.product.models.entities.lifecycle.v1;

import com.firefly.core.product.interfaces.enums.lifecycle.v1.LimitTypeEnum;
import com.firefly.core.product.interfaces.enums.lifecycle.v1.TimePeriodEnum;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("product_limit")
public class ProductLimit extends BaseEntity {
    @Id
    @Column("product_limit_id")
    private Long productLimitId;

    @Column("product_id")
    private Long productId;

    @Column("limit_type")
    private LimitTypeEnum limitType;

    @Column("limit_value")
    private BigDecimal limitValue;

    @Column("limit_unit")
    private String limitUnit;

    @Column("time_period")
    private TimePeriodEnum timePeriod;

    @Column("effective_date")
    private LocalDate effectiveDate;

    @Column("expiry_date")
    private LocalDate expiryDate;
}