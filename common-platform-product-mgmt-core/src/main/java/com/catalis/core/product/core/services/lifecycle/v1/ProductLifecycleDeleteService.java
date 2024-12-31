package com.catalis.core.product.core.services.lifecycle.v1;

import com.catalis.core.product.models.repositories.lifecycle.v1.ProductLifecycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductLifecycleDeleteService {

    @Autowired
    private ProductLifecycleRepository repository;

    /**
     * Deletes a product lifecycle record identified by the given product lifecycle ID.
     * The method first attempts to find the record by its ID, and if found, deletes it from the repository.
     * If the record is not found, an error is emitted.
     * Ensures proper error handling throughout the process.
     *
     * @param productLifecycleId the ID of the product lifecycle to be deleted
     * @return a Mono that completes when the deletion process is finished or emits an error if the operation fails
     */
    public Mono<Void> deleteProductLifecycle(Long productLifecycleId) {
        return repository.findById(productLifecycleId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Lifecycle not found for ID: " + productLifecycleId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete Product Lifecycle", e)))
                .then();
    }

}
