package com.catalis.core.product.core.services.relationship.v1;

import com.catalis.core.product.models.repositories.relationship.v1.ProductRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductRelationshipDeleteService {

    @Autowired
    private ProductRelationshipRepository repository;

    /**
     * Deletes a product relationship based on the provided relationship ID.
     * If the relationship does not exist, an error is returned.
     *
     * @param productRelationshipId the ID of the product relationship to delete
     * @return a {@code Mono<Void>} that completes when the product relationship is deleted,
     *         or emits an error if the deletion fails or the relationship does not exist
     */
    public Mono<Void> deleteProductRelationship(Long productRelationshipId) {
        return repository.findById(productRelationshipId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Relationship not found for ID: " + productRelationshipId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete Product Relationship", e)))
                .then();
    }

}
