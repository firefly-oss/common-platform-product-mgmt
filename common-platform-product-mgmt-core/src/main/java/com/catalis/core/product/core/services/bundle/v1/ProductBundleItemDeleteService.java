package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.core.product.models.repositories.bundle.v1.ProductBundleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductBundleItemDeleteService {

    @Autowired
    private ProductBundleItemRepository repository;

    /**
     * Deletes a ProductBundleItem identified by the provided ID.
     * If the ProductBundleItem with the given ID does not exist, an error is emitted.
     * Any failure during the deletion process will result in an error being emitted.
     *
     * @param id the ID of the ProductBundleItem to be deleted
     * @return a Mono that completes if the deletion is successful or emits an error if the ProductBundleItem
     *         does not exist or if there is a failure during the deletion process
     * @throws IllegalArgumentException if the ProductBundleItem with the given ID does not exist
     * @throws RuntimeException if there is a failure during the deletion process
     */
    public Mono<Void> delete(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ProductBundleItem with id " + id + " not found")))
                .flatMap(existingItem -> repository.delete(existingItem))
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete ProductBundleItem", e)));
    }

}
