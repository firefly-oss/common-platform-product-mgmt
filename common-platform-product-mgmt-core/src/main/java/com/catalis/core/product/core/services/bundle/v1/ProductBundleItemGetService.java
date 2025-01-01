package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.bundle.v1.ProductBundleItemMapper;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import com.catalis.core.product.models.entities.bundle.v1.ProductBundleItem;
import com.catalis.core.product.models.repositories.bundle.v1.ProductBundleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductBundleItemGetService {


    @Autowired
    private ProductBundleItemRepository repository;

    @Autowired
    private ProductBundleItemMapper mapper;

    /**
     * Retrieves a ProductBundleItem by its ID and maps it to a ProductBundleItemDTO.
     * If no ProductBundleItem is found with the specified ID, an error is emitted.
     * Any failures during retrieval will result in an error being emitted.
     *
     * @param id the ID of the ProductBundleItem to be retrieved
     * @return a Mono emitting the retrieved ProductBundleItemDTO, or an error if the ProductBundleItem is not found
     *         or if there is a failure during the retrieval process
     * @throws IllegalArgumentException if the ProductBundleItem with the given ID does not exist
     * @throws RuntimeException if there is a failure during the retrieval process
     */
    public Mono<ProductBundleItemDTO> get(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ProductBundleItem with id " + id + " not found")))
                .map(entity -> {
                    ProductBundleItemDTO dto = mapper.toDto(entity);
                    dto.setProductBundleItemId(entity.getProductBundleItemId());
                    return dto;
                })
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve ProductBundleItem", e)));
    }

    /**
     * Retrieves all product categories associated with a specific product ID with pagination support.
     * This method utilizes the pagination request to fetch a pageable list of product categories
     * and their count for the provided product ID.
     *
     * @param productId the ID of the product to retrieve its associated categories
     * @param paginationRequest an object containing pagination parameters such as page number and size
     * @return a Mono emitting a PaginationResponse containing the paginated list of ProductBundleItemDTOs
     *         and additional pagination metadata
     */
    public Mono<PaginationResponse<ProductBundleItemDTO>> getAllProductCategories(Long productId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        );
    }

    

}
