package com.catalis.core.product.core.services.category.v1;

import com.catalis.core.product.models.repositories.category.v1.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductCategoryDeleteService {

    @Autowired
    private ProductCategoryRepository repository;

    /**
     * Deletes a product category identified by its ID.
     * If the product category is not found, an exception is returned.
     *
     * @param id the unique identifier of the product category to be deleted
     * @return a Mono that completes when the product category is successfully deleted,
     * or emits an error if the category does not exist
     */
    public Mono<Void> deleteProductCategory(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product category not found for ID: " + id)))
                .flatMap(existingCategory -> repository.deleteById(id))
                .then();
    }
    
}
