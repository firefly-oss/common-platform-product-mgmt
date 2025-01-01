package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
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
     * Retrieves a paginated list of FeeStructureDTOs filtered by the specified FeeStructureType.
     *
     * @param feeStructureType the type of Fee Structure to filter by
     * @param paginationRequest the pagination request containing page number, size, and sorting information
     * @return a Mono containing the paginated response with a list of FeeStructureDTOs
     */
    public Mono<PaginationResponse<FeeStructureDTO>> findByFeeStructureType(FeeStructureTypeEnum feeStructureType,
                                                                            PaginationRequest paginationRequest) {

        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByFeeStructureType(feeStructureType, pageable),
                () -> repository.countByFeeStructureType(feeStructureType)
        );

    }
    
}
