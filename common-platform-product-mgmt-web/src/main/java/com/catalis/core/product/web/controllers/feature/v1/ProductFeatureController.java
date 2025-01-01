package com.catalis.core.product.web.controllers.feature.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.feature.v1.*;
        import com.catalis.core.product.interfaces.dtos.feature.v1.ProductFeatureDTO;
import com.catalis.core.product.interfaces.enums.feature.v1.FeatureTypeEnum;
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
@RequestMapping("/api/v1/product-features")
@Tag(name = "Product Feature Management API", description = "Operations for managing product features")
public class ProductFeatureController {

    @Autowired
    private ProductFeatureCreateService createService;

    @Autowired
    private ProductFeatureGetService getService;

    @Autowired
    private ProductFeatureUpdateService updateService;

    @Autowired
    private ProductFeatureDeleteService deleteService;

    /**
     * Retrieve a product feature by its ID.
     *
     * @param productFeatureId The ID of the product feature to retrieve.
     * @return A {@code Mono<ProductFeatureDTO>} containing the requested product feature.
     */
    @Operation(summary = "Retrieve a product feature by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product feature",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductFeatureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product feature not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{productFeatureId}")
    public Mono<ProductFeatureDTO> getProductFeature(@PathVariable Long productFeatureId) {
        return getService.getProductFeature(productFeatureId);
    }

    /**
     * Retrieve a paginated list of product features filtered by feature type.
     *
     * @param featureType       The feature type to filter by.
     * @param paginationRequest Object containing pagination settings.
     * @return A {@code Mono<PaginationResponse<ProductFeatureDTO>>} containing the paginated list of features.
     */
    @Operation(summary = "Retrieve product features by feature type with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product features",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/feature-type/{featureType}")
    public Mono<PaginationResponse<ProductFeatureDTO>> getProductFeaturesByFeatureType(
            @PathVariable FeatureTypeEnum featureType, PaginationRequest paginationRequest) {
        return getService.findByFeatureType(featureType, paginationRequest);
    }

    /**
     * Creates a new product feature.
     *
     * @param request The {@code ProductFeatureDTO} containing the feature details.
     * @return A {@code Mono<ProductFeatureDTO>} containing the created product feature.
     */
    @Operation(summary = "Create a new product feature")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the product feature",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductFeatureDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductFeatureDTO> createProductFeature(@RequestBody ProductFeatureDTO request) {
        return createService.createProductFeature(request);
    }

    /**
     * Updates an existing product feature by its ID.
     *
     * @param id      The ID of the product feature to update.
     * @param request The {@code ProductFeatureDTO} containing the updated feature details.
     * @return A {@code Mono<ProductFeatureDTO>} containing the updated product feature.
     */
    @Operation(summary = "Update an existing product feature by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product feature",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductFeatureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product feature not found for the given ID", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ProductFeatureDTO> updateProductFeature(@PathVariable Long id, @RequestBody ProductFeatureDTO request) {
        return updateService.updateProductFeature(id, request);
    }

    /**
     * Deletes a product feature by its ID.
     *
     * @param id The ID of the product feature to delete.
     * @return A {@code Mono<Void>} indicating whether the operation completed successfully.
     */
    @Operation(summary = "Delete a product feature by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product feature", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product feature not found for the given ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductFeature(@PathVariable Long id) {
        return deleteService.deleteProductFeature(id);
    }
}