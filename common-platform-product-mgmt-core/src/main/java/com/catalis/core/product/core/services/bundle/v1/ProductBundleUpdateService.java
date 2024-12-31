package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.core.product.core.mappers.bundle.v1.ProductBundleMapper;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
import com.catalis.core.product.models.repositories.bundle.v1.ProductBundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductBundleUpdateService {

    @Autowired
    private ProductBundleRepository repository;

    @Autowired
    private ProductBundleMapper mapper;

    /**
     * Updates an existing ProductBundle identified by the given ID with the provided details.
     * The update operation modifies fields in the ProductBundle only if corresponding fields in the request are non-null.
     *
     * @param id      the ID of the ProductBundle to be updated
     * @param request the ProductBundleDTO containing updated details for the ProductBundle
     * @return a Mono emitting the updated ProductBundleDTO
     * @throws IllegalArgumentException if the ProductBundle with the given ID does not exist
     * @throws RuntimeException         if there is a failure during the update process
     */
    public Mono<ProductBundleDTO> update(Long id, ProductBundleDTO request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ProductBundle with id " + id + " not found")))
                .flatMap(existingEntity -> {
                    if (request.getBundleName() != null) {
                        existingEntity.setBundleName(request.getBundleName());
                    }
                    if (request.getBundleDescription() != null) {
                        existingEntity.setBundleDescription(request.getBundleDescription());
                    }
                    if (request.getBundleStatus() != null) {
                        existingEntity.setBundleStatus(request.getBundleStatus());
                    }
                    return repository.save(existingEntity);
                })
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to update ProductBundle", e)));
    }

}
