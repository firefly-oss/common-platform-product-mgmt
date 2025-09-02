package com.firefly.core.product.core.services.bundle.v1;

import com.firefly.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface ProductBundleItemService {

    /**
     * Retrieve a specific item within a product bundle by its unique item ID.
     */
    Mono<ProductBundleItemDTO> getItem(UUID bundleId, UUID itemId);

    /**
     * Create a new item in an existing product bundle.
     */
    Mono<ProductBundleItemDTO> createItem(UUID bundleId, ProductBundleItemDTO request);

    /**
     * Update an existing item within a product bundle.
     */
    Mono<ProductBundleItemDTO> updateItem(UUID bundleId, UUID itemId, ProductBundleItemDTO request);

    /**
     * Delete an existing item from a product bundle.
     */
    Mono<Void> deleteItem(UUID bundleId, UUID itemId);
}