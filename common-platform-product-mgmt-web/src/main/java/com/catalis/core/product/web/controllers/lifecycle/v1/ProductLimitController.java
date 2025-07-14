package com.catalis.core.product.web.controllers.lifecycle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.lifecycle.v1.ProductLimitServiceImpl;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLimitDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Product Limit", description = "APIs for managing limits (restrictions, thresholds, etc.) associated with a specific product")
@RestController
@RequestMapping("/api/v1/products/{productId}/limits")
public class ProductLimitController {

    @Autowired
    private ProductLimitServiceImpl service;


    @Operation(
            summary = "List Product Limits",
            description = "Retrieve a paginated list of all limits associated with the specified product."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product limits",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No limits found for the specified product",
                    content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ProductLimitDTO>>> getAllProductLimits(
            @Parameter(description = "Unique identifier of the product", required = true)
            @PathVariable Long productId,

            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.getAllProductLimits(productId, paginationRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create Product Limit",
            description = "Create a new limit and associate it with a product."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product limit created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductLimitDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product limit data provided",
                    content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductLimitDTO>> createProductLimit(
            @Parameter(description = "Unique identifier of the product", required = true)
            @PathVariable Long productId,

            @Parameter(description = "Data for the new product limit", required = true,
                    schema = @Schema(implementation = ProductLimitDTO.class))
            @RequestBody ProductLimitDTO productLimitDTO
    ) {
        return service.createProductLimit(productId, productLimitDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(
            summary = "Get Product Limit by ID",
            description = "Retrieve a specific product limit by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product limit",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductLimitDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product limit not found",
                    content = @Content)
    })
    @GetMapping(value = "/{limitId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductLimitDTO>> getProductLimit(
            @Parameter(description = "Unique identifier of the product", required = true)
            @PathVariable Long productId,

            @Parameter(description = "Unique identifier of the product limit", required = true)
            @PathVariable Long limitId
    ) {
        return service.getProductLimit(productId, limitId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update Product Limit",
            description = "Update an existing product limit with new data."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product limit updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductLimitDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product limit not found",
                    content = @Content)
    })
    @PutMapping(value = "/{limitId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductLimitDTO>> updateProductLimit(
            @Parameter(description = "Unique identifier of the product", required = true)
            @PathVariable Long productId,

            @Parameter(description = "Unique identifier of the product limit to update", required = true)
            @PathVariable Long limitId,

            @Parameter(description = "Updated data for the product limit", required = true,
                    schema = @Schema(implementation = ProductLimitDTO.class))
            @RequestBody ProductLimitDTO productLimitDTO
    ) {
        return service.updateProductLimit(productId, limitId, productLimitDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete Product Limit",
            description = "Remove an existing product limit by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product limit deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product limit not found",
                    content = @Content)
    })
    @DeleteMapping(value = "/{limitId}")
    public Mono<ResponseEntity<Void>> deleteProductLimit(
            @Parameter(description = "Unique identifier of the product", required = true)
            @PathVariable Long productId,

            @Parameter(description = "Unique identifier of the product limit to delete", required = true)
            @PathVariable Long limitId
    ) {
        return service.deleteProductLimit(productId, limitId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}