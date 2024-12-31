package com.catalis.core.product.core.services.lifecycle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.lifecycle.v1.ProductLimitMapper;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLimitDTO;
import com.catalis.core.product.models.entities.lifecycle.v1.ProductLimit;
import com.catalis.core.product.models.repositories.lifecycle.v1.ProductLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductLimitGetService {

    @Autowired
    private ProductLimitRepository repository;

    @Autowired
    private ProductLimitMapper mapper;

    /**
     * Retrieves a product limit record by its ID.
     * If the record is found, it is mapped to a ProductLimitDTO and returned.
     * If the record is not found, an error is emitted.
     *
     * @param productLimitId the ID of the product limit to retrieve
     * @return a Mono containing the ProductLimitDTO if found, or an error if the record does not exist
     */
    public Mono<ProductLimitDTO> getProductLimit(Long productLimitId) {
        return repository.findById(productLimitId)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Limit not found for ID: " + productLimitId)));
    }

    /**
     * Retrieves a paginated list of ProductLimitDTO records for a given product ID.
     * The method fetches the product limits based on the product ID and the provided pagination parameters,
     * then maps the results to DTOs and returns them with pagination metadata.
     *
     * @param productId the ID of the product whose limits are to be fetched
     * @param paginationRequest the pagination request containing information such as page number, size, and sorting
     * @return a Mono containing a PaginationResponse of ProductLimitDTOs with the list of product limits, total count, total pages, and current page
     */
    public Mono<PaginationResponse<ProductLimitDTO>> findByProductId(Long productId,
                                                                     PaginationRequest paginationRequest) {
        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductLimit entities from the repository
        Flux<ProductLimit> limits = repository.findByProductId(productId, pageable);

        // Fetch the total count of ProductLimit entities for the given productId
        Mono<Long> count = repository.countByProductId(productId);

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return limits
                // Map each ProductLimit entity to a ProductLimitDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<ProductLimitDTO> productLimitDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            productLimitDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Get the current page number
                    );
                });

    }

}