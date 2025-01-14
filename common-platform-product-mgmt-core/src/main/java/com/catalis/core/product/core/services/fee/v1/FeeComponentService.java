package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeComponentDTO;
import reactor.core.publisher.Mono;

public interface FeeComponentService {

    /**
     * Retrieve a paginated list of fee components associated with a specific fee structure.
     */
    Mono<PaginationResponse<FeeComponentDTO>> getByFeeStructureId(Long feeStructureId, PaginationRequest paginationRequest);

    /**
     * Create a new fee component under the specified fee structure.
     */
    Mono<FeeComponentDTO> createFeeComponent(Long feeStructureId, FeeComponentDTO feeComponentDTO);

    /**
     * Retrieve a specific fee component by its unique identifier under the given fee structure.
     */
    Mono<FeeComponentDTO> getFeeComponent(Long feeStructureId, Long componentId);

    /**
     * Update an existing fee component by its unique identifier under the given fee structure.
     */
    Mono<FeeComponentDTO> updateFeeComponent(Long feeStructureId, Long componentId, FeeComponentDTO feeComponentDTO);

    /**
     * Delete an existing fee component by its unique identifier under the specified fee structure.
     */
    Mono<Void> deleteFeeComponent(Long feeStructureId, Long componentId);
}
