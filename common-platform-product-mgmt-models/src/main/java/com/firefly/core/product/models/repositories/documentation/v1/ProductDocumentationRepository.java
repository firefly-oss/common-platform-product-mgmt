package com.firefly.core.product.models.repositories.documentation.v1;

import com.firefly.core.product.interfaces.enums.documentation.v1.DocTypeEnum;
import com.firefly.core.product.models.entities.documentation.v1.ProductDocumentation;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ProductDocumentationRepository extends BaseRepository<ProductDocumentation, UUID> {
    Flux<ProductDocumentation> findByProductId(UUID productId);
    Flux<ProductDocumentation> findByDocType(DocTypeEnum docType);
    Flux<ProductDocumentation> findByDateAddedBetween(LocalDateTime start, LocalDateTime end);

    Flux<ProductDocumentation> findByProductId(UUID productId, Pageable pageable);
    Mono<Long> countByProductId(UUID productId);

    Flux<ProductDocumentation> findByDocType(DocTypeEnum docType, Pageable pageable);
    Mono<Long> countByDocType(DocTypeEnum docType);
}
