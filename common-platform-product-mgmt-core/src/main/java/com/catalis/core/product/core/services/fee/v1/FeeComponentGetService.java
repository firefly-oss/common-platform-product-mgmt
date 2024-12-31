package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.fee.v1.FeeComponentMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeComponentDTO;
import com.catalis.core.product.interfaces.enums.fee.v1.FeeTypeEnum;
import com.catalis.core.product.models.repositories.fee.v1.FeeComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
public class FeeComponentGetService {
    
    @Autowired
    private FeeComponentRepository repository;

    @Autowired
    private FeeComponentMapper mapper;

    /**
     * Retrieves a FeeComponent identified by its ID and converts it to a FeeComponentDTO.
     * If the FeeComponent does not exist, an exception is thrown.
     *
     * @param feeComponentId the ID of the FeeComponent to be retrieved
     * @return a Mono containing the FeeComponentDTO if the component exists
     */
    public Mono<FeeComponentDTO> getFeeComponent(Long feeComponentId) {
        return repository.findById(feeComponentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Fee Component not found for ID: " + feeComponentId)))
                .map(mapper::toDto);
    }

    public Mono<PaginationResponse<FeeComponentDTO>> findByFeeType(FeeTypeEnum feeType, PaginationRequest pagination){
        return null;
        //TODO: Pending implementation
    }

    public Mono<PaginationResponse<FeeComponentDTO>> findByFeeStructureId(Long feeStructureId, PaginationRequest pagination){
        return null;
        //TODO: Pending implementation
    }
    
}
