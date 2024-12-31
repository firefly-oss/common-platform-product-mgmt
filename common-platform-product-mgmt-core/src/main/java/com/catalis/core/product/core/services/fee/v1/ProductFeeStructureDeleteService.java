package com.catalis.core.product.core.services.fee.v1;

import com.catalis.core.product.models.repositories.fee.v1.ProductFeeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductFeeStructureDeleteService {

    @Autowired
    private ProductFeeStructureRepository repository;

    /**
     * Deletes a Product Fee Structure identified by the provided productFeeStructureId.
     * If the Product Fee Structure does not exist, an exception is thrown.
     * Handles errors during the delete operation by wrapping them in a RuntimeException.
     *
     * @param productFeeStructureId the ID of the Product Fee Structure to be deleted
     * @return a Mono that completes when the deletion has been successfully performed
     */
    public Mono<Void> deleteProductFeeStructure(Long productFeeStructureId) {
        return repository.findById(productFeeStructureId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Fee Structure not found for ID: " + productFeeStructureId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete Product Fee Structure", e)))
                .then();
    }

}
