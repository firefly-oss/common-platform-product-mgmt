package com.firefly.core.product.core.services.fee.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.fee.v1.FeeComponentDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface FeeComponentService {

    /**
     * Retrieve a paginated list of fee components associated with a specific fee structure.
     */
    Mono<PaginationResponse<FeeComponentDTO>> getByFeeStructureId(UUID feeStructureId, PaginationRequest paginationRequest);

    /**
     * Create a new fee component under the specified fee structure.
     */
    Mono<FeeComponentDTO> createFeeComponent(UUID feeStructureId, FeeComponentDTO feeComponentDTO);

    /**
     * Retrieve a specific fee component by its unique identifier under the given fee structure.
     */
    Mono<FeeComponentDTO> getFeeComponent(UUID feeStructureId, UUID componentId);

    /**
     * Update an existing fee component by its unique identifier under the given fee structure.
     */
    Mono<FeeComponentDTO> updateFeeComponent(UUID feeStructureId, UUID componentId, FeeComponentDTO feeComponentDTO);

    /**
     * Delete an existing fee component by its unique identifier under the specified fee structure.
     */
    Mono<Void> deleteFeeComponent(UUID feeStructureId, UUID componentId);
}
