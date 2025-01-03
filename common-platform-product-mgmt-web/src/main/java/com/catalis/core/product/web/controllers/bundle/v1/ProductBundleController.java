package com.catalis.core.product.web.controllers.bundle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleCreateService;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleDeleteService;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleGetService;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleUpdateService;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
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
@RequestMapping("/api/v1/product-bundles")
@Tag(name = "Product Bundle Management API", description = "Operations for managing product bundles")
public class ProductBundleController {

    @Autowired
    private ProductBundleGetService productBundleGetService;

    @Autowired
    private ProductBundleCreateService productBundleCreateService;

    @Autowired
    private ProductBundleUpdateService productBundleUpdateService;

    @Autowired
    private ProductBundleDeleteService productBundleDeleteService;

    @Operation(summary = "Retrieve all product bundles with pagination")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved product bundles",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))}
            ),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping
    public Mono<ResponseEntity<PaginationResponse<ProductBundleDTO>>> getAllProductBundles(PaginationRequest paginationRequest) {
        return productBundleGetService.getAllProductBundles(paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Retrieve a single product bundle by its ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the product bundle",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductBundleDTO.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Product bundle not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductBundleDTO>> getProductBundleById(@PathVariable Long id) {
        return productBundleGetService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new product bundle")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully created the product bundle",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductBundleDTO.class))}
            ),
            @ApiResponse(responseCode = "400", description = "Invalid ProductBundleDTO", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    public Mono<ResponseEntity<ProductBundleDTO>> createProductBundle(@RequestBody ProductBundleDTO request) {
        return productBundleCreateService.create(request)
                .map(bundle -> ResponseEntity.status(HttpStatus.CREATED).body(bundle));
    }

    @Operation(summary = "Update an existing product bundle by its ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated the product bundle",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductBundleDTO.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Product bundle not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ProductBundleDTO", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductBundleDTO>> updateProductBundle(@PathVariable Long id, @RequestBody ProductBundleDTO request) {
        return productBundleUpdateService.update(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a product bundle by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product bundle", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product bundle not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProductBundle(@PathVariable Long id) {
        return productBundleDeleteService.delete(id)
                .<ResponseEntity<Void>> map(deleted -> ResponseEntity.noContent().build())
                .onErrorReturn(ResponseEntity.notFound().build());
    }

}
