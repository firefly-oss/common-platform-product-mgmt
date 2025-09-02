package com.firefly.core.product.core.services.fee.v1;

import com.firefly.core.product.interfaces.dtos.fee.v1.FeeStructureDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface FeeStructureService {

    /**
     * Retrieve a specific fee structure by its unique identifier.
     */
    Mono<FeeStructureDTO> getFeeStructure(UUID feeStructureId);

    /**
     * Create a new fee structure in the system.
     */
    Mono<FeeStructureDTO> createFeeStructure(FeeStructureDTO feeStructureDTO);

    /**
     * Update an existing fee structure by its unique identifier.
     */
    Mono<FeeStructureDTO> updateFeeStructure(UUID feeStructureId, FeeStructureDTO feeStructureDTO);

    /**
     * Delete a fee structure by its unique identifier.
     */
    Mono<Void> deleteFeeStructure(UUID feeStructureId);
}