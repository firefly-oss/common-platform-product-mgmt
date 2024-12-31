package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.pricing.v1.ProductPricingLocalizationMapper;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingLocalizationDTO;
import com.catalis.core.product.models.repositories.pricing.v1.ProductPricingLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
public class ProductPricingLocalizationGetService {

    @Autowired
    private ProductPricingLocalizationRepository repository;

    @Autowired
    private ProductPricingLocalizationMapper mapper;

    /**
     * Retrieves the product pricing localization details for the specified ID.
     * Emits an error if no product pricing localization is found for the given ID.
     *
     * @param productPricingLocalizationId the unique identifier of the product pricing localization to retrieve
     * @return a Mono emitting the ProductPricingLocalizationDTO containing the localization details,
     *         or an error if the product pricing localization is not found
     */
    public Mono<ProductPricingLocalizationDTO> getProductPricingLocalization(Long productPricingLocalizationId) {
        return repository.findById(productPricingLocalizationId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Pricing Localization not found for ID: " + productPricingLocalizationId)))
                .map(mapper::toDto);
    }

    public Mono<PaginationResponse<ProductPricingLocalizationDTO>> findByProductPricingId(Long pricingId, PaginationRequest pagination){
        return null;
        //TODO: Pending implementation
    }

}
