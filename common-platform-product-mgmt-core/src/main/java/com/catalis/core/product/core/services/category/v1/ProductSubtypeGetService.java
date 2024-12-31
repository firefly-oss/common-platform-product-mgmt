package com.catalis.core.product.core.services.category.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.category.v1.ProductSubtypeMapper;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductSubtypeDTO;
import com.catalis.core.product.models.repositories.category.v1.ProductSubtypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
public class ProductSubtypeGetService {

    @Autowired
    private ProductSubtypeRepository repository;

    @Autowired
    private ProductSubtypeMapper mapper;


    /**
     * Retrieves the ProductSubtypeDTO for the given subtype ID.
     * If the subtype ID is not found in the repository, an error is returned.
     * Additionally, any encountered errors during the processing are wrapped and propagated.
     *
     * @param id the unique identifier of the product subtype to be retrieved
     * @return a Mono emitting the ProductSubtypeDTO if found, or an error if not found
     */
    public Mono<ProductSubtypeDTO> getProductSubtype(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product subtype not found for ID: " + id)))
                .map(mapper::toDto)
                .onErrorMap(ex -> new RuntimeException("An error occurred while retrieving the product subtype.", ex));
    }

    public Mono<PaginationResponse<ProductSubtypeDTO>> getAllProductSubtypesByCategoryId(Long categoryId, PaginationRequest pagination) {
        return null;
        // TODO: Pending implementation
    }


}
