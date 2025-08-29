package com.firefly.core.product.models.repositories.lifecycle.v1;

import com.firefly.core.product.interfaces.enums.lifecycle.v1.LimitTypeEnum;
import com.firefly.core.product.interfaces.enums.lifecycle.v1.TimePeriodEnum;
import com.firefly.core.product.models.entities.lifecycle.v1.ProductLimit;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface ProductLimitRepository extends BaseRepository<ProductLimit, Long> {
    Flux<ProductLimit> findByProductId(Long productId);
    Flux<ProductLimit> findByLimitType(LimitTypeEnum type);
    Flux<ProductLimit> findByTimePeriod(TimePeriodEnum period);
    Flux<ProductLimit> findByEffectiveDateLessThanEqualAndExpiryDateGreaterThanEqual(
            LocalDate date, LocalDate date2);

    Flux<ProductLimit> findByProductId(Long productId, Pageable pageable);
    Mono<Long> countByProductId(Long productId);
}