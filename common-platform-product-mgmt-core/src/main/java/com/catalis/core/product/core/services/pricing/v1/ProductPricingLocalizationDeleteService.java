package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.core.product.models.repositories.pricing.v1.ProductPricingLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductPricingLocalizationDeleteService {

    @Autowired
    private ProductPricingLocalizationRepository repository;

    /**
     * Deletes a Product Pricing Localization entry identified by the given ID.
     * If the entry is not found, an error is emitted.
     *
     * @param productPricingLocalizationId the unique identifier of the Product Pricing Localization to delete
     * @return a Mono signaling completion when the deletion is successful or propagating an error if the entry is not found or the operation fails
     */
    public Mono<Void> deleteProductPricingLocalization(Long productPricingLocalizationId) {
        return repository.findById(productPricingLocalizationId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Pricing Localization not found for ID: " + productPricingLocalizationId)))
                .flatMap(repository::delete)
                .then();
    }

}
