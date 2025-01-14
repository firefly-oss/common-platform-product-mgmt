package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.interfaces.dtos.fee.v1.ProductFeeStructureDTO;
import reactor.core.publisher.Mono;

public interface ProductFeeStructureService {

    /**
     * Retrieve a paginated list of fee structures associated with a given product.
     */
    Mono<PaginationResponse<ProductFeeStructureDTO>> getAllFeeStructuresByProduct(
            Long productId,
            PaginationRequest paginationRequest
    );

    /**
     * Create a new fee structure and associate it with a specific product.
     */
    Mono<ProductFeeStructureDTO> createFeeStructure(Long productId, ProductFeeStructureDTO request);

    /**
     * Retrieve a specific fee structure by its unique identifier, checking it belongs to the specified product.
     */
    Mono<ProductFeeStructureDTO> getFeeStructureById(Long productId, Long feeStructureId);

    /**
     * Update an existing fee structure for a specific product.
     */
    Mono<ProductFeeStructureDTO> updateFeeStructure(Long productId, Long feeStructureId, ProductFeeStructureDTO request);

    /**
     * Delete an existing fee structure from a specific product.
     */
    Mono<Void> deleteFeeStructure(Long productId, Long feeStructureId);
}