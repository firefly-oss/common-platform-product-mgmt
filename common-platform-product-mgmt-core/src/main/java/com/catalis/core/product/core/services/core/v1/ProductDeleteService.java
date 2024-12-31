package com.catalis.core.product.core.services.core.v1;

import com.catalis.core.product.models.repositories.product.v1.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductDeleteService {

    @Autowired
    private ProductRepository repository;

    /**
     * Deletes a product identified by the given ID. If the product does not exist,
     * an error will be returned.
     *
     * @param id the ID of the product to delete
     * @return a Mono emitting completion signal upon successful deletion, or an error if the product is not found
     */
    public Mono<Void> deleteProduct(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found for ID: " + id)))
                .flatMap(existingProduct -> repository.deleteById(id))
                .then();
    }

}
