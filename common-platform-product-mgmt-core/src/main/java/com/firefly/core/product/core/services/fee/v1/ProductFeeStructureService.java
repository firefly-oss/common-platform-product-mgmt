package com.firefly.core.product.core.services.fee.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.fee.v1.ProductFeeStructureDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductFeeStructureService {

    /**
     * Retrieve a paginated list of fee structures associated with a given product.
     */
    Mono<PaginationResponse<ProductFeeStructureDTO>> getAllFeeStructuresByProduct(
            UUID productId,
            PaginationRequest paginationRequest
    );

    /**
     * Create a new fee structure and associate it with a specific product.
     */
    Mono<ProductFeeStructureDTO> createFeeStructure(UUID productId, ProductFeeStructureDTO request);

    /**
     * Retrieve a specific fee structure by its unique identifier, checking it belongs to the specified product.
     */
    Mono<ProductFeeStructureDTO> getFeeStructureById(UUID productId, UUID feeStructureId);

    /**
     * Update an existing fee structure for a specific product.
     */
    Mono<ProductFeeStructureDTO> updateFeeStructure(UUID productId, UUID feeStructureId, ProductFeeStructureDTO request);

    /**
     * Delete an existing fee structure from a specific product.
     */
    Mono<Void> deleteFeeStructure(UUID productId, UUID feeStructureId);
}