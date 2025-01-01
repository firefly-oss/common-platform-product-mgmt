package com.catalis.core.product.core.services.category.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.category.v1.ProductCategoryMapper;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
import com.catalis.core.product.models.entities.category.v1.ProductCategory;
import com.catalis.core.product.models.repositories.category.v1.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductCategoryGetService {

    @Autowired
    private ProductCategoryRepository repository;

    @Autowired
    private ProductCategoryMapper mapper;

    /**
     * Retrieves the ProductCategoryDTO for the given category ID.
     * If the category ID is not found, an error is returned.
     *
     * @param id the unique identifier of the product category to be retrieved
     * @return a Mono emitting the ProductCategoryDTO if found, or an error if not found
     */
    public Mono<ProductCategoryDTO> getProductCategory(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product category not found for ID: " + id)))
                .map(mapper::toDto);
    }

    /**
     * Retrieves a paginated list of all ProductCategoryDTOs.
     * Utilizes the provided pagination request for page size and page number.
     *
     * @param paginationRequest the pagination request containing page size and page number
     * @return a Mono emitting a PaginationResponse containing the list of ProductCategoryDTOs
     *         along with total elements and pagination details
     */
    public Mono<PaginationResponse<ProductCategoryDTO>> getAllProductCategories(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                repository::findAllBy,
                repository::count
        );
    }


}
