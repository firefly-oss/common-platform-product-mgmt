package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.core.product.models.repositories.pricing.v1.ProductPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductPricingDeleteService {

    @Autowired
    private ProductPricingRepository repository;

    /**
     * Deletes a product pricing entry identified by the given product pricing ID.
     * If the product pricing entry is not found, an error is emitted.
     * In case of a failure during deletion, an error is propagated.
     *
     * @param productPricingId the unique identifier of the product pricing to delete
     * @return a Mono signaling completion when the deletion is successful or propagating an error if the operation fails
     */
    public Mono<Void> deleteProductPricing(Long productPricingId) {
        return repository.findById(productPricingId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Pricing not found for ID: " + productPricingId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete Product Pricing", e)))
                .then();
    }

}