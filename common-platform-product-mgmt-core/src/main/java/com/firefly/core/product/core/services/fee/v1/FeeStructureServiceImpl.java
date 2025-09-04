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

import com.firefly.core.product.core.mappers.fee.v1.FeeStructureMapper;
import com.firefly.core.product.interfaces.dtos.fee.v1.FeeStructureDTO;
import com.firefly.core.product.models.entities.fee.v1.FeeStructure;
import com.firefly.core.product.models.repositories.fee.v1.FeeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class FeeStructureServiceImpl implements FeeStructureService {

    @Autowired
    private FeeStructureRepository repository;

    @Autowired
    private FeeStructureMapper mapper;

    @Override
    public Mono<FeeStructureDTO> getFeeStructure(UUID feeStructureId) {
        return repository.findById(feeStructureId)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Fee structure not found for ID: " + feeStructureId)))
                .onErrorMap(e -> new RuntimeException("Failed to retrieve fee structure with ID: " + feeStructureId, e));
    }

    @Override
    public Mono<FeeStructureDTO> createFeeStructure(FeeStructureDTO feeStructureDTO) {
        FeeStructure entity = mapper.toEntity(feeStructureDTO);
        return repository.save(entity)
                .map(mapper::toDto)
                .onErrorMap(e -> new RuntimeException("Failed to create fee structure", e));
    }

    @Override
    public Mono<FeeStructureDTO> updateFeeStructure(UUID feeStructureId, FeeStructureDTO feeStructureDTO) {
        return repository.findById(feeStructureId)
                .switchIfEmpty(Mono.error(new RuntimeException("Fee structure not found for ID: " + feeStructureId)))
                .flatMap(existingEntity -> {
                    FeeStructure updatedEntity = mapper.toEntity(feeStructureDTO);
                    updatedEntity.setFeeStructureId(feeStructureId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDto)
                .onErrorMap(e -> new RuntimeException("Failed to update fee structure with ID: " + feeStructureId, e));
    }

    @Override
    public Mono<Void> deleteFeeStructure(UUID feeStructureId) {
        return repository.findById(feeStructureId)
                .switchIfEmpty(Mono.error(new RuntimeException("Fee structure not found for ID: " + feeStructureId)))
                .flatMap(repository::delete)
                .onErrorMap(e -> new RuntimeException("Failed to delete fee structure with ID: " + feeStructureId, e));
    }
}
