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


package com.firefly.core.product.interfaces.dtos.lifecycle.v1;

import com.firefly.core.product.interfaces.dtos.BaseDTO;
import com.firefly.core.product.interfaces.enums.lifecycle.v1.LimitTypeEnum;
import com.firefly.core.product.interfaces.enums.lifecycle.v1.TimePeriodEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductLimitDTO extends BaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private UUID productLimitId;
    private UUID productId;
    private LimitTypeEnum limitType;
    private BigDecimal limitValue;
    private String limitUnit;
    private TimePeriodEnum timePeriod;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
}