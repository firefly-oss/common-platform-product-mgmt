package com.firefly.core.product.core.services.fee.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.fee.v1.FeeApplicationRuleDTO;
import reactor.core.publisher.Mono;

public interface FeeApplicationRuleService {

    /**
     * Retrieve a paginated list of fee application rules for a specific fee structure component.
     */
    Mono<PaginationResponse<FeeApplicationRuleDTO>> getRulesByComponentId(
            Long feeStructureId,
            Long componentId,
            PaginationRequest paginationRequest
    );

    /**
     * Create a new fee application rule under the specified fee structure component.
     */
    Mono<FeeApplicationRuleDTO> createRule(Long feeStructureId, Long componentId, FeeApplicationRuleDTO ruleDTO);

    /**
     * Retrieve a specific fee application rule by its unique identifier.
     */
    Mono<FeeApplicationRuleDTO> getRule(Long feeStructureId, Long componentId, Long ruleId);

    /**
     * Update an existing fee application rule by its unique identifier.
     */
    Mono<FeeApplicationRuleDTO> updateRule(Long feeStructureId, Long componentId, Long ruleId, FeeApplicationRuleDTO ruleDTO);

    /**
     * Delete an existing fee application rule by its unique identifier.
     */
    Mono<Void> deleteRule(Long feeStructureId, Long componentId, Long ruleId);
}