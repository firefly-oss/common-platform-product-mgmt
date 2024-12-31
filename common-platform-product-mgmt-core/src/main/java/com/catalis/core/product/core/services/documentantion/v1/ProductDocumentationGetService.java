package com.catalis.core.product.core.services.documentantion.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.documentantion.v1.ProductDocumentationMapper;
import com.catalis.core.product.interfaces.dtos.documentation.v1.ProductDocumentationDTO;
import com.catalis.core.product.models.entities.documentation.v1.ProductDocumentation;
import com.catalis.core.product.models.repositories.documentation.v1.ProductDocumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductDocumentationGetService {

    @Autowired
    private ProductDocumentationRepository repository;

    @Autowired
    private ProductDocumentationMapper mapper;

    /**
     * Retrieves a specific product documentation record by its ID.
     *
     * @param productDocumentationId the ID of the product documentation to be retrieved
     * @return a Mono containing the ProductDocumentationDTO if found, or an error if the documentation does not exist
     */
    public Mono<ProductDocumentationDTO> getProductDocumentation(Long productDocumentationId) {
        return repository.findById(productDocumentationId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Documentation not found for ID: " + productDocumentationId)))
                .map(mapper::toDto);
    }

    /**
     * Retrieves paginated documentation records associated with the specified product ID.
     *
     * @param productId the ID of the product for which documentation records are being requested
     * @param paginationRequest an object containing pagination details such as page number and page size
     * @return a Mono containing a PaginationResponse with a list of ProductDocumentationDTO objects,
     *         the total number of records, total pages, and the current page number
     */
    public Mono<PaginationResponse<ProductDocumentationDTO>> getProductDocumentationsByProductId(
            Long productId, PaginationRequest paginationRequest) {
        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductDocumentation entities from the repository
        Flux<ProductDocumentation> documents = repository.findByProductId(productId, pageable);

        // Fetch the total count of ProductDocumentation entities for the given productId
        Mono<Long> count = repository.countByProductId(productId);

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return documents
                // Map each ProductDocumentation entity to a ProductDocumentationDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<ProductDocumentationDTO> documentationDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            documentationDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Set the current page number
                    );
                });
    }

}
