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
public class ProductFeatureUpdateService {
    @Autowired
    private ProductFeatureRepository repository;

    @Autowired
    private ProductFeatureMapper mapper;

    /**
     * Updates an existing product feature based on the provided identifier and updated details.
     * Searches for the product feature by its ID in the repository. If found, updates its attributes
     * with the details from the provided Data Transfer Object (DTO) and saves it back to the repository.
     * If the product feature is not found, an {@code IllegalArgumentException} is returned as an error.
     *
     * @param id the unique identifier of the product feature to update
     * @param productFeature the product feature data transfer object containing the updated details
     * @return a {@code Mono<ProductFeatureDTO>} emitting the updated product feature data transfer object
     *         if the operation is successful, or an error if the product feature does not exist or an error occurs
     */
    public Mono<ProductFeatureDTO> updateProductFeature(Long id, ProductFeatureDTO productFeature) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Feature not found for ID: " + id)))
                .flatMap(existingFeature -> {
                    // Map DTO fields to the existing entity
                    existingFeature.setProductId(productFeature.getProductId());
                    existingFeature.setFeatureName(productFeature.getFeatureName());
                    existingFeature.setFeatureDescription(productFeature.getFeatureDescription());
                    existingFeature.setFeatureType(productFeature.getFeatureType());
                    existingFeature.setIsMandatory(productFeature.getIsMandatory());
                    return repository.save(existingFeature);
                })
                .map(mapper::toDto);
    }

}