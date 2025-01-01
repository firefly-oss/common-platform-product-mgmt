package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
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
     * Retrieves a paginated list of FeeComponentDTOs filtered by the specified fee type.
     *
     * @param feeType the type of fee to filter the fee components by
     * @param paginationRequest the pagination request containing details such as page size and page number
     * @return a Mono emitting a PaginationResponse containing a list of FeeComponentDTOs and pagination metadata
     */
    public Mono<PaginationResponse<FeeComponentDTO>> findByFeeType(FeeTypeEnum feeType, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByFeeType(feeType, pageable),
                () -> repository.countByFeeType(feeType)
        );
    }

    /**
     * Retrieves a paginated list of FeeComponentDTOs associated with a specified fee structure ID.
     *
     * @param feeStructureId the unique identifier of the fee structure whose associated fee components are to be retrieved
     * @param paginationRequest the pagination request containing pagination details such as page size and page number
     * @return a Mono emitting a PaginationResponse containing a list of FeeComponentDTOs and pagination metadata
     */
    public Mono<PaginationResponse<FeeComponentDTO>> findByFeeStructureId(Long feeStructureId, PaginationRequest paginationRequest) {

        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByFeeStructureId(feeStructureId, pageable),
                () -> repository.countByFeeStructureId(feeStructureId)
        );

    }
}