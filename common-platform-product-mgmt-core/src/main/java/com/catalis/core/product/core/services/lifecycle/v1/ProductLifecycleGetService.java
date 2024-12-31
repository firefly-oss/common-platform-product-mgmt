package com.catalis.core.product.core.services.lifecycle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.lifecycle.v1.ProductLifecycleMapper;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import com.catalis.core.product.models.repositories.lifecycle.v1.ProductLifecycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
public class ProductLifecycleGetService {

    @Autowired
    private ProductLifecycleRepository repository;

    @Autowired
    private ProductLifecycleMapper mapper;

    /**
     * Retrieves a product lifecycle record by its ID.
     * If the record is found, it is mapped to a ProductLifecycleDTO and returned.
     * If the record is not found, an error is emitted.
     *
     * @param productLifecycleId the ID of the product lifecycle to retrieve
     * @return a Mono containing the ProductLifecycleDTO if found, or an error if the record does not exist
     */
    public Mono<ProductLifecycleDTO> getProductLifecycle(Long productLifecycleId) {
        return repository.findById(productLifecycleId)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Lifecycle not found for ID: " + productLifecycleId)));
    }

    public Mono<PaginationResponse<ProductLifecycleDTO> findByProductId(Long productId, PaginationRequest paginatio){
        return null;
        //TODO: Pending implementation
    }


}
