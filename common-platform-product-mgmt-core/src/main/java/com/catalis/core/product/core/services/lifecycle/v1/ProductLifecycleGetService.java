package com.catalis.core.product.core.services.lifecycle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.lifecycle.v1.ProductLifecycleMapper;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import com.catalis.core.product.models.entities.lifecycle.v1.ProductLifecycle;
import com.catalis.core.product.models.repositories.lifecycle.v1.ProductLifecycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    /**
     * Retrieves a paginated list of ProductLifecycleDTO instances associated with a specific product ID.
     * This method applies pagination and mapping logic to the query results.
     *
     * @param productId the ID of the product whose lifecycle records are to be retrieved
     * @param paginationRequest the pagination request containing page number and size
     * @return a Mono wrapping a PaginationResponse containing the mapped ProductLifecycleDTO objects and pagination details
     */
    public Mono<PaginationResponse<ProductLifecycleDTO>> findByProductId(Long productId,
                                                                         PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        );
    }

}