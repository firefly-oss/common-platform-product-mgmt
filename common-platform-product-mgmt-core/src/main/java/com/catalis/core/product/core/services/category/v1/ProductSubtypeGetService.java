package com.catalis.core.product.core.services.category.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.category.v1.ProductSubtypeMapper;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductSubtypeDTO;
import com.catalis.core.product.models.entities.category.v1.ProductSubtype;
import com.catalis.core.product.models.repositories.category.v1.ProductSubtypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductSubtypeGetService {

    @Autowired
    private ProductSubtypeRepository repository;

    @Autowired
    private ProductSubtypeMapper mapper;


    /**
     * Retrieves the ProductSubtypeDTO for the given subtype ID.
     * If the subtype ID is not found in the repository, an error is returned.
     * Additionally, any encountered errors during the processing are wrapped and propagated.
     *
     * @param id the unique identifier of the product subtype to be retrieved
     * @return a Mono emitting the ProductSubtypeDTO if found, or an error if not found
     */
    public Mono<ProductSubtypeDTO> getProductSubtype(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product subtype not found for ID: " + id)))
                .map(mapper::toDto)
                .onErrorMap(ex -> new RuntimeException("An error occurred while retrieving the product subtype.", ex));
    }

    /**
     * Retrieves a paginated list of ProductSubtypeDTOs by the given category ID.
     * If the category ID is not found, an empty paginated response is returned.
     *
     * @param categoryId        the unique identifier of the product category
     * @param paginationRequest the pagination request containing page size and number
     * @return a Mono emitting a PaginationResponse of ProductSubtypeDTOs
     */
    public Mono<PaginationResponse<ProductSubtypeDTO>> getAllProductSubtypesByCategoryId(Long categoryId,
                                                                                         PaginationRequest paginationRequest) {
        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductSubtype entities from the repository
        Flux<ProductSubtype> subtypes = repository.findByProductCategoryId(categoryId, pageable);

        // Fetch the total count of ProductSubtype entities
        Mono<Long> count = repository.count();

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return subtypes
                // Map each ProductSubtype entity to a ProductSubtypeDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<ProductSubtypeDTO> subtypeDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            subtypeDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Set the current page number
                    );
                });
    }

}