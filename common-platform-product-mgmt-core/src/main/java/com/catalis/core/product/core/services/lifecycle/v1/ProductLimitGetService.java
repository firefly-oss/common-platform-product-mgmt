package com.catalis.core.product.core.services.lifecycle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.lifecycle.v1.ProductLimitMapper;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLimitDTO;
import com.catalis.core.product.models.entities.lifecycle.v1.ProductLimit;
import com.catalis.core.product.models.repositories.lifecycle.v1.ProductLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    /**
     * Retrieves a paginated list of product limit records associated with the given product ID.
     * The method uses the provided pagination parameters to query the repository, map the
     * results to DTOs, and return a Mono containing the paginated response.
     *
     * @param productId the ID of the product whose limits are to be retrieved
     * @param paginationRequest the pagination parameters for the query, including page number, size, and sorting
     * @return a Mono containing a PaginationResponse with the paginated list of ProductLimitDTOs
     */
    public Mono<PaginationResponse<ProductLimitDTO>> findByProductId(Long productId,
                                                                     PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        );

    }

}