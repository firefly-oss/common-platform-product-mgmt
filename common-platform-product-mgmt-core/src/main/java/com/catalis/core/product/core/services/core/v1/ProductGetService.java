package com.catalis.core.product.core.services.core.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.core.v1.ProductMapper;
import com.catalis.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.catalis.core.product.interfaces.enums.core.v1.ProductTypeEnum;
import com.catalis.core.product.models.entities.core.v1.Product;
import com.catalis.core.product.models.repositories.product.v1.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found for ID: " + id)))
                .onErrorMap(e -> new IllegalArgumentException("An error occurred while retrieving the product: " + e.getMessage(), e));
    }

    /**
     * Retrieves a paginated list of products filtered by their type.
     *
     * @param type              the type of the products to retrieve
     * @param paginationRequest the pagination settings including page number and page size
     * @return a Mono emitting a PaginationResponse containing a list of ProductDTOs
     */
    public Mono<PaginationResponse<ProductDTO>> getProductsByType(ProductTypeEnum type,
                                                                  PaginationRequest paginationRequest) {
        return getPaginatedProducts(
                repository.findByProductType(type, paginationRequest.toPageable()),
                paginationRequest
        );
    }

    /**
     * Retrieves a paginated list of products by name containing the given value (case-insensitive).
     *
     * @param productName       the value to search for in product names
     * @param paginationRequest the pagination settings including page number and page size
     * @return a Mono emitting a PaginationResponse containing a list of ProductDTOs
     */
    public Mono<PaginationResponse<ProductDTO>> findByProductNameContainingIgnoreCase(
            String productName, PaginationRequest paginationRequest) {
        return getPaginatedProducts(
                repository.findByProductNameContainingIgnoreCase(productName, paginationRequest.toPageable()),
                paginationRequest
        );
    }

    /**
     * Helper method to handle the pagination logic and response transformation.
     *
     * @param products          Flux of Product entities
     * @param paginationRequest Pagination settings
     * @return a Mono emitting a PaginationResponse containing a list of ProductDTOs
     */
    private Mono<PaginationResponse<ProductDTO>> getPaginatedProducts(Flux<Product> products,
                                                                      PaginationRequest paginationRequest) {
        Pageable pageable = paginationRequest.toPageable();
        Mono<Long> count = repository.count();

        // Process the products and map them to PaginationResponse
        return products.map(mapper::toDto)
                .collectList()
                .zipWith(count)
                .map(tuple -> {
                    List<ProductDTO> productDTOS = tuple.getT1();
                    long total = tuple.getT2();
                    return new PaginationResponse<>(
                            productDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()),
                            pageable.getPageNumber()
                    );
                });
    }
}
