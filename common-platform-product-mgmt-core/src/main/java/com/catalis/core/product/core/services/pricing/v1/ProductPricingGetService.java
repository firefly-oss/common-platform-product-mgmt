package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
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
     * Retrieves paginated pricing information for a specific product identified by its product ID.
     * The method fetches the pricing details, calculates the total count of pricing records,
     * and constructs a paginated response.
     *
     * @param productId the unique identifier of the product whose pricing details are to be retrieved
     * @param paginationRequest the object containing pagination information such as page number and size
     * @return a Mono emitting a PaginationResponse containing a list of ProductPricingDTOs, total records,
     *         total pages, and the current page number
     */
    public Mono<PaginationResponse<ProductPricingDTO>> findByProductId(Long productId,
                                                                       PaginationRequest paginationRequest) {
        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductPricing entities from the repository
        Flux<ProductPricing> pricings = repository.findByProductId(productId, pageable);

        // Fetch the total count of ProductPricing entities for the given productId
        Mono<Long> count = repository.countByProductId(productId);

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return pricings
                // Map each ProductPricing entity to a ProductPricingDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<ProductPricingDTO> productPricingDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            productPricingDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Get the current page number
                    );
                });

    }

}