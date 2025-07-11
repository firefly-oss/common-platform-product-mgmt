package com.catalis.core.product.web.controllers.bundle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleServiceImpl;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Product Bundle", description = "APIs for managing product bundles in the product management platform")
@RestController
@RequestMapping("/api/v1/bundles")
public class ProductBundleController {

    @Autowired
    private ProductBundleServiceImpl service;

    @Operation(
            summary = "Get Product Bundle by ID",
            description = "Retrieve a specific product bundle using its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the product bundle",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductBundleDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid Bundle ID provided", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product bundle not found", content = @Content)
    })
    @GetMapping(value = "/{bundleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductBundleDTO>> getById(
            @Parameter(description = "The unique identifier of the product bundle", required = true)
            @PathVariable Long bundleId
    ) {
        return service.getById(bundleId)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @Operation(
            summary = "List Product Bundles",
            description = "Retrieve a paginated list of all product bundles."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the list of product bundles"
            ),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters", content = @Content)
    })
    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ProductBundleDTO>>> getAll(
            @RequestBody PaginationRequest paginationRequest
    ) {
        return service.getAll(paginationRequest)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @Operation(
            summary = "Create Product Bundle",
            description = "Create a new product bundle containing multiple products or offerings."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product bundle created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductBundleDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductBundleDTO>> create(
            @Parameter(description = "Data for the new product bundle", required = true,
                    schema = @Schema(implementation = ProductBundleDTO.class))
            @RequestBody ProductBundleDTO productBundleDTO
    ) {
        return service.create(productBundleDTO)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @Operation(
            summary = "Update Product Bundle",
            description = "Update the information of an existing product bundle by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product bundle updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductBundleDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data or bundle ID provided", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product bundle not found", content = @Content)
    })
    @PutMapping(value = "/{bundleId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductBundleDTO>> update(
            @Parameter(description = "The unique identifier of the product bundle to update", required = true)
            @PathVariable Long bundleId,

            @Parameter(description = "Updated product bundle data", required = true,
                    schema = @Schema(implementation = ProductBundleDTO.class))
            @RequestBody ProductBundleDTO productBundleDTO
    ) {
        return service.update(bundleId, productBundleDTO)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @Operation(
            summary = "Delete Product Bundle",
            description = "Delete an existing product bundle by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product bundle deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product bundle not found", content = @Content)
    })
    @DeleteMapping(value = "/{bundleId}")
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "The unique identifier of the product bundle to delete", required = true)
            @PathVariable Long bundleId
    ) {
        return service.delete(bundleId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
