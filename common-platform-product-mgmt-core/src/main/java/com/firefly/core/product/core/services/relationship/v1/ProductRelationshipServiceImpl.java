package com.firefly.core.product.core.services.relationship.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.product.core.mappers.relationship.v1.ProductRelationshipMapper;
import com.firefly.core.product.interfaces.dtos.relationship.v1.ProductRelationshipDTO;
import com.firefly.core.product.models.entities.relationship.v1.ProductRelationship;
import com.firefly.core.product.models.repositories.relationship.v1.ProductRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class ProductRelationshipServiceImpl implements ProductRelationshipService {

    @Autowired
    private ProductRelationshipRepository repository;

    @Autowired
    private ProductRelationshipMapper mapper;

    @Override
    public Mono<PaginationResponse<ProductRelationshipDTO>> getAllRelationships(UUID productId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        ).onErrorResume(e -> Mono.error(new RuntimeException("Error occurred while retrieving relationships", e)));
    }

    @Override
    public Mono<ProductRelationshipDTO> createRelationship(UUID productId, ProductRelationshipDTO dto) {
        try {
            ProductRelationship relationship = mapper.toEntity(dto);
            relationship.setProductId(productId);
            return repository.save(relationship)
                    .map(mapper::toDto)
                    .onErrorResume(e -> Mono.error(new RuntimeException("Error occurred while creating the relationship", e)));
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Unexpected error occurred", e));
        }
    }

    @Override
    public Mono<ProductRelationshipDTO> getRelationship(UUID productId, UUID relationshipId) {
        return repository.findById(relationshipId)
                .filter(r -> r.getProductId().equals(productId))
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Relationship not found for product ID and relationship ID")))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error occurred while retrieving the relationship", e)));
    }

    @Override
    public Mono<ProductRelationshipDTO> updateRelationship(UUID productId, UUID relationshipId, ProductRelationshipDTO dto) {
        return repository.findById(relationshipId)
                .filter(r -> r.getProductId().equals(productId))
                .switchIfEmpty(Mono.error(new RuntimeException("Relationship not found for product ID and relationship ID")))
                .flatMap(existing -> {
                    try {
                        ProductRelationship relationship = mapper.toEntity(dto);
                        relationship.setProductRelationshipId(relationshipId);
                        relationship.setProductId(productId);
                        return repository.save(relationship)
                                .onErrorResume(e -> Mono.error(new RuntimeException("Error occurred while updating the relationship", e)));
                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("Unexpected error occurred during update", e));
                    }
                })
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error occurred while mapping the updated relationship", e)));
    }

    @Override
    public Mono<Void> deleteRelationship(UUID productId, UUID relationshipId) {
        return repository.findById(relationshipId)
                .filter(r -> r.getProductId().equals(productId))
                .switchIfEmpty(Mono.error(new RuntimeException("Relationship not found for product ID and relationship ID")))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Error occurred while deleting the relationship", e)));
    }
}