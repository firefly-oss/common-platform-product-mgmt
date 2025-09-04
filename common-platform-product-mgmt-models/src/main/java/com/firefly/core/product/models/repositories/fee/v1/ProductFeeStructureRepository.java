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


package com.firefly.core.product.models.repositories.fee.v1;

import com.firefly.core.product.models.entities.fee.v1.ProductFeeStructure;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProductFeeStructureRepository extends BaseRepository<ProductFeeStructure, UUID> {
    Flux<ProductFeeStructure> findByProductId(UUID productId);
    Flux<ProductFeeStructure> findByFeeStructureId(UUID feeStructureId);
    Flux<ProductFeeStructure> findByProductIdOrderByPriorityAsc(UUID productId);

    Flux<ProductFeeStructure> findByProductId(UUID productId, Pageable pageable);
    Mono<Long> countByProductId(UUID productId);
}