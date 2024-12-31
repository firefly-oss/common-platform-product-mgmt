package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.core.product.core.mappers.bundle.v1.ProductBundleItemMapper;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import com.catalis.core.product.models.repositories.bundle.v1.ProductBundleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductBundleItemUpdateService {

    @Autowired
    private ProductBundleItemRepository productBundleItemRepository;

    @Autowired
    private ProductBundleItemMapper productBundleItemMapper;

    /**
     * Updates an existing ProductBundleItem with the given ID using the data from the provided ProductBundleItemDTO.
     *
     * This method retrieves the existing ProductBundleItem by its ID, updates its fields with the new values,
     * and saves the updated entity back to the repository. If the entity with the specified ID does not exist,
     * an {@link IllegalArgumentException} is emitted. Any failure during the operation will result in a {@link RuntimeException}.
     *
     * @param id the ID of the ProductBundleItem to be updated
     * @param request the ProductBundleItemDTO containing the updated data for the ProductBundleItem
     * @return a Mono emitting the updated ProductBundleItemDTO, or an error if the ProductBundleItem is not found
     *         or if there is a failure during the updating process
     * @throws IllegalArgumentException if the ProductBundleItem with the given ID does not exist
     * @throws RuntimeException if there is a failure during the update process
     */
    public Mono<ProductBundleItemDTO> update(Long id, ProductBundleItemDTO request) {
        return productBundleItemRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ProductBundleItem with id " + id + " not found")))
                .flatMap(existingItem -> {
                    // Update fields from DTO to entity
                    existingItem.setProductBundleId(request.getProductBundleId());
                    existingItem.setProductId(request.getProductId());
                    existingItem.setSpecialConditions(request.getSpecialConditions());
                    // Save the updated entity
                    return productBundleItemRepository.save(existingItem);
                })
                .map(productBundleItemMapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to update ProductBundleItem", e)));
    }

}
