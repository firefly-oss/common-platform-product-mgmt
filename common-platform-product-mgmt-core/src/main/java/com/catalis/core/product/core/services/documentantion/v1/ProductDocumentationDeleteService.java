package com.catalis.core.product.core.services.documentantion.v1;

import com.catalis.core.product.models.repositories.documentation.v1.ProductDocumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductDocumentationDeleteService {

    @Autowired
    private ProductDocumentationRepository repository;

    /**
     * Deletes a specific product documentation record by its ID.
     *
     * @param productDocumentationId the ID of the product documentation to be deleted
     * @return a Mono that completes when the deletion operation is finished
     */
    public Mono<Void> deleteProductDocumentation(Long productDocumentationId) {
        return repository.findById(productDocumentationId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Documentation not found for ID: " + productDocumentationId)))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete Product Documentation", e)))
                .then();
    }

}