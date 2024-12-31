package com.catalis.core.product.core.services.lifecycle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.lifecycle.v1.ProductLimitMapper;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLimitDTO;
import com.catalis.core.product.models.repositories.lifecycle.v1.ProductLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
public class ProductLimitGetService {

    @Autowired
    private ProductLimitRepository repository;

    @Autowired
    private ProductLimitMapper mapper;

    /**
     * Retrieves a product limit record by its ID.
     * If the record is found, it is mapped to a ProductLimitDTO and returned.
     * If the record is not found, an error is emitted.
     *
     * @param productLimitId the ID of the product limit to retrieve
     * @return a Mono containing the ProductLimitDTO if found, or an error if the record does not exist
     */
    public Mono<ProductLimitDTO> getProductLimit(Long productLimitId) {
        return repository.findById(productLimitId)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Limit not found for ID: " + productLimitId)));
    }

    public Mono<PaginationResponse<ProductLimitDTO>> findByProductId(Long productId, PaginationRequest pagination){
        return null;
        //TODO: Pending implementation
    }

}