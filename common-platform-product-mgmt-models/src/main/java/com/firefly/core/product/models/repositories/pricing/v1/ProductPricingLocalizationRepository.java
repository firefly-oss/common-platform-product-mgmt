package com.firefly.core.product.models.repositories.pricing.v1;

import com.firefly.core.product.models.entities.pricing.v1.ProductPricingLocalization;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface ProductPricingLocalizationRepository extends BaseRepository<ProductPricingLocalization, UUID> {
    Flux<ProductPricingLocalization> findByProductPricingId(UUID pricingId, Pageable pageable);

    Flux<ProductPricingLocalization> findByCurrencyCode(String currencyCode);

    // Find all localizations for a specific pricing and currency
    Flux<ProductPricingLocalization> findByProductPricingIdAndCurrencyCode(
            UUID pricingId, String currencyCode);

    Mono<Long> countByProductPricingId(UUID pricingId);
}