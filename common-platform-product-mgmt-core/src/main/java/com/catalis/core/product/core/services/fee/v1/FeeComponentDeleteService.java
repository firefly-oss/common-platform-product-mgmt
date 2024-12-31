package com.catalis.core.product.core.services.fee.v1;

import com.catalis.core.product.models.repositories.fee.v1.FeeComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class FeeComponentDeleteService {

    @Autowired
    private FeeComponentRepository repository;

    /**
     * Deletes a FeeComponent identified by the provided feeComponentId.
     * If the FeeComponent does not exist, an exception is thrown.
     * Handles errors during the delete operation by wrapping them in a RuntimeException.
     *
     * @param feeComponentId the ID of the FeeComponent to be deleted
     * @return a Mono that completes when the deletion has been successfully performed
     */
    public Mono<Void> deleteFeeComponent(Long feeComponentId) {
        return repository.findById(feeComponentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Fee Component not found for ID: " + feeComponentId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete Fee Component", e)))
                .then();
    }

}
