package com.firefly.core.product.core.services.feature.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.product.core.mappers.feature.v1.ProductFeatureMapper;
import com.firefly.core.product.interfaces.dtos.feature.v1.ProductFeatureDTO;
import com.firefly.core.product.models.entities.feature.v1.ProductFeature;
import com.firefly.core.product.models.repositories.feature.v1.ProductFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class ProductFeatureServiceImpl implements ProductFeatureService {

    @Autowired
    private ProductFeatureRepository repository;

    @Autowired
    private ProductFeatureMapper mapper;

    @Override
    public Mono<PaginationResponse<ProductFeatureDTO>> getAllFeatures(UUID productId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        ).onErrorMap(e -> new RuntimeException("Error occurred while retrieving features", e));
    }

    @Override
    public Mono<ProductFeatureDTO> createFeature(UUID productId, ProductFeatureDTO featureDTO) {
        ProductFeature entity = mapper.toEntity(featureDTO);
        entity.setProductId(productId);
        return repository.save(entity)
                .map(mapper::toDto)
                .onErrorMap(e -> new RuntimeException("Error occurred while creating feature", e));
    }

    @Override
    public Mono<ProductFeatureDTO> getFeature(UUID productId, UUID featureId) {
        return repository.findById(featureId)
                .filter(feature -> feature.getProductId().equals(productId))
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Feature not found or does not belong to the product")))
                .onErrorMap(e -> new RuntimeException("Error occurred while retrieving feature", e));
    }

    @Override
    public Mono<ProductFeatureDTO> updateFeature(UUID productId, UUID featureId, ProductFeatureDTO featureDTO) {
        return repository.findById(featureId)
                .filter(feature -> feature.getProductId().equals(productId))
                .flatMap(existingFeature -> {
                    ProductFeature toUpdate = mapper.toEntity(featureDTO);
                    toUpdate.setProductFeatureId(existingFeature.getProductFeatureId());
                    toUpdate.setProductId(existingFeature.getProductId());
                    return repository.save(toUpdate);
                })
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Feature not found or does not belong to the product")))
                .onErrorMap(e -> new RuntimeException("Error occurred while updating feature", e));
    }

    @Override
    public Mono<Void> deleteFeature(UUID productId, UUID featureId) {
        return repository.findById(featureId)
                .filter(feature -> feature.getProductId().equals(productId))
                .flatMap(repository::delete)
                .onErrorMap(e -> new RuntimeException("Error occurred while deleting feature", e));
    }
}