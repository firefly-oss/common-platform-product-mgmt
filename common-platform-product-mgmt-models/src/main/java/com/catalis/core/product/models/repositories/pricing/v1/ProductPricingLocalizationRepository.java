package com.catalis.core.product.models.repositories.pricing.v1;

import com.catalis.core.product.models.entities.pricing.v1.ProductPricingLocalization;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductPricingLocalizationRepository extends BaseRepository<ProductPricingLocalization, Long> {
    Flux<ProductPricingLocalization> findByProductPricingId(Long pricingId);

    Flux<ProductPricingLocalization> findByCurrencyCode(String currencyCode);

    // Find all localizations for a specific pricing and currency
    Flux<ProductPricingLocalization> findByProductPricingIdAndCurrencyCode(
            Long pricingId, String currencyCode);

    Mono<Long> countByProductPricingId(Long pricingId);
}