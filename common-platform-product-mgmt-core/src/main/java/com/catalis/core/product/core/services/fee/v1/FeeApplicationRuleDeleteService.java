package com.catalis.core.product.core.services.fee.v1;

import com.catalis.core.product.models.repositories.fee.v1.FeeApplicationRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class FeeApplicationRuleDeleteService {

    @Autowired
    private FeeApplicationRuleRepository repository;

    /**
     * Deletes a FeeApplicationRule identified by the provided feeApplicationRuleId.
     * If the rule does not exist, an exception is thrown.
     * Handles errors during the delete operation by wrapping them in a RuntimeException.
     *
     * @param feeApplicationRuleId the ID of the FeeApplicationRule to be deleted
     * @return a Mono that completes when the deletion has been successfully performed
     */
    public Mono<Void> deleteFeeApplicationRule(Long feeApplicationRuleId) {
        return repository.findById(feeApplicationRuleId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Fee Application Rule not found for ID: " + feeApplicationRuleId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete Fee Application Rule", e)))
                .then();
    }

}
