package com.firefly.core.product.models.repositories.fee.v1;

import com.firefly.core.product.models.entities.fee.v1.FeeApplicationRule;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface FeeApplicationRuleRepository extends BaseRepository<FeeApplicationRule, Long> {
    Flux<FeeApplicationRule> findByFeeComponentId(Long componentId);
    Flux<FeeApplicationRule> findByEffectiveDateLessThanEqualAndExpiryDateGreaterThanEqual(
            LocalDate effectiveDate, LocalDate expiryDate);

    Flux<FeeApplicationRule> findByFeeComponentId(Long componentId, Pageable pageable);
    Mono<Long> countByFeeComponentId(Long componentId);
}