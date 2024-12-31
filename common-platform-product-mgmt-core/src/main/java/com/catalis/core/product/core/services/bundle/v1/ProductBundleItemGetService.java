package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.bundle.v1.ProductBundleItemMapper;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import com.catalis.core.product.models.repositories.bundle.v1.ProductBundleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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


    public Mono<PaginationResponse<ProductBundleItemDTO>> findByProductId(Long productId, PaginationRequest paginationRequest) {
        return null;
        // TODO: Implementation pending
    }
    

}
