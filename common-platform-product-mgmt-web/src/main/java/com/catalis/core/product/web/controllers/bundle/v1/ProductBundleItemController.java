package com.catalis.core.product.web.controllers.bundle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleItemCreateService;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleItemDeleteService;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleItemGetService;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleItemUpdateService;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/product-bundle-items")
@Tag(name = "Product Bundle Management API", description = "Operations for managing product bundles")
public class ProductBundleItemController {

    @Autowired
    private ProductBundleItemCreateService createService;

    @Autowired
    private ProductBundleItemGetService getService;

    @Autowired
    private ProductBundleItemUpdateService updateService;

    @Autowired
    private ProductBundleItemDeleteService deleteService;

    @Operation(summary = "Retrieve all product bundle items for a given product ID with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product bundle items",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{productId}")
    public Mono<ResponseEntity<PaginationResponse<ProductBundleItemDTO>>> getAllBundleItemsByProductId(
            @PathVariable Long productId, PaginationRequest paginationRequest) {
        return getService.getAllProductCategories(productId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Retrieve a specific product bundle item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product bundle item",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductBundleItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product bundle item not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/item/{id}")
    public Mono<ResponseEntity<ProductBundleItemDTO>> getBundleItemById(@PathVariable Long id) {
        return getService.get(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new product bundle item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the product bundle item",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductBundleItemDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ProductBundleItemDTO data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    public Mono<ResponseEntity<ProductBundleItemDTO>> createBundleItem(@RequestBody ProductBundleItemDTO request) {
        return createService.create(request)
                .map(productBundleItemDTO -> ResponseEntity.status(HttpStatus.CREATED).body(productBundleItemDTO));
    }

    @Operation(summary = "Update an existing product bundle item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product bundle item",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductBundleItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product bundle item not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ProductBundleItemDTO data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductBundleItemDTO>> updateBundleItem(@PathVariable Long id, @RequestBody ProductBundleItemDTO request) {
        return updateService.update(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a specific product bundle item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product bundle item", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product bundle item not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBundleItem(@PathVariable Long id) {
        return deleteService.delete(id).<ResponseEntity<Void>> map(deleted -> ResponseEntity.noContent().build())
                .onErrorReturn(ResponseEntity.notFound().build());
    }
}