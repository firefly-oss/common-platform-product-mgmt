package com.firefly.core.product.core.services.lifecycle.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.product.core.mappers.lifecycle.v1.ProductLifecycleMapper;
import com.firefly.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import com.firefly.core.product.models.entities.lifecycle.v1.ProductLifecycle;
import com.firefly.core.product.models.repositories.lifecycle.v1.ProductLifecycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductLifecycleServiceImpl implements ProductLifecycleService {

    @Autowired
    private ProductLifecycleRepository repository;

    @Autowired
    private ProductLifecycleMapper mapper;

    @Override
    public Mono<PaginationResponse<ProductLifecycleDTO>> getProductLifecycles(Long productId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        ).onErrorMap(e -> new RuntimeException("Error retrieving product lifecycles", e));
    }

    @Override
    public Mono<ProductLifecycleDTO> createProductLifecycle(Long productId, ProductLifecycleDTO request) {
        request.setProductId(productId);
        ProductLifecycle entity = mapper.toEntity(request);
        return repository.save(entity)
                .map(mapper::toDto)
                .onErrorMap(e -> new RuntimeException("Error creating product lifecycle", e));
    }

    @Override
    public Mono<ProductLifecycleDTO> getProductLifecycle(Long productId, Long lifecycleId) {
        return repository.findById(lifecycleId)
                .filter(entity -> entity.getProductId().equals(productId))
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Product lifecycle not found")))
                .onErrorMap(e -> new RuntimeException("Error retrieving product lifecycle", e));
    }

    @Override
    public Mono<ProductLifecycleDTO> updateProductLifecycle(Long productId, Long lifecycleId, ProductLifecycleDTO request) {
        return repository.findById(lifecycleId)
                .filter(entity -> entity.getProductId().equals(productId))
                .flatMap(existingEntity -> {
                    try {
                        ProductLifecycle updatedEntity = mapper.toEntity(request);
                        updatedEntity.setProductLifecycleId(lifecycleId);
                        updatedEntity.setProductId(productId);
                        return repository.save(updatedEntity).map(mapper::toDto);
                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("Invalid update request data", e));
                    }
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Product lifecycle not found or not associated with the given product")))
                .onErrorMap(e -> new RuntimeException("Error updating product lifecycle", e));
    }

    @Override
    public Mono<Void> deleteProductLifecycle(Long productId, Long lifecycleId) {
        return repository.findById(lifecycleId)
                .filter(entity -> entity.getProductId().equals(productId))
                .switchIfEmpty(Mono.error(new RuntimeException("Product lifecycle not found or not associated with the given product")))
                .flatMap(repository::delete)
                .onErrorMap(e -> new RuntimeException("Error deleting product lifecycle", e));
    }
}