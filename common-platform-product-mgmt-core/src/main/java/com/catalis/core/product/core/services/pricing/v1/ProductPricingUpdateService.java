package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.core.product.core.mappers.pricing.v1.ProductPricingMapper;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingDTO;
import com.catalis.core.product.models.entities.pricing.v1.ProductPricing;
import com.catalis.core.product.models.repositories.pricing.v1.ProductPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductPricingUpdateService {

    @Autowired
    private ProductPricingRepository repository;

    @Autowired
    private ProductPricingMapper mapper;

    /**
     * Updates the product pricing details for a specific product pricing ID using the provided
     * ProductPricingDTO object. The method retrieves the existing product pricing entity, updates its
     * attributes with those from the provided DTO, and saves the updated entity back to the repository.
     *
     * @param id the unique identifier of the product pricing to be updated
     * @param productPricingDTO the data transfer object containing the new pricing details to be updated
     * @return a Mono emitting the updated ProductPricingDTO object upon successful update or an error if the ID is not found
     */
    public Mono<ProductPricingDTO> updateProductPricing(Long id, ProductPricingDTO productPricingDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Pricing not found for ID: " + id)))
                .flatMap(existingEntity -> {
                    ProductPricing updatedEntity = mapper.toEntity(productPricingDTO);
                    updatedEntity.setProductPricingId(id);
                    updatedEntity.setProductId(existingEntity.getProductId());
                    updatedEntity.setPricingType(existingEntity.getPricingType());
                    updatedEntity.setAmountValue(existingEntity.getAmountValue());
                    updatedEntity.setAmountUnit(existingEntity.getAmountUnit());
                    updatedEntity.setPricingCondition(existingEntity.getPricingCondition());
                    updatedEntity.setEffectiveDate(existingEntity.getEffectiveDate());
                    updatedEntity.setExpiryDate(existingEntity.getExpiryDate());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDto);
    }

}
