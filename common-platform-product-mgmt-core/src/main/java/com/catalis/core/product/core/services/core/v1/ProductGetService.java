package com.catalis.core.product.core.services.core.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
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
     * Retrieves a paginated list of products filtered by the specified product type.
     * Converts the retrieved Product entities to ProductDTOs and applies pagination based on the provided request.
     *
     * @param type the type of products to filter by
     * @param paginationRequest the pagination settings including page number and page size
     * @return a Mono emitting a PaginationResponse containing a list of ProductDTOs that match the specified product type
     */
    public Mono<PaginationResponse<ProductDTO>> getProductsByType(ProductTypeEnum type,
                                                                  PaginationRequest paginationRequest) {

        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductType(type, pageable),
                () -> repository.countByProductType(type)
        );

    }

    /**
     * Retrieves a paginated list of products whose names contain the specified string, ignoring case sensitivity.
     * Converts the retrieved Product entities to ProductDTOs and applies pagination based on the provided request.
     *
     * @param productName       the substring to search for within product names, case-insensitively
     * @param paginationRequest the pagination settings including page number and page size
     * @return a Mono emitting a PaginationResponse containing a list of ProductDTOs that match the search criteria
     */
    public Mono<PaginationResponse<ProductDTO>> findByProductNameContainingIgnoreCase(
            String productName, PaginationRequest paginationRequest) {

        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductNameContainingIgnoreCase(productName, pageable),
                () -> repository.countByProductNameContainingIgnoreCase(productName)
        );

    }
}
