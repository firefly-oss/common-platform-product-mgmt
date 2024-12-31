package com.catalis.core.product.core.services.core.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.core.v1.ProductMapper;
import com.catalis.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.catalis.core.product.interfaces.enums.core.v1.ProductTypeEnum;
import com.catalis.core.product.models.repositories.product.v1.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
public class ProductGetService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    /**
     * Retrieves a product by its ID.
     * If the product does not exist, it returns an error.
     * Maps the retrieved product entity to a ProductDTO.
     * Handles errors that may occur during retrieval.
     *
     * @param id the ID of the product to retrieve
     * @return a Mono emitting the ProductDTO if found, or an error otherwise
     */
    public Mono<ProductDTO> getProduct(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found for ID: " + id)))
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new IllegalArgumentException("An error occurred while retrieving the product: " + e.getMessage(), e)));
    }

    public Mono<PaginationResponse<ProductDTO>> getProductsByType(ProductTypeEnum type, PaginationRequest pagination) {
        return null;
        // TODO: Pending this implementation
    }

    public Mono<PaginationResponse<ProductDTO>> findByProductNameContainingIgnoreCase(String productName, PaginationRequest pagination) {
        return null;
        // TODO: Pending this implementation
    }


}
