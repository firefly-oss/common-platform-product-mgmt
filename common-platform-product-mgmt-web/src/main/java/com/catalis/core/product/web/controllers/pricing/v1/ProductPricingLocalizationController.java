package com.catalis.core.product.web.controllers.pricing.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.pricing.v1.*;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingLocalizationDTO;
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
@RequestMapping("/api/v1/product-pricing-localization")
@Tag(name = "Product Pricing Management API", description = "Manage product pricing details")
public class ProductPricingLocalizationController {

    @Autowired
    private ProductPricingLocalizationGetService getService;

    @Autowired
    private ProductPricingLocalizationCreateService createService;

    @Autowired
    private ProductPricingLocalizationUpdateService updateService;

    @Autowired
    private ProductPricingLocalizationDeleteService deleteService;

    /**
     * Retrieves the details of a specific product pricing localization by ID.
     *
     * @param id The ID of the product pricing localization.
     * @return A Mono containing the ProductPricingLocalizationDTO.
     */
    @Operation(summary = "Get product pricing localization by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product pricing localization retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductPricingLocalizationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product pricing localization not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{id}")
    public Mono<ProductPricingLocalizationDTO> getProductPricingLocalization(@PathVariable Long id) {
        return getService.getProductPricingLocalization(id);
    }

    /**
     * Retrieves a paginated list of product pricing localization records for a specific pricing ID.
     *
     * @param pricingId The ID of the product pricing.
     * @param paginationRequest The pagination details.
     * @return A Mono containing a paginated response of localized product pricing.
     */
    @Operation(summary = "Get paginated product pricing localization by pricing ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated product pricing localization retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/pricing/{pricingId}")
    public Mono<PaginationResponse<ProductPricingLocalizationDTO>> findByProductPricingId(
            @PathVariable Long pricingId, PaginationRequest paginationRequest) {
        return getService.findByProductPricingId(pricingId, paginationRequest);
    }

    /**
     * Creates a new product pricing localization.
     *
     * @param request The ProductPricingLocalizationDTO object containing localization details.
     * @return A Mono containing the created ProductPricingLocalizationDTO.
     */
    @Operation(summary = "Create product pricing localization")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product pricing localization created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductPricingLocalizationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductPricingLocalizationDTO> createProductPricingLocalization(@RequestBody ProductPricingLocalizationDTO request) {
        return createService.createProductPricingLocalization(request);
    }

    /**
     * Updates an existing product pricing localization by ID.
     *
     * @param id The ID of the product pricing localization to update.
     * @param request The updated ProductPricingLocalizationDTO object.
     * @return A Mono containing the updated ProductPricingLocalizationDTO.
     */
    @Operation(summary = "Update product pricing localization by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product pricing localization updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductPricingLocalizationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product pricing localization not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ProductPricingLocalizationDTO> updateProductPricingLocalization(
            @PathVariable Long id, @RequestBody ProductPricingLocalizationDTO request) {
        return updateService.updateProductPricingLocalization(id, request);
    }

    /**
     * Deletes a product pricing localization by ID.
     *
     * @param id The ID of the product pricing localization to delete.
     * @return A Mono signaling completion of the deletion process.
     */
    @Operation(summary = "Delete product pricing localization by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product pricing localization deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product pricing localization not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductPricingLocalization(@PathVariable Long id) {
        return deleteService.deleteProductPricingLocalization(id);
    }
}