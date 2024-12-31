package com.catalis.core.product.core.services.lifecycle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.lifecycle.v1.ProductLifecycleMapper;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import com.catalis.core.product.models.entities.lifecycle.v1.ProductLifecycle;
import com.catalis.core.product.models.repositories.lifecycle.v1.ProductLifecycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductLifecycleGetService {

    @Autowired
    private ProductLifecycleRepository repository;

    @Autowired
    private ProductLifecycleMapper mapper;

    /**
     * Retrieves a product lifecycle record by its ID.
     * If the record is found, it is mapped to a ProductLifecycleDTO and returned.
     * If the record is not found, an error is emitted.
     *
     * @param productLifecycleId the ID of the product lifecycle to retrieve
     * @return a Mono containing the ProductLifecycleDTO if found, or an error if the record does not exist
     */
    public Mono<ProductLifecycleDTO> getProductLifecycle(Long productLifecycleId) {
        return repository.findById(productLifecycleId)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Lifecycle not found for ID: " + productLifecycleId)));
    }

    /**
     * Retrieves paginated product lifecycle records associated with a specific product ID.
     * The method fetches the product lifecycles from the database, maps them to DTOs,
     * and combines them with pagination data to produce a paginated response.
     *
     * @param productId the ID of the product for which lifecycle records are being retrieved
     * @param paginationRequest the pagination request object which specifies page size and page number
     * @return a Mono containing the PaginationResponse with a list of ProductLifecycleDTOs,
     *         total count of records, total pages, and current page number
     */
    public Mono<PaginationResponse<ProductLifecycleDTO>> findByProductId(Long productId,
                                                                         PaginationRequest paginationRequest) {
        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductLifecycle entities from the repository
        Flux<ProductLifecycle> lifecycles = repository.findByProductId(productId, pageable);

        // Fetch the total count of ProductLifecycle entities for the given productId
        Mono<Long> count = repository.countByProductId(productId);

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return lifecycles
                // Map each ProductLifecycle entity to a ProductLifecycleDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<ProductLifecycleDTO> productLifecycleDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            productLifecycleDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Set the current page number
                    );
                });
    }

}