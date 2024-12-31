package com.catalis.core.product.core.services.fee.v1;

import com.catalis.core.product.models.repositories.fee.v1.FeeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class FeeStructureDeleteService {

    @Autowired
    private FeeStructureRepository repository;

    /**
     * Deletes a FeeStructure identified by the provided feeStructureId.
     * If the FeeStructure does not exist, an exception is thrown.
     * Handles errors during the delete operation by wrapping them in a RuntimeException.
     *
     * @param feeStructureId the ID of the FeeStructure to be deleted
     * @return a Mono that completes when the deletion has been successfully performed
     */
    public Mono<Void> deleteFeeStructure(Long feeStructureId) {
        return repository.findById(feeStructureId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Fee Structure not found for ID: " + feeStructureId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete Fee Structure", e)))
                .then();
    }

}
