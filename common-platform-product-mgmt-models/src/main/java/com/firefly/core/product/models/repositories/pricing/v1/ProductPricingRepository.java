package com.firefly.core.product.models.repositories.pricing.v1;

import com.firefly.core.product.interfaces.enums.pricing.v1.PricingTypeEnum;
import com.firefly.core.product.models.entities.pricing.v1.ProductPricing;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface ProductPricingRepository extends BaseRepository<ProductPricing, Long> {
    Flux<ProductPricing> findByProductId(Long productId);
    Flux<ProductPricing> findByPricingType(PricingTypeEnum type);
    Flux<ProductPricing> findByEffectiveDateLessThanEqualAndExpiryDateGreaterThanEqual(
            LocalDate effectiveDate, LocalDate expiryDate);

    Flux<ProductPricing> findByProductId(Long productId, Pageable pageable);
    Mono<Long> countByProductId(Long productId);
}
