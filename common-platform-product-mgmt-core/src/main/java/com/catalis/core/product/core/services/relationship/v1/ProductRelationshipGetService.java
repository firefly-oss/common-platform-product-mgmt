package com.catalis.core.product.core.services.relationship.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.relationship.v1.ProductRelationshipMapper;
import com.catalis.core.product.interfaces.dtos.relationship.v1.ProductRelationshipDTO;
import com.catalis.core.product.models.entities.relationship.v1.ProductRelationship;
import com.catalis.core.product.models.repositories.relationship.v1.ProductRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductRelationshipGetService {

    @Autowired
    private ProductRelationshipRepository repository;

    @Autowired
    private ProductRelationshipMapper mapper;

    /**
     * Retrieves a product relationship by its ID and maps it to a {@code ProductRelationshipDTO}.
     * If the relationship does not exist, an error is returned.
     *
     * @param productRelationshipId the ID of the product relationship to retrieve
     * @return a {@code Mono<ProductRelationshipDTO>} containing the product relationship details,
     *         or an error if no relationship is found for the given ID
     */
    public Mono<ProductRelationshipDTO> getProductRelationship(Long productRelationshipId) {
        return repository.findById(productRelationshipId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Relationship not found for ID: " + productRelationshipId)))
                .map(mapper::toDto);
    }

    /**
     * Retrieves a paginated list of product relationships for a given product ID and
     * maps the results to {@code ProductRelationshipDTO}.
     *
     * @param productId the ID of the product whose relationships are to be retrieved
     * @param paginationRequest the pagination parameters including page number and size
     * @return a {@code Mono<PaginationResponse<ProductRelationshipDTO>>} containing a paginated
     *         response with the list of product relationships and associated metadata
     */
    public Mono<PaginationResponse<ProductRelationshipDTO>> findByProductId(Long productId,
                                                                            PaginationRequest paginationRequest) {
        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductRelationship entities from the repository
        Flux<ProductRelationship> relationships = repository.findByProductId(productId, pageable);

        // Fetch the total count of ProductRelationship entities for the given product ID
        Mono<Long> count = repository.countByProductId(productId);

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return relationships
                // Map each ProductRelationship entity to a ProductRelationshipDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<ProductRelationshipDTO> productRelationshipDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            productRelationshipDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Get the current page number
                    );
                });

    }

}