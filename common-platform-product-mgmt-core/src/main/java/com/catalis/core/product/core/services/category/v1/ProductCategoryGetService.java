package com.catalis.core.product.core.services.category.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.category.v1.ProductCategoryMapper;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
import com.catalis.core.product.models.entities.category.v1.ProductCategory;
import com.catalis.core.product.models.repositories.category.v1.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductCategoryGetService {

    @Autowired
    private ProductCategoryRepository repository;

    @Autowired
    private ProductCategoryMapper mapper;

    /**
     * Retrieves the ProductCategoryDTO for the given category ID.
     * If the category ID is not found, an error is returned.
     *
     * @param id the unique identifier of the product category to be retrieved
     * @return a Mono emitting the ProductCategoryDTO if found, or an error if not found
     */
    public Mono<ProductCategoryDTO> getProductCategory(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product category not found for ID: " + id)))
                .map(mapper::toDto);
    }

    /**
     * Retrieves all product categories with pagination support.
     *
     * @param paginationRequest the request object containing pagination details
     * @return a Mono emitting a PaginationResponse of ProductCategoryDTO, representing the paginated list of product categories;
     * emits an error if any issue occurs during fetching
     */
    public Mono<PaginationResponse<ProductCategoryDTO>> getAllProductCategories(PaginationRequest paginationRequest) {

        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductCategory entities from the repository
        Flux<ProductCategory> categories = repository.findAllBy(pageable);

        // Fetch the total count of ProductCategory entities
        Mono<Long> count = repository.count();

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return categories
                // Map each ProductCategory entity to a ProductCategoryDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<ProductCategoryDTO> categoryDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            categoryDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Set the current page number
                    );
                });

    }


}
