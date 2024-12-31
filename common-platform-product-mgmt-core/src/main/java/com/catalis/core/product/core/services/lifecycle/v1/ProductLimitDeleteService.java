package com.catalis.core.product.core.services.lifecycle.v1;

import com.catalis.core.product.models.repositories.lifecycle.v1.ProductLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductLimitDeleteService {

    @Autowired
    private ProductLimitRepository repository;

    /**
     * Deletes a product limit record identified by the given product limit ID.
     * The method first attempts to find the record by its ID, and if found, deletes it from the repository.
     * If the record is not found, an error is emitted.
     *
     * @param productLimitId the ID of the product limit to be deleted
     * @return a Mono that completes when the deletion process is finished or emits an error if the record is not found or the operation fails
     */
    public Mono<Void> deleteProductLimit(Long productLimitId) {
        return repository.findById(productLimitId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Limit not found for ID: " + productLimitId)))
                .flatMap(repository::delete)
                .then();
    }

}
