package com.catalis.core.product.core.services.lifecycle.v1;

import com.catalis.core.product.core.mappers.lifecycle.v1.ProductLifecycleMapper;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import com.catalis.core.product.models.repositories.lifecycle.v1.ProductLifecycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductLifecycleUpdateService {

    @Autowired
    private ProductLifecycleRepository repository;

    @Autowired
    private ProductLifecycleMapper mapper;

    /**
     * Updates an existing product lifecycle record with new values provided in the request DTO.
     * If the record is not found for the given ID, an error is emitted.
     *
     * @param id the ID of the product lifecycle to update
     * @param request the ProductLifecycleDTO containing the updated details of the product lifecycle
     * @return a Mono containing the updated ProductLifecycleDTO if the operation is successful,
     *         or an error if the record does not exist or another issue occurs
     */
    public Mono<ProductLifecycleDTO> updateProductLifecycle(Long id, ProductLifecycleDTO request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Lifecycle not found for ID: " + id)))
                .map(existingEntity -> {
                    existingEntity.setProductId(request.getProductId());
                    existingEntity.setLifecycleStatus(request.getLifecycleStatus());
                    existingEntity.setStatusStartDate(request.getStatusStartDate());
                    existingEntity.setStatusEndDate(request.getStatusEndDate());
                    existingEntity.setReason(request.getReason());
                    return existingEntity;
                })
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

}
