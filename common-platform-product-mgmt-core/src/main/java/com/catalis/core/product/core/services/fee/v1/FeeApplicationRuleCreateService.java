package com.catalis.core.product.core.services.fee.v1;

import com.catalis.core.product.core.mappers.fee.v1.FeeApplicationRuleMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeApplicationRuleDTO;
import com.catalis.core.product.models.entities.fee.v1.FeeApplicationRule;
import com.catalis.core.product.models.repositories.fee.v1.FeeApplicationRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class FeeApplicationRuleCreateService {

    @Autowired
    private FeeApplicationRuleRepository repository;
    
    @Autowired
    private FeeApplicationRuleMapper mapper;

    /**
     * Creates a new FeeApplicationRule and returns the corresponding FeeApplicationRuleDTO.
     * This method saves the provided FeeApplicationRule entity in the repository and retrieves the saved entity
     * from the database to convert it into its DTO representation.
     *
     * @param feeApplicationRule the FeeApplicationRule entity to be created
     * @return a Mono containing the FeeApplicationRuleDTO of the created entity
     */
    public Mono<FeeApplicationRuleDTO> createFeeApplicationRule(FeeApplicationRule feeApplicationRule) {
        return repository.save(feeApplicationRule)
                .flatMap(savedEntity -> repository.findById(savedEntity.getFeeApplicationRuleId())
                        .map(mapper::toDto));
    }

}
