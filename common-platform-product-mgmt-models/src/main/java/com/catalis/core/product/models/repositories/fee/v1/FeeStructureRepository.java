package com.catalis.core.product.models.repositories.fee.v1;

import com.catalis.core.product.interfaces.enums.fee.v1.FeeStructureTypeEnum;
import com.catalis.core.product.models.entities.fee.v1.FeeStructure;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FeeStructureRepository extends BaseRepository<FeeStructure, Long> {
    Flux<FeeStructure> findByFeeStructureType(FeeStructureTypeEnum type);
    Flux<FeeStructure> findByFeeStructureNameContainingIgnoreCase(String namePattern);

    Flux<FeeStructure> findByFeeStructureType(FeeStructureTypeEnum type, Pageable pageable);
    Mono<Long> countByFeeStructureType(FeeStructureTypeEnum type);
}