package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.pricing.v1.ProductPricingLocalizationMapper;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingLocalizationDTO;
import com.catalis.core.product.models.entities.pricing.v1.ProductPricingLocalization;
import com.catalis.core.product.models.repositories.pricing.v1.ProductPricingLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    /**
     * Retrieves a paginated list of product pricing localizations for the specified product pricing ID.
     * The list includes localization details such as currency code and localized amount.
     *
     * @param pricingId the unique identifier of the product pricing for which localizations are to be fetched
     * @param paginationRequest the pagination request containing page size and page number
     * @return a Mono emitting the paginated response containing a list of ProductPricingLocalizationDTO objects,
     *         the total count of matching records, total pages, and the current page number
     */
    public Mono<PaginationResponse<ProductPricingLocalizationDTO>> findByProductPricingId(
            Long pricingId, PaginationRequest paginationRequest) {

        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductPricingLocalization entities from the repository
        Flux<ProductPricingLocalization> pricingLocalization = repository.findByProductPricingId(pricingId, pageable);

        // Fetch the total count of ProductPricingLocalization entities for the given pricing ID
        Mono<Long> count = repository.countByProductPricingId(pricingId);

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return pricingLocalization
                // Map each ProductPricingLocalization entity to a ProductPricingLocalizationDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<ProductPricingLocalizationDTO> productPricingLocalizationDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            productPricingLocalizationDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Get the current page number
                    );
                });

    }

}
