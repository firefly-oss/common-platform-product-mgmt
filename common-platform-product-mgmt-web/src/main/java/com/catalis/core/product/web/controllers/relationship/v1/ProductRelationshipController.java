package com.catalis.core.product.web.controllers.relationship.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.relationship.v1.ProductRelationshipCreateService;
import com.catalis.core.product.core.services.relationship.v1.ProductRelationshipDeleteService;
import com.catalis.core.product.core.services.relationship.v1.ProductRelationshipGetService;
import com.catalis.core.product.core.services.relationship.v1.ProductRelationshipUpdateService;
import com.catalis.core.product.interfaces.dtos.relationship.v1.ProductRelationshipDTO;
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
@RequestMapping("/api/v1/product-relationships")
@Tag(name = "Product Relationship Management API", description = "Manage relationships between products")
public class ProductRelationshipController {

    @Autowired
    private ProductRelationshipGetService getService;

    @Autowired
    private ProductRelationshipCreateService createService;

    @Autowired
    private ProductRelationshipUpdateService updateService;

    @Autowired
    private ProductRelationshipDeleteService deleteService;

    /**
     * Retrieves a product relationship by its ID.
     *
     * @param id The ID of the product relationship to retrieve.
     * @return A {@code Mono<ResponseEntity<ProductRelationshipDTO>>} containing the product relationship details.
     */
    @Operation(summary = "Get product relationship by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product relationship retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductRelationshipDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product relationship not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductRelationshipDTO>> getProductRelationship(@PathVariable Long id) {
        return getService.getProductRelationship(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a paginated list of product relationships for a given product ID.
     *
     * @param productId         The ID of the product whose relationships to fetch.
     * @param paginationRequest The pagination request containing size and page details.
     * @return A {@code Mono<ResponseEntity<PaginationResponse<ProductRelationshipDTO>>>} containing paginated product relationships.
     */
    @Operation(summary = "Get paginated product relationships by product ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product relationships retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/product/{productId}")
    public Mono<ResponseEntity<PaginationResponse<ProductRelationshipDTO>>> findByProductId(
            @PathVariable Long productId, PaginationRequest paginationRequest) {
        return getService.findByProductId(productId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * Creates a new product relationship.
     *
     * @param request The {@code ProductRelationshipDTO} containing the details of the relationship to create.
     * @return A {@code Mono<ResponseEntity<ProductRelationshipDTO>>} representing the created product relationship.
     */
    @Operation(summary = "Create a new product relationship")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product relationship created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductRelationshipDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public Mono<ResponseEntity<ProductRelationshipDTO>> createProductRelationship(@RequestBody ProductRelationshipDTO request) {
        return createService.createProductRelationship(request)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    /**
     * Updates an existing product relationship by its ID.
     *
     * @param id      The ID of the product relationship to update.
     * @param request The {@code ProductRelationshipDTO} with the updated details.
     * @return A {@code Mono<ResponseEntity<ProductRelationshipDTO>>} containing the updated product relationship.
     */
    @Operation(summary = "Update a product relationship")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product relationship updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductRelationshipDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product relationship not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductRelationshipDTO>> updateProductRelationship(
            @PathVariable Long id, @RequestBody ProductRelationshipDTO request) {
        return updateService.updateProductRelationship(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a product relationship by its ID.
     *
     * @param id The ID of the relationship to delete.
     * @return A {@code Mono<ResponseEntity<Void>>} signaling the successful deletion or an error.
     */
    @Operation(summary = "Delete a product relationship by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product relationship deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product relationship not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProductRelationship(@PathVariable Long id) {
        return deleteService.deleteProductRelationship(id)
                .<ResponseEntity<Void>>map(deleted -> ResponseEntity.noContent().build())
                .onErrorReturn(ResponseEntity.notFound().build());
    }
}