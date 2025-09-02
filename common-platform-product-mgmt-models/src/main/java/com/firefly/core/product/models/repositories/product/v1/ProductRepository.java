package com.firefly.core.product.models.repositories.product.v1;

import com.firefly.core.product.interfaces.enums.core.v1.ProductStatusEnum;
import com.firefly.core.product.interfaces.enums.core.v1.ProductTypeEnum;
import com.firefly.core.product.models.entities.core.v1.Product;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ProductRepository extends BaseRepository<Product, UUID> {
}
