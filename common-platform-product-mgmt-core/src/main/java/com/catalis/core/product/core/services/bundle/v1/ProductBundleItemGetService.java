package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
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
    private ProductBundleItemRepository productBundleItemRepository;

    @Autowired
    private ProductBundleItemMapper productBundleItemMapper;

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
        return productBundleItemRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ProductBundleItem with id " + id + " not found")))
                .map(entity -> {
                    ProductBundleItemDTO dto = productBundleItemMapper.toDto(entity);
                    dto.setProductBundleItemId(entity.getProductBundleItemId());
                    return dto;
                })
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve ProductBundleItem", e)));
    }


    /**
     * Retrieves paginated ProductBundleItemDTOs by Product ID.
     * Converts the pagination request into a Pageable object, fetches the paged results
     * and total count of items from the repository, maps entities to DTOs, and creates
     * a paginated response.
     *
     * @param productId         the ID of the product to retrieve bundle items for
     * @param paginationRequest the pagination settings including page number and size
     * @return a Mono emitting a paginated response containing a list of ProductBundleItemDTOs,
     * total count of items, total pages, and current page number
     */
    public Mono<PaginationResponse<ProductBundleItemDTO>> findByProductId(Long productId, PaginationRequest paginationRequest) {

        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductBundleItem entities from the repository
        Flux<ProductBundleItem> items = productBundleItemRepository.findByProductId(productId, pageable);

        // Fetch the total count of ProductBundleItem entities matching the product ID
        Mono<Long> count = productBundleItemRepository.countByProductId(productId);

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return items
                // Map each ProductBundleItem entity to a ProductBundleItemDTO using the mapper
                .map(productBundleItemMapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<ProductBundleItemDTO> itemDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            itemDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Set the current page number
                    );
                });
    }
    

}
