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
