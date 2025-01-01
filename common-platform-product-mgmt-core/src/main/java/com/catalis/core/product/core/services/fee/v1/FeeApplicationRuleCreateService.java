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
     * Creates a new Fee Application Rule in the system.
     * Converts the provided FeeApplicationRuleDTO to a FeeApplicationRule entity,
     * saves it in the repository, and then maps the saved entity back to a DTO.
     * Updates the returned DTO with the generated feeApplicationRuleId from the saved entity.
     *
     * @param feeApplicationRule the FeeApplicationRuleDTO object containing the details of the rule to be created
     * @return a Mono emitting the created FeeApplicationRuleDTO with the assigned ID and other details
     */
    public Mono<FeeApplicationRuleDTO> createFeeApplicationRule(FeeApplicationRuleDTO feeApplicationRule) {
        FeeApplicationRule entity = mapper.toEntity(feeApplicationRule);
        return repository.save(entity)
                .map(savedEntity -> {
                    FeeApplicationRuleDTO dto = mapper.toDto(savedEntity);
                    dto.setFeeApplicationRuleId(savedEntity.getFeeApplicationRuleId());
                    return dto;
                });
    }

}
