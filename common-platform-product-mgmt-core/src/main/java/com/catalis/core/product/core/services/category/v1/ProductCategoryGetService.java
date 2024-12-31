package com.catalis.core.product.core.services.category.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.core.product.core.mappers.category.v1.ProductCategoryMapper;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
import com.catalis.core.product.models.repositories.category.v1.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
     * Retrieves all product categories with pagination support.
     *
     * @param paginationRequest the request object containing pagination details
     * @return a Flux of ProductCategoryDTO, representing the paginated list of product categories;
     *         emits an error if no product categories are found or if any issue occurs during fetching
     */
    public Flux<ProductCategoryDTO> getAllProductCategories(PaginationRequest paginationRequest) {
        return repository.findAllBy(paginationRequest.toPageable())
                .switchIfEmpty(Flux.error(new IllegalArgumentException("No product categories found.")))
                .map(mapper::toDto)
                .onErrorMap(throwable -> new RuntimeException("An error occurred while fetching product categories.", throwable));
    }

    /**
     * Searches for product categories with names containing the specified substring, ignoring case.
     * The results are paginated based on the provided pagination request.
     *
     * @param categoryName the substring to search for within product category names 
     *                     (case-insensitive match)
     * @param paginationRequest the pagination parameters (e.g., page number, page size)
     * @return a Flux emitting ProductCategoryDTO objects matching the search criteria, 
     *         or an error if no results are found or an exception occurs during processing
     */
    public Flux<ProductCategoryDTO> findByCategoryNameContainingIgnoreCase(String categoryName, PaginationRequest paginationRequest) {
        return repository.findByCategoryNameContainingIgnoreCase(categoryName, paginationRequest.toPageable())
                .switchIfEmpty(Flux.error(new IllegalArgumentException("No product categories found containing: " + categoryName)))
                .map(mapper::toDto)
                .onErrorMap(throwable -> new RuntimeException("An error occurred while searching for product categories.", throwable));
    }

    /**
     * Retrieves all product categories that have the specified parent category ID,
     * with support for pagination.
     *
     * @param parentCategoryId the ID of the parent category for which to find child categories
     * @param paginationRequest the request object containing pagination details such as page size and page number
     * @return a Flux emitting ProductCategoryDTO objects representing the child categories;
     *         emits an error if no categories are found or if an exception occurs during processing
     */
    public Flux<ProductCategoryDTO> findAllByParentCategoryId(Long parentCategoryId, PaginationRequest paginationRequest) {
        return repository.findByParentCategoryId(parentCategoryId, paginationRequest.toPageable())
                .switchIfEmpty(Flux.error(new IllegalArgumentException("No product categories found for parent category ID: " + parentCategoryId)))
                .map(mapper::toDto)
                .onErrorMap(throwable -> new RuntimeException("An error occurred while fetching product categories by parent category ID.", throwable));
    }

}
