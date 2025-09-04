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