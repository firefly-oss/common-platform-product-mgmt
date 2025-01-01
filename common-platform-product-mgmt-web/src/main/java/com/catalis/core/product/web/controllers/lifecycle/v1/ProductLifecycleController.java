package com.catalis.core.product.web.controllers.lifecycle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.lifecycle.v1.*;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * @return A Mono containing the ProductLifecycleDTO.
     */
    @Operation(summary = "Retrieve a product lifecycle by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product lifecycle retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductLifecycleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product lifecycle not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{productLifecycleId}")
    public Mono<ProductLifecycleDTO> getProductLifecycle(@PathVariable Long productLifecycleId) {
        return getService.getProductLifecycle(productLifecycleId);
    }

    /**
     * Retrieves a paginated list of Product Lifecycle records for a specific Product ID.
     *
     * @param productId The Product ID.
     * @param paginationRequest The pagination request details.
     * @return A Mono with the paginated ProductLifecycleDTOs.
     */
    @Operation(summary = "Retrieve a paginated list of product lifecycle records for a specific product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product lifecycle records retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/product/{productId}")
    public Mono<PaginationResponse<ProductLifecycleDTO>> findByProductId(
            @PathVariable Long productId, PaginationRequest paginationRequest) {
        return getService.findByProductId(productId, paginationRequest);
    }

    /**
     * Creates a new Product Lifecycle record.
     *
     * @param request Request body containing the lifecycle details.
     * @return A Mono containing the created ProductLifecycleDTO.
     */
    @Operation(summary = "Create a new product lifecycle")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product lifecycle created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductLifecycleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductLifecycleDTO> createProductLifecycle(@RequestBody ProductLifecycleDTO request) {
        return createService.createProductLifecycle(request);
    }

    /**
     * Updates an existing Product Lifecycle record by ID.
     *
     * @param id The Product Lifecycle record ID.
     * @param request Request containing the updated details.
     * @return A Mono containing the updated ProductLifecycleDTO.
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
    public Mono<ProductLifecycleDTO> updateProductLifecycle(@PathVariable Long id, @RequestBody ProductLifecycleDTO request) {
        return updateService.updateProductLifecycle(id, request);
    }

    /**
     * Deletes a Product Lifecycle record by ID.
     *
     * @param id The Product Lifecycle record ID.
     * @return A Mono indicating the deletion result.
     */
    @Operation(summary = "Delete a product lifecycle record by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product lifecycle deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product lifecycle not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductLifecycle(@PathVariable Long id) {
        return deleteService.deleteProductLifecycle(id);
    }
}