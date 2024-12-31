package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.pricing.v1.ProductPricingMapper;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingDTO;
import com.catalis.core.product.models.repositories.pricing.v1.ProductPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductPricingGetService {

    @Autowired
    private ProductPricingRepository repository;

    @Autowired
    private ProductPricingMapper mapper;

    /**
     * Fetches the pricing information for a specific product identified by the given product pricing ID.
     * If no product pricing is found for the specified ID, an error will be emitted.
     *
     * @param productPricingId the unique identifier of the product pricing to retrieve
     * @return a Mono emitting the ProductPricingDTO containing the pricing details, or an error if the product pricing is not found
     */
    public Mono<ProductPricingDTO> getProductPricing(Long productPricingId) {
        return repository.findById(productPricingId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Pricing not found for ID: " + productPricingId)))
                .map(mapper::toDto);
    }

    public Mono<PaginationResponse<ProductPricingDTO>> findByProductId(Long productId, PaginationRequest pagination){
        return null;
        //TODO: Pending implementation
    }

}