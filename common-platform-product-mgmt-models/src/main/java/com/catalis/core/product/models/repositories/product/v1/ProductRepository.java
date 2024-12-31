package com.catalis.core.product.models.repositories.product.v1;

import com.catalis.core.product.interfaces.enums.core.v1.ProductStatusEnum;
import com.catalis.core.product.interfaces.enums.core.v1.ProductTypeEnum;
import com.catalis.core.product.models.entities.core.v1.Product;
import com.catalis.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    Flux<Product> findByProductSubtypeId(Long subtypeId);
    Flux<Product> findByProductStatus(ProductStatusEnum status);
    Flux<Product> findByProductType(ProductTypeEnum type);
    Flux<Product> findByProductNameContainingIgnoreCase(String namePattern, Pageable pageable);
    Flux<Product> findByLaunchDateBetween(LocalDate startDate, LocalDate endDate);

    // Custom pageable queries
    Flux<Product> findByProductStatus(ProductStatusEnum status, Pageable pageable);
    Mono<Long> countByProductStatus(ProductStatusEnum status);

    Flux<Product> findByProductType(ProductTypeEnum type, Pageable pageable);
    Mono<Long> countByProductType(ProductTypeEnum type);
}
