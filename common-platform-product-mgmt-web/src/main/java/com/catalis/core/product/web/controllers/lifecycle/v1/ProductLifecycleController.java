package com.catalis.core.product.web.controllers.lifecycle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.lifecycle.v1.ProductLifecycleCreateService;
import com.catalis.core.product.core.services.lifecycle.v1.ProductLifecycleDeleteService;
import com.catalis.core.product.core.services.lifecycle.v1.ProductLifecycleGetService;
import com.catalis.core.product.core.services.lifecycle.v1.ProductLifecycleUpdateService;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
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
@RequestMapping("/api/v1/product-lifecycle")
@Tag(name = "Product Lifecycle Management API", description = "Operations for managing the Product Lifecycle")
public class ProductLifecycleController {

    @Autowired
    private ProductLifecycleGetService getService;

    @Autowired
    private ProductLifecycleCreateService createService;

    @Autowired
    private ProductLifecycleUpdateService updateService;

    @Autowired
    private ProductLifecycleDeleteService deleteService;

    /**
     * Retrieves a product lifecycle record by ID.
     *
     * @param productLifecycleId The Product Lifecycle ID.
     * @return A Mono containing the ResponseEntity with ProductLifecycleDTO.
     */
    @Operation(summary = "Retrieve a product lifecycle by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product lifecycle retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductLifecycleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product lifecycle not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{productLifecycleId}")
    public Mono<ResponseEntity<ProductLifecycleDTO>> getProductLifecycle(@PathVariable Long productLifecycleId) {
        return getService.getProductLifecycle(productLifecycleId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a paginated list of Product Lifecycle records for a specific Product ID.
     *
     * @param productId         The Product ID.
     * @param paginationRequest The pagination request details.
     * @return A Mono with the ResponseEntity of paginated ProductLifecycleDTOs.
     */
    @Operation(summary = "Retrieve a paginated list of product lifecycle records for a specific product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product lifecycle records retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/product/{productId}")
    public Mono<ResponseEntity<PaginationResponse<ProductLifecycleDTO>>> findByProductId(
            @PathVariable Long productId, PaginationRequest paginationRequest) {
        return getService.findByProductId(productId, paginationRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    /**
     * Creates a new Product Lifecycle record.
     *
     * @param request Request body containing the lifecycle details.
     * @return A Mono containing the ResponseEntity with created ProductLifecycleDTO.
     */
    @Operation(summary = "Create a new product lifecycle")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product lifecycle created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductLifecycleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public Mono<ResponseEntity<ProductLifecycleDTO>> createProductLifecycle(@RequestBody ProductLifecycleDTO request) {
        return createService.createProductLifecycle(request)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto));
    }

    /**
     * Updates an existing Product Lifecycle record by ID.
     *
     * @param id      The Product Lifecycle record ID.
     * @param request Request containing the updated details.
     * @return A Mono containing the ResponseEntity with updated ProductLifecycleDTO.
     */
    @Operation(summary = "Update an existing product lifecycle record by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product lifecycle updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductLifecycleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product lifecycle not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductLifecycleDTO>> updateProductLifecycle(@PathVariable Long id, @RequestBody ProductLifecycleDTO request) {
        return updateService.updateProductLifecycle(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a Product Lifecycle record by ID.
     *
     * @param id The Product Lifecycle record ID.
     * @return A Mono containing the ResponseEntity with the deletion result.
     */
    @Operation(summary = "Delete a product lifecycle record by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product lifecycle deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product lifecycle not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProductLifecycle(@PathVariable Long id) {
        return deleteService.deleteProductLifecycle(id)
                .<ResponseEntity<Void>> map(deleted -> ResponseEntity.noContent().build())
                .onErrorReturn(ResponseEntity.notFound().build());
    }
}