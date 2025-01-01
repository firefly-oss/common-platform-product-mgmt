package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.pricing.v1.ProductPricingMapper;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingDTO;
import com.catalis.core.product.models.entities.pricing.v1.ProductPricing;
import com.catalis.core.product.models.repositories.pricing.v1.ProductPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    /**
     * Retrieves paginated pricing information for a specific product identified by the given product ID.
     * The method applies pagination and maps the results to a DTO representation.
     *
     * @param productId the unique identifier of the product whose pricing information is to be retrieved
     * @param paginationRequest the pagination request containing page size, page number, and other pagination parameters
     * @return a Mono emitting a PaginationResponse containing a list of ProductPricingDTO objects for the specified product ID
     */
    public Mono<PaginationResponse<ProductPricingDTO>> findByProductId(Long productId,
                                                                       PaginationRequest paginationRequest) {

        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        );

    }

}