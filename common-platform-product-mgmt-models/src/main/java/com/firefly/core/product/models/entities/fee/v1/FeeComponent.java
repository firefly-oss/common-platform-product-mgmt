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


package com.firefly.core.product.models.entities.fee.v1;

import com.firefly.core.product.interfaces.enums.fee.v1.FeeTypeEnum;
import com.firefly.core.product.interfaces.enums.fee.v1.FeeUnitEnum;
import com.firefly.core.product.models.entities.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("fee_component")
public class FeeComponent extends BaseEntity {
    @Id
    @Column("fee_component_id")
    private UUID feeComponentId;

    @Column("fee_structure_id")
    private UUID feeStructureId;

    @Column("fee_type")
    private FeeTypeEnum feeType;

    @Column("fee_description")
    private String feeDescription;

    @Column("fee_amount")
    private BigDecimal feeAmount;

    @Column("fee_unit")
    private FeeUnitEnum feeUnit;

    @Column("applicable_conditions")
    private String applicableConditions;
}
