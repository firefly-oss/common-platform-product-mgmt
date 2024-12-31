package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.fee.v1.FeeComponentMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeComponentDTO;
import com.catalis.core.product.interfaces.enums.fee.v1.FeeTypeEnum;
import com.catalis.core.product.models.entities.fee.v1.FeeComponent;
import com.catalis.core.product.models.repositories.fee.v1.FeeComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FeeComponentGetService {

    @Autowired
    private FeeComponentRepository repository;

    @Autowired
    private FeeComponentMapper mapper;

    /**
     * Retrieves a FeeComponentDTO by its unique identifier.
     *
     * @param feeComponentId the unique identifier of the Fee Component to retrieve.
     * @return a Mono emitting the FeeComponentDTO if found, or an error if the Fee Component is not found.
     */
    public Mono<FeeComponentDTO> getFeeComponent(Long feeComponentId) {
        return repository.findById(feeComponentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Fee Component not found for ID: " + feeComponentId)))
                .map(mapper::toDto);
    }

    /**
     * Creates a paginated response for a list of FeeComponent entities.
     * Converts the entities into DTOs and compiles them into a PaginationResponse
     * object containing metadata about the total number of items, number of pages,
     * and the current page.
     *
     * @param components   the Flux stream of FeeComponent entities to be converted to DTOs
     * @param totalCount   a Mono representing the total count of FeeComponent entities matching the query
     * @param pageable     the Pageable object containing pagination information (e.g., page size, page number)
     * @return a Mono emitting a PaginationResponse containing a list of FeeComponentDTOs and pagination information
     */
    private Mono<PaginationResponse<FeeComponentDTO>> createPaginatedResponse(
            Flux<FeeComponent> components, Mono<Long> totalCount, Pageable pageable) {
        return components.map(mapper::toDto)
                .collectList()
                .zipWith(totalCount)
                .map(tuple -> {
                    List<FeeComponentDTO> feeComponentDTOS = tuple.getT1();
                    long total = tuple.getT2();
                    return new PaginationResponse<>(
                            feeComponentDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()),
                            pageable.getPageNumber()
                    );
                });
    }

    /**
     * Retrieves a paginated response containing fee components filtered by the specified fee type.
     *
     * @param feeType the type of fee to filter the components by
     * @param paginationRequest the pagination request containing page size and page number
     * @return a Mono emitting a PaginationResponse with a list of FeeComponentDTO and pagination details
     */
    public Mono<PaginationResponse<FeeComponentDTO>> findByFeeType(FeeTypeEnum feeType, PaginationRequest paginationRequest) {
        Pageable pageable = paginationRequest.toPageable();
        return createPaginatedResponse(
                repository.findByFeeType(feeType, pageable),
                repository.countByFeeType(feeType),
                pageable
        );
    }

    /**
     * Retrieves a paginated response of FeeComponentDTO objects associated with a specific fee structure ID.
     *
     * @param feeStructureId the ID of the fee structure to filter FeeComponents.
     * @param paginationRequest the pagination request containing page size and page number.
     * @return a reactive Mono containing the paginated response with the list of FeeComponentDTOs and pagination details.
     */
    public Mono<PaginationResponse<FeeComponentDTO>> findByFeeStructureId(Long feeStructureId, PaginationRequest paginationRequest) {
        Pageable pageable = paginationRequest.toPageable();
        return createPaginatedResponse(
                repository.findByFeeStructureId(feeStructureId, pageable),
                repository.countByFeeStructureId(feeStructureId),
                pageable
        );
    }
}