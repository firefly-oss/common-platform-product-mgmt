package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.fee.v1.FeeApplicationRuleMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeApplicationRuleDTO;
import com.catalis.core.product.models.repositories.fee.v1.FeeApplicationRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
public class FeeApplicationRuleGetService {

    @Autowired
    private FeeApplicationRuleRepository repository;

    @Autowired
    private FeeApplicationRuleMapper mapper;

    /**
     * Retrieves a FeeApplicationRule identified by its ID and converts it to a FeeApplicationRuleDTO.
     * If the FeeApplicationRule does not exist, an exception is thrown.
     *
     * @param feeApplicationRuleId the ID of the FeeApplicationRule to be retrieved
     * @return a Mono containing the FeeApplicationRuleDTO if the rule exists
     */
    public Mono<FeeApplicationRuleDTO> getFeeApplicationRule(Long feeApplicationRuleId) {
        return repository.findById(feeApplicationRuleId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Fee Application Rule not found for ID: " + feeApplicationRuleId)))
                .map(mapper::toDto);
    }

    public Mono<PaginationResponse<FeeApplicationRuleDTO>> findByFeeComponentId(Long componentId,
                                                                                PaginationRequest pagination){
        return null;
        //TODO: Pending implementation
    }


}
