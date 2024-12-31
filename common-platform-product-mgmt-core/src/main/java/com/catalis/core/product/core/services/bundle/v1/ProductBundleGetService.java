package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.bundle.v1.ProductBundleMapper;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
import com.catalis.core.product.models.entities.bundle.v1.ProductBundle;
import com.catalis.core.product.models.repositories.bundle.v1.ProductBundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductBundleGetService {

    @Autowired
    private ProductBundleRepository repository;

    @Autowired
    private ProductBundleMapper mapper;

    /**
     * Retrieves a ProductBundle by its ID and maps it to a ProductBundleDTO.
     * If no ProductBundle is found with the specified ID, an error is emitted.
     * Any failures during retrieval will result in an error being emitted.
     *
     * @param id the ID of the ProductBundle to be retrieved
     * @return a Mono emitting the retrieved ProductBundleDTO, or an error if the ProductBundle is not found
     *         or if there is a failure during the retrieval process
     * @throws IllegalArgumentException if the ProductBundle with the given ID does not exist
     * @throws RuntimeException if there is a failure during the retrieval process
     */
    private Mono<ProductBundleDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ProductBundle with id " + id + " not found")))
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve ProductBundle", e)));
    }

    /**
     * Retrieves a paginated list of ProductBundleDTO objects based on the provided PaginationRequest.
     * Transforms ProductBundle entities into DTOs and aggregates them into a paginated response.
     *
     * @param paginationRequest the request containing pagination parameters such as page number and size
     * @return a Mono emitting a PaginationResponse containing a list of ProductBundleDTOs and pagination metadata
     */
    private Mono<PaginationResponse<ProductBundleDTO>> getAll(PaginationRequest paginationRequest) {
        // Convert PaginationRequest to Spring's Pageable object for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductBundle entities using the repository
        Flux<ProductBundle> bundles = repository.findAllBy(pageable);

        // Fetch the total count of ProductBundle entities from the repository
        Mono<Long> count = repository.count();

        // Transform the fetched entities into DTOs, combine with the count, and return them as a paginated response
        return bundles
                // Map each ProductBundle entity to a ProductBundleDTO using the mapper
                .map(mapper::toDto)

                // Collect the transformed DTOs into a list
                .collectList()

                // Combine the list of DTOs with the total count
                .zipWith(count)

                // Create a PaginationResponse object with the DTOs, total count, and pagination details
                .map(tuple -> {
                    List<ProductBundleDTO> bundlesDTO = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Return a new PaginationResponse object containing the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            bundlesDTO,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate total pages
                            pageable.getPageNumber() // Current page number
                    );
                });
    }

}
