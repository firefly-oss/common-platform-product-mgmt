package com.firefly.core.product.core.services.fee.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.fee.v1.FeeApplicationRuleDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface FeeApplicationRuleService {

    /**
     * Retrieve a paginated list of fee application rules for a specific fee structure component.
     */
    Mono<PaginationResponse<FeeApplicationRuleDTO>> getRulesByComponentId(
            UUID feeStructureId,
            UUID componentId,
            PaginationRequest paginationRequest
    );

    /**
     * Create a new fee application rule under the specified fee structure component.
     */
    Mono<FeeApplicationRuleDTO> createRule(UUID feeStructureId, UUID componentId, FeeApplicationRuleDTO ruleDTO);

    /**
     * Retrieve a specific fee application rule by its unique identifier.
     */
    Mono<FeeApplicationRuleDTO> getRule(UUID feeStructureId, UUID componentId, UUID ruleId);

    /**
     * Update an existing fee application rule by its unique identifier.
     */
    Mono<FeeApplicationRuleDTO> updateRule(UUID feeStructureId, UUID componentId, UUID ruleId, FeeApplicationRuleDTO ruleDTO);

    /**
     * Delete an existing fee application rule by its unique identifier.
     */
    Mono<Void> deleteRule(UUID feeStructureId, UUID componentId, UUID ruleId);
}