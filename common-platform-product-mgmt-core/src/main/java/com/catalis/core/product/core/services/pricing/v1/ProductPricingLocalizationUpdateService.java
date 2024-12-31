package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.core.product.core.mappers.pricing.v1.ProductPricingLocalizationMapper;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingLocalizationDTO;
import com.catalis.core.product.models.repositories.pricing.v1.ProductPricingLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductPricingLocalizationUpdateService {

    @Autowired
    private ProductPricingLocalizationRepository repository;

    @Autowired
    private ProductPricingLocalizationMapper mapper;

    /**
     * Updates the localization details of a product pricing entry identified by its ID.
     * If the product pricing localization is not found, an error is emitted.
     *
     * @param id the unique identifier of the product pricing localization to be updated
     * @param request the data transfer object containing the updated localization details
     * @return a Mono emitting the updated ProductPricingLocalizationDTO or an error if the operation fails
     */
    public Mono<ProductPricingLocalizationDTO> updateProductPricingLocalization(
            Long id, ProductPricingLocalizationDTO request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Pricing Localization not found for ID: " + id)))
                .flatMap(existingEntity -> {
                    existingEntity.setProductPricingId(request.getProductPricingId());
                    existingEntity.setCurrencyCode(request.getCurrencyCode());
                    existingEntity.setLocalizedAmountValue(request.getLocalizedAmountValue());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDto);
    }
    
}
