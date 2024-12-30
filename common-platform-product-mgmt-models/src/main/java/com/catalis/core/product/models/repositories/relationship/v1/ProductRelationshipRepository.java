package com.catalis.core.product.models.repositories.relationship.v1;

import com.catalis.core.product.interfaces.enums.relationship.v1.RelationshipTypeEnum;
import com.catalis.core.product.models.entities.relationship.v1.ProductRelationship;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRelationshipRepository extends BaseRepository<ProductRelationship, Long> {
    Flux<ProductRelationship> findByProductId(Long productId);
    Flux<ProductRelationship> findByRelatedProductId(Long relatedProductId);
    Flux<ProductRelationship> findByRelationshipType(RelationshipTypeEnum type);

    // Bidirectional relationship search
    Flux<ProductRelationship> findByProductIdOrRelatedProductId(Long productId, Long relatedProductId);

    // Pageable queries
    Flux<ProductRelationship> findByProductId(Long productId, Pageable pageable);
    Mono<Long> countByProductId(Long productId);

    Flux<ProductRelationship> findByRelationshipType(RelationshipTypeEnum type, Pageable pageable);
    Mono<Long> countByRelationshipType(RelationshipTypeEnum type);
}