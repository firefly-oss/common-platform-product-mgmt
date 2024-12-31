package com.catalis.core.product.core.services.fee.v1;

import com.catalis.core.product.core.mappers.fee.v1.FeeApplicationRuleMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeApplicationRuleDTO;
import com.catalis.core.product.models.repositories.fee.v1.FeeApplicationRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class FeeApplicationRuleUpdateService {

    @Autowired
    private FeeApplicationRuleRepository repository;

    @Autowired
    private FeeApplicationRuleMapper mapper;

    /**
     * Updates an existing Fee Application Rule identified by its ID with the details
     * provided in the given FeeApplicationRuleDTO. If the Fee Application Rule is not found
     * for the specified ID, an error is returned. Updates include fee component ID, rule description,
     * rule conditions, effective date, and expiry date.
     *
     * @param id the ID of the Fee Application Rule to be updated
     * @param feeApplicationRuleDTO the FeeApplicationRuleDTO containing the updated details
     * @return a Mono containing the updated FeeApplicationRuleDTO if the update is successful
     */
    public Mono<FeeApplicationRuleDTO> updateFeeApplicationRule(Long id, FeeApplicationRuleDTO feeApplicationRuleDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Fee Application Rule not found for ID: " + id)))
                .flatMap(existingEntity -> {
                    existingEntity.setFeeComponentId(feeApplicationRuleDTO.getFeeComponentId());
                    existingEntity.setRuleDescription(feeApplicationRuleDTO.getRuleDescription());
                    existingEntity.setRuleConditions(feeApplicationRuleDTO.getRuleConditions());
                    existingEntity.setEffectiveDate(feeApplicationRuleDTO.getEffectiveDate());
                    existingEntity.setExpiryDate(feeApplicationRuleDTO.getExpiryDate());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to update Fee Application Rule", e)));
    }

}
