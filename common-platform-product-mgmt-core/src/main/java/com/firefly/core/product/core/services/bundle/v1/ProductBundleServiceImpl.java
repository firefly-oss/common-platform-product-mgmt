package com.firefly.core.product.core.services.bundle.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.product.core.mappers.bundle.v1.ProductBundleMapper;
import com.firefly.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
import com.firefly.core.product.models.entities.bundle.v1.ProductBundle;
import com.firefly.core.product.models.repositories.bundle.v1.ProductBundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductBundleServiceImpl implements ProductBundleService {

    @Autowired
    private ProductBundleRepository repository;

    @Autowired
    private ProductBundleMapper mapper;

    @Override
    public Mono<ProductBundleDTO> getById(Long bundleId) {
        return repository.findById(bundleId)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product bundle not found")))
                .onErrorMap(e -> new RuntimeException("An error occurred while retrieving the product bundle", e));
    }

    @Override
    public Mono<PaginationResponse<ProductBundleDTO>> getAll(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findAllBy(pageable),
                () -> repository.count()
        ).onErrorMap(e -> new RuntimeException("An error occurred while retrieving all product bundles", e));
    }

    @Override
    public Mono<ProductBundleDTO> create(ProductBundleDTO productBundleDTO) {
        ProductBundle entity = mapper.toEntity(productBundleDTO);
        return repository.save(entity)
                .map(mapper::toDto)
                .onErrorMap(e -> new RuntimeException("An error occurred while creating the product bundle", e));
    }

    @Override
    public Mono<ProductBundleDTO> update(Long bundleId, ProductBundleDTO productBundleDTO) {
        return repository.findById(bundleId)
                .flatMap(existing -> {
                    existing.setBundleName(productBundleDTO.getBundleName());
                    existing.setBundleDescription(productBundleDTO.getBundleDescription());
                    existing.setBundleStatus(productBundleDTO.getBundleStatus());
                    return repository.save(existing);
                })
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product bundle not found")))
                .onErrorMap(e -> new RuntimeException("An error occurred while updating the product bundle", e));
    }

    @Override
    public Mono<Void> delete(Long bundleId) {
        return repository.findById(bundleId)
                .flatMap(repository::delete)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product bundle not found")))
                .onErrorMap(e -> new RuntimeException("An error occurred while deleting the product bundle", e));
    }
}
