package com.firefly.core.product.models.repositories.fee.v1;

import com.firefly.core.product.interfaces.enums.fee.v1.FeeTypeEnum;
import com.firefly.core.product.interfaces.enums.fee.v1.FeeUnitEnum;
import com.firefly.core.product.models.entities.fee.v1.FeeComponent;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FeeComponentRepository extends BaseRepository<FeeComponent, Long> {
    Flux<FeeComponent> findByFeeStructureId(Long structureId);

    Flux<FeeComponent> findByFeeType(FeeTypeEnum type, Pageable pageable);
    Mono<Long> countByFeeType(FeeTypeEnum type);

    Flux<FeeComponent> findByFeeStructureId(Long structureId, Pageable pageable);
    Mono<Long> countByFeeStructureId(Long structureId);
}
