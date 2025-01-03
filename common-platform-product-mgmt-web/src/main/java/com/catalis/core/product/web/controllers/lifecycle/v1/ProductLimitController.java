package com.catalis.core.product.web.controllers.lifecycle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.lifecycle.v1.ProductLimitCreateService;
import com.catalis.core.product.core.services.lifecycle.v1.ProductLimitDeleteService;
import com.catalis.core.product.core.services.lifecycle.v1.ProductLimitGetService;
import com.catalis.core.product.core.services.lifecycle.v1.ProductLimitUpdateService;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLimitDTO;
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
@RequestMapping("/api/v1/product-limits")
@Tag(name = "Product Lifecycle Management API", description = "Operations for managing the Product Lifecycle")
public class ProductLimitController {

    @Autowired
    private ProductLimitGetService getService;

    @Autowired
    private ProductLimitCreateService createService;

    @Autowired
    private ProductLimitUpdateService updateService;

    @Autowired
    private ProductLimitDeleteService deleteService;

    /**
     * Retrieves a product limit by its ID.
     *
     * @param productLimitId The unique ID of the product limit.
     * @return A Mono containing the ProductLimitDTO if the record exists.
     */
    @Operation(summary = "Retrieve a product limit by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product Limit retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductLimitDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product Limit not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{productLimitId}")
    public Mono<ResponseEntity<ProductLimitDTO>> getProductLimit(@PathVariable Long productLimitId) {
        return getService.getProductLimit(productLimitId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a paginated list of product limits for a specific product ID.
     *
     * @param productId         The unique ID of the product.
     * @param paginationRequest Pagination details such as page size and page number.
     * @return A Mono containing a paginated response of ProductLimitDTOs.
     */
    @Operation(summary = "Retrieve paginated product limits by product ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated Product Limits retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/product/{productId}")
    public Mono<ResponseEntity<PaginationResponse<ProductLimitDTO>>> findByProductId(
            @PathVariable Long productId, PaginationRequest paginationRequest) {
        return getService.findByProductId(productId, paginationRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    /**
     * Creates a new product limit.
     *
     * @param request The ProductLimitDTO containing details of the new record.
     * @return A Mono containing the created ProductLimitDTO.
     */
    @Operation(summary = "Create a new product limit")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product Limit created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductLimitDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public Mono<ResponseEntity<ProductLimitDTO>> createProductLimit(@RequestBody ProductLimitDTO request) {
        return createService.createProductLimit(request)
                .map(productLimit -> ResponseEntity.status(HttpStatus.CREATED).body(productLimit));
    }

    /**
     * Updates an existing product limit by ID.
     *
     * @param id      The unique ID of the product limit to update.
     * @param request The ProductLimitDTO containing updated details.
     * @return A Mono containing the updated ProductLimitDTO.
     */
    @Operation(summary = "Update a product limit by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product Limit updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductLimitDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product Limit not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductLimitDTO>> updateProductLimit(@PathVariable Long id, @RequestBody ProductLimitDTO request) {
        return updateService.updateProductLimit(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a product limit by its ID.
     *
     * @param id The unique ID of the product limit to delete.
     * @return A Mono that completes upon successful deletion.
     */
    @Operation(summary = "Delete a product limit by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product Limit deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product Limit not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProductLimit(@PathVariable Long id) {
        return deleteService.deleteProductLimit(id)
                .<ResponseEntity<Void>>map(deleted -> ResponseEntity.noContent().build())
                .onErrorReturn(ResponseEntity.notFound().build());
    }
}