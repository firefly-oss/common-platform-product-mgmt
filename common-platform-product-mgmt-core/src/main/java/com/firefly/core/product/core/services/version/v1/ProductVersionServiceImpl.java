package com.firefly.core.product.core.services.version.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.product.core.mappers.version.v1.ProductVersionMapper;
import com.firefly.core.product.interfaces.dtos.version.v1.ProductVersionDTO;
import com.firefly.core.product.models.entities.version.v1.ProductVersion;
import com.firefly.core.product.models.repositories.version.v1.ProductVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductVersionServiceImpl implements ProductVersionService {

    @Autowired
    private ProductVersionRepository repository;

    @Autowired
    private ProductVersionMapper mapper;

    @Override
    public Mono<PaginationResponse<ProductVersionDTO>> getAllProductVersions(Long productId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        ).onErrorResume(e -> Mono.error(new RuntimeException("Failed to fetch product versions", e)));
    }

    @Override
    public Mono<ProductVersionDTO> createProductVersion(Long productId, ProductVersionDTO productVersionDTO) {
        productVersionDTO.setProductId(productId);
        ProductVersion entity = mapper.toEntity(productVersionDTO);
        return repository.save(entity)
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to create product version", e)));
    }

    @Override
    public Mono<ProductVersionDTO> getProductVersion(Long productId, Long versionId) {
        return repository.findById(versionId)
                .filter(productVersion -> productVersion.getProductId().equals(productId))
                .flatMap(productVersion -> Mono.just(mapper.toDto(productVersion)))
                .switchIfEmpty(Mono.error(new RuntimeException("Product version not found or does not belong to the product")));
    }

    @Override
    public Mono<ProductVersionDTO> updateProductVersion(Long productId, Long versionId, ProductVersionDTO productVersionDTO) {
        return repository.findById(versionId)
                .filter(productVersion -> productVersion.getProductId().equals(productId))
                .flatMap(existingVersion -> {
                    ProductVersion updatedEntity = mapper.toEntity(productVersionDTO);
                    updatedEntity.setProductVersionId(existingVersion.getProductVersionId());
                    updatedEntity.setProductId(productId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Product version not found or does not belong to the product")));
    }

    @Override
    public Mono<Void> deleteProductVersion(Long productId, Long versionId) {
        return repository.findById(versionId)
                .filter(productVersion -> productVersion.getProductId().equals(productId))
                .flatMap(repository::delete)
                .switchIfEmpty(Mono.error(new RuntimeException("Product version not found or does not belong to the product")));
    }
}
