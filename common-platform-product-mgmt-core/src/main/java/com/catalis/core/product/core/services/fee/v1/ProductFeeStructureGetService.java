package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.fee.v1.ProductFeeStructureMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.ProductFeeStructureDTO;
import com.catalis.core.product.models.entities.fee.v1.ProductFeeStructure;
import com.catalis.core.product.models.repositories.fee.v1.ProductFeeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductFeeStructureGetService {

    @Autowired
    private ProductFeeStructureRepository repository;

    @Autowired
    private ProductFeeStructureMapper mapper;

    /**
     * Retrieves a ProductFeeStructure identified by its ID and converts it to a ProductFeeStructureDTO.
     * If the ProductFeeStructure does not exist, an exception is thrown.
     *
     * @param productFeeStructureId the ID of the ProductFeeStructure to be retrieved
     * @return a Mono containing the ProductFeeStructureDTO if the structure exists
     */
    public Mono<ProductFeeStructureDTO> getProductFeeStructure(Long productFeeStructureId) {
        return repository.findById(productFeeStructureId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Fee Structure not found for ID: " + productFeeStructureId)))
                .map(mapper::toDto);
    }

    /**
     * Retrieves a paginated list of ProductFeeStructureDTO objects for the specified product ID.
     * The method applies pagination and transforms the ProductFeeStructure entities into their corresponding DTOs.
     *
     * @param productId the unique identifier of the product whose fee structures are being retrieved
     * @param paginationRequest the configuration object specifying pagination details such as page number and size
     * @return a Mono containing the paginated response of ProductFeeStructureDTO objects along with metadata such as total count and pages
     */
    public Mono<PaginationResponse<ProductFeeStructureDTO>> findByProductId(Long productId,
                                                                            PaginationRequest paginationRequest) {
        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductFeeStructure entities from the repository
        Flux<ProductFeeStructure> structures = repository.findByProductId(productId, pageable);

        // Fetch the total count of ProductFeeStructure entities for the given productId
        Mono<Long> count = repository.countByProductId(productId);

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return structures
                // Map each ProductFeeStructure entity to a ProductFeeStructureDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<ProductFeeStructureDTO> productFeeStructureDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            productFeeStructureDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Set the current page number
                    );
                });
    }

}
