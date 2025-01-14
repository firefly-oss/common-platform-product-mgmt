package com.catalis.core.product.web.controllers.bundle.v1;

import com.catalis.core.product.core.services.bundle.v1.ProductBundleItemServiceImpl;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Product Bundle", description = "APIs for managing product bundles in the product management platform")
@RestController
@RequestMapping("/api/v1/bundles/{bundleId}/items")
public class ProductBundleItemController {

    @Autowired
    private ProductBundleItemServiceImpl service;


    @Operation(
            summary = "Get Product Bundle Item by ID",
            description = "Retrieve a specific item within a product bundle using its unique item identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product bundle item",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductBundleItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item not found in the specified product bundle",
                    content = @Content)
    })
    @GetMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductBundleItemDTO>> getProductBundleItemById(
            @Parameter(description = "Unique identifier of the product bundle", required = true)
            @PathVariable Long bundleId,

            @Parameter(description = "Unique identifier of the item within the product bundle", required = true)
            @PathVariable Long itemId
    ) {
        return service.getItem(bundleId, itemId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(
            summary = "Create Product Bundle Item",
            description = "Add a new item to an existing product bundle."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product bundle item created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductBundleItemDTO.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductBundleItemDTO>> createProductBundleItem(
            @Parameter(description = "Unique identifier of the product bundle to add an item to", required = true)
            @PathVariable Long bundleId,

            @Parameter(description = "Data for the new item within the product bundle", required = true,
                    schema = @Schema(implementation = ProductBundleItemDTO.class))
            @RequestBody ProductBundleItemDTO request
    ) {
        return service.createItem(bundleId, request)
                .map(ResponseEntity::ok);
    }

    @Operation(
            summary = "Update Product Bundle Item",
            description = "Update an existing item within a product bundle."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product bundle item updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductBundleItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item not found in the specified product bundle",
                    content = @Content)
    })
    @PutMapping(value = "/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductBundleItemDTO>> updateProductBundleItem(
            @Parameter(description = "Unique identifier of the product bundle", required = true)
            @PathVariable Long bundleId,

            @Parameter(description = "Unique identifier of the item to be updated", required = true)
            @PathVariable Long itemId,

            @Parameter(description = "Updated data for the item within the product bundle", required = true,
                    schema = @Schema(implementation = ProductBundleItemDTO.class))
            @RequestBody ProductBundleItemDTO request
    ) {
        return service.updateItem(bundleId, itemId, request)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(
            summary = "Delete Product Bundle Item",
            description = "Remove an existing item from a product bundle."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product bundle item deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found in the specified product bundle",
                    content = @Content)
    })
    @DeleteMapping(value = "/{itemId}")
    public Mono<ResponseEntity<Void>> deleteProductBundleItem(
            @Parameter(description = "Unique identifier of the product bundle", required = true)
            @PathVariable Long bundleId,

            @Parameter(description = "Unique identifier of the item to be deleted", required = true)
            @PathVariable Long itemId
    ) {
        return service.deleteItem(bundleId, itemId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}