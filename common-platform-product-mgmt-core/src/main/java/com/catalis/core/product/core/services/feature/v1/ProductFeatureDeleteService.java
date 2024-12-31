package com.catalis.core.product.core.services.feature.v1;

import com.catalis.core.product.models.repositories.feature.v1.ProductFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductFeatureDeleteService {

    @Autowired
    private ProductFeatureRepository repository;

    /**
     * Deletes a product feature identified by the given ID.
     * Searches for the product feature in the repository and attempts to remove it.
     * If the product feature is not found, an {@code IllegalArgumentException} will be returned.
     * Handles errors during the delete operation by emitting a {@code RuntimeException}.
     *
     * @param productFeatureId the unique identifier of the product feature to be deleted
     * @return a {@code Mono<Void>} indicating the completion of the deletion process
     */
    public Mono<Void> deleteProductFeature(Long productFeatureId) {
        return repository.findById(productFeatureId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Feature not found for ID: " + productFeatureId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete Product Feature", e)))
                .then();
    }

}
