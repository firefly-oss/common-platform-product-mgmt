package com.catalis.core.product.web.controllers.pricing.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.pricing.v1.*;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingDTO;
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
@RequestMapping("/api/v1/product-pricing")
@Tag(name = "Product Pricing Management API", description = "Manage product pricing details")
public class ProductPricingController {

    @Autowired
    private ProductPricingGetService getService;

    @Autowired
    private ProductPricingCreateService createService;

    @Autowired
    private ProductPricingUpdateService updateService;

    @Autowired
    private ProductPricingDeleteService deleteService;

    /**
     * Retrieves the details of a specific product pricing by ID.
     *
     * @param productPricingId The ID of the product pricing.
     * @return A Mono containing the ProductPricingDTO.
     */
    @Operation(summary = "Get product pricing by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product pricing retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductPricingDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product pricing not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{productPricingId}")
    public Mono<ProductPricingDTO> getProductPricing(@PathVariable Long productPricingId) {
        return getService.getProductPricing(productPricingId);
    }

    /**
     * Retrieves a paginated list of pricing for a specific product ID.
     *
     * @param productId The product ID.
     * @param paginationRequest Pagination request details.
     * @return A Mono containing paginated pricing for the product.
     */
    @Operation(summary = "Get paginated product pricing by product ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product pricing retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/product/{productId}")
    public Mono<PaginationResponse<ProductPricingDTO>> findByProductId(
            @PathVariable Long productId, PaginationRequest paginationRequest) {
        return getService.findByProductId(productId, paginationRequest);
    }

    /**
     * Creates a new product pricing.
     *
     * @param request The ProductPricingDTO.
     * @return A Mono containing the created ProductPricingDTO.
     */
    @Operation(summary = "Create product pricing")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product pricing created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductPricingDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data for creating product pricing", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductPricingDTO> createProductPricing(@RequestBody ProductPricingDTO request) {
        return createService.createProductPricing(request);
    }

    /**
     * Updates an existing product pricing by ID.
     *
     * @param id The ID of the product pricing to update.
     * @param request The updated ProductPricingDTO.
     * @return A Mono containing the updated ProductPricingDTO.
     */
    @Operation(summary = "Update product pricing by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product pricing updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductPricingDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data for updating product pricing", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product pricing not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ProductPricingDTO> updateProductPricing(@PathVariable Long id, @RequestBody ProductPricingDTO request) {
        return updateService.updateProductPricing(id, request);
    }

    /**
     * Deletes product pricing by ID.
     *
     * @param id The ID of the product pricing to delete.
     * @return A Mono indicating deletion completion.
     */
    @Operation(summary = "Delete product pricing by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product pricing deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product pricing not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductPricing(@PathVariable Long id) {
        return deleteService.deleteProductPricing(id);
    }
}