/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("product_limit")
public class ProductLimit extends BaseEntity {
    @Id
    @Column("product_limit_id")
    private UUID productLimitId;

    @Column("product_id")
    private UUID productId;

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