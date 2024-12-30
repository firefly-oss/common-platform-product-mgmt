package com.catalis.core.product.models.repositories.documentation.v1;

import com.catalis.core.product.interfaces.enums.documentation.v1.DocTypeEnum;
import com.catalis.core.product.models.entities.documentation.v1.ProductDocumentation;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface ProductDocumentationRepository extends BaseRepository<ProductDocumentation, Long> {
    Flux<ProductDocumentation> findByProductId(Long productId);
    Flux<ProductDocumentation> findByDocType(DocTypeEnum docType);
    Flux<ProductDocumentation> findByDateAddedBetween(LocalDateTime start, LocalDateTime end);

    Flux<ProductDocumentation> findByProductId(Long productId, Pageable pageable);
    Mono<Long> countByProductId(Long productId);

    Flux<ProductDocumentation> findByDocType(DocTypeEnum docType, Pageable pageable);
    Mono<Long> countByDocType(DocTypeEnum docType);
}
