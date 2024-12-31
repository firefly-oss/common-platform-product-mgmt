package com.catalis.core.product.core.services.feature.v1;

import com.catalis.core.product.core.mappers.feature.v1.ProductFeatureMapper;
import com.catalis.core.product.interfaces.dtos.feature.v1.ProductFeatureDTO;
import com.catalis.core.product.models.repositories.feature.v1.ProductFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductFeatureCreateService {

    @Autowired
    private ProductFeatureRepository repository;

    @Autowired
    private ProductFeatureMapper mapper;

    /**
     * Creates a new product feature by mapping the provided DTO to an entity,
     * saving the entity in the repository, and mapping the saved entity back to a DTO.
     *
     * @param productFeature the product feature data transfer object containing the details to be created
     * @return a {@code Mono} emitting the created product feature data transfer object
     */
    public Mono<ProductFeatureDTO> createProductFeature(ProductFeatureDTO productFeature) {
        return Mono.just(productFeature)
                .map(mapper::toEntity)
                .flatMap(entity -> repository.save(entity)
                        .doOnNext(savedEntity -> productFeature.setProductFeatureId(savedEntity.getProductFeatureId()))
                )
                .map(mapper::toDto);
    }
    
}