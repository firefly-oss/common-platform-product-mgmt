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


package com.firefly.core.product.models.repositories.relationship.v1;

import com.firefly.core.product.interfaces.enums.relationship.v1.RelationshipTypeEnum;
import com.firefly.core.product.models.entities.relationship.v1.ProductRelationship;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProductRelationshipRepository extends BaseRepository<ProductRelationship, UUID> {
    Flux<ProductRelationship> findByProductId(UUID productId);
    Flux<ProductRelationship> findByRelatedProductId(UUID relatedProductId);
    Flux<ProductRelationship> findByRelationshipType(RelationshipTypeEnum type);

    // Bidirectional relationship search
    Flux<ProductRelationship> findByProductIdOrRelatedProductId(UUID productId, UUID relatedProductId);

    // Pageable queries
    Flux<ProductRelationship> findByProductId(UUID productId, Pageable pageable);
    Mono<Long> countByProductId(UUID productId);

    Flux<ProductRelationship> findByRelationshipType(RelationshipTypeEnum type, Pageable pageable);
    Mono<Long> countByRelationshipType(RelationshipTypeEnum type);
}