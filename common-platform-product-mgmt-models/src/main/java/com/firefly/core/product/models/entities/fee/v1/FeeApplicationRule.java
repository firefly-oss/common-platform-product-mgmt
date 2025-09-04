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

import com.firefly.core.product.models.entities.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("fee_application_rule")
public class FeeApplicationRule extends BaseEntity {
    @Id
    @Column("fee_application_rule_id")
    private UUID feeApplicationRuleId;

    @Column("fee_component_id")
    private UUID feeComponentId;

    @Column("rule_description")
    private String ruleDescription;

    @Column("rule_conditions")
    private String ruleConditions;

    @Column("effective_date")
    private LocalDate effectiveDate;

    @Column("expiry_date")
    private LocalDate expiryDate;
}
