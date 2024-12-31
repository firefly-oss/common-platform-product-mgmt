package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.fee.v1.FeeStructureMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeStructureDTO;
import com.catalis.core.product.interfaces.enums.fee.v1.FeeStructureTypeEnum;
import com.catalis.core.product.models.entities.fee.v1.FeeStructure;
import com.catalis.core.product.models.repositories.fee.v1.FeeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FeeStructureGetService {

    @Autowired
    private FeeStructureRepository repository;

    @Autowired
    private FeeStructureMapper mapper;

    /**
     * Retrieves a FeeStructure identified by its ID and converts it to a FeeStructureDTO.
     * If the FeeStructure does not exist, an exception is thrown.
     *
     * @param feeStructureId the ID of the FeeStructure to be retrieved
     * @return a Mono containing the FeeStructureDTO if the FeeStructure exists
     */
    public Mono<FeeStructureDTO> getFeeStructure(Long feeStructureId) {
        return repository.findById(feeStructureId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Fee Structure not found for ID: " + feeStructureId)))
                .map(mapper::toDto);
    }

    /**
     * Retrieves a paginated list of FeeStructureDTOs based on a specific FeeStructureType.
     *
     * @param feeStructureType the type of FeeStructure to filter by
     * @param paginationRequest contains pagination details such as page number and size
     * @return a Mono containing a PaginationResponse with the list of FeeStructureDTOs, total count, total pages,
     *         and the current page number
     */
    public Mono<PaginationResponse<FeeStructureDTO>> findByFeeStructureType(FeeStructureTypeEnum feeStructureType,
                                                                            PaginationRequest paginationRequest) {

        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of FeeStructure entities from the repository
        Flux<FeeStructure> structures = repository.findByFeeStructureType(feeStructureType, pageable);

        // Fetch the total count of FeeStructure entities for the given feeStructureType
        Mono<Long> count = repository.countByFeeStructureType(feeStructureType);

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return structures
                // Map each FeeStructure entity to a FeeStructureDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<FeeStructureDTO> feeStructureDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            feeStructureDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Set the current page number
                    );
                });

    }
    
}
