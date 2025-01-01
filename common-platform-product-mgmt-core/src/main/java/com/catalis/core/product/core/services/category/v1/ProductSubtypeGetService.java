package com.catalis.core.product.core.services.category.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.category.v1.ProductSubtypeMapper;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductSubtypeDTO;
import com.catalis.core.product.models.entities.category.v1.ProductSubtype;
import com.catalis.core.product.models.repositories.category.v1.ProductSubtypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    /**
     * Retrieves a paginated list of ProductSubtypeDTOs belonging to a specific product category.
     * The results are fetched based on the provided category ID and pagination request details.
     *
     * @param categoryId the unique identifier of the product category to filter subtypes by
     * @param paginationRequest the pagination request containing page size, page number, and sorting details
     * @return a Mono emitting a PaginationResponse containing the list of ProductSubtypeDTOs,
     *         along with total elements and pagination details
     */
    public Mono<PaginationResponse<ProductSubtypeDTO>> getAllProductSubtypesByCategoryId(
            Long categoryId, PaginationRequest paginationRequest) {

        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductCategoryId(categoryId, pageable),
                () -> repository.countByProductCategoryId(categoryId)
        );

    }

}