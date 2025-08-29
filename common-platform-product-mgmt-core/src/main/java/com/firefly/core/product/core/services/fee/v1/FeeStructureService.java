package com.firefly.core.product.core.services.fee.v1;

import com.firefly.core.product.interfaces.dtos.fee.v1.FeeStructureDTO;
import reactor.core.publisher.Mono;

public interface FeeStructureService {

    /**
     * Retrieve a specific fee structure by its unique identifier.
     */
    Mono<FeeStructureDTO> getFeeStructure(Long feeStructureId);

    /**
     * Create a new fee structure in the system.
     */
    Mono<FeeStructureDTO> createFeeStructure(FeeStructureDTO feeStructureDTO);

    /**
     * Update an existing fee structure by its unique identifier.
     */
    Mono<FeeStructureDTO> updateFeeStructure(Long feeStructureId, FeeStructureDTO feeStructureDTO);

    /**
     * Delete a fee structure by its unique identifier.
     */
    Mono<Void> deleteFeeStructure(Long feeStructureId);
}