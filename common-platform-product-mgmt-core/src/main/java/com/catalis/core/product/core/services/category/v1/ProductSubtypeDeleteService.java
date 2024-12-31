package com.catalis.core.product.core.services.category.v1;

import com.catalis.core.product.models.repositories.category.v1.ProductSubtypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductSubtypeDeleteService {

    @Autowired
    private ProductSubtypeRepository repository;

    /**
     * Deletes a product subtype by its unique identifier. If the subtype with the given
     * ID does not exist, an error is returned.
     *
     * @param id the unique identifier of the product subtype to be deleted
     * @return a Mono<Void> signaling completion when the deletion is successful, or
     *         an error if the subtype is not found or an exception occurs during deletion
     */
    public Mono<Void> deleteProductSubtype(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product subtype not found for ID: " + id)))
                .flatMap(existingSubtype -> repository.deleteById(id))
                .then();
    }

}
