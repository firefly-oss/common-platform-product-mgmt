package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import reactor.core.publisher.Mono;

public interface ProductBundleItemService {

    /**
     * Retrieve a specific item within a product bundle by its unique item ID.
     */
    Mono<ProductBundleItemDTO> getItem(Long bundleId, Long itemId);

    /**
     * Create a new item in an existing product bundle.
     */
    Mono<ProductBundleItemDTO> createItem(Long bundleId, ProductBundleItemDTO request);

    /**
     * Update an existing item within a product bundle.
     */
    Mono<ProductBundleItemDTO> updateItem(Long bundleId, Long itemId, ProductBundleItemDTO request);

    /**
     * Delete an existing item from a product bundle.
     */
    Mono<Void> deleteItem(Long bundleId, Long itemId);
}