package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.core.product.core.mappers.bundle.v1.ProductBundleMapper;
import com.catalis.core.product.models.repositories.bundle.v1.ProductBundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductBundleDeleteService {

    @Autowired
    private ProductBundleRepository repository;

    @Autowired
    private ProductBundleMapper mapper;

    /**
     * Deletes a ProductBundle identified by the provided ID.
     * If the ProductBundle with the given ID does not exist, an error is thrown.
     * Any failure during the deletion process will result in an error being emitted.
     *
     * @param id the ID of the ProductBundle to be deleted
     * @return a Mono that completes if the deletion is successful or emits an error if the ProductBundle
     *         does not exist or if there is a failure during the deletion process
     * @throws IllegalArgumentException if the ProductBundle with the given ID does not exist
     * @throws RuntimeException if there is a failure during the deletion process
     */
    public Mono<Void> delete(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ProductBundle with id " + id + " not found")))
                .flatMap(existingBundle -> repository.delete(existingBundle))
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete ProductBundle", e)));
    }

}
