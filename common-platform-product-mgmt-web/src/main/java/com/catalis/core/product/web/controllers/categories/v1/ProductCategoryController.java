package com.catalis.core.product.web.controllers.categories.v1;

import com.catalis.core.product.core.services.category.v1.ProductCategoryServiceImpl;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Product Category", description = "APIs for managing product categories in the product management platform")
@RestController
@RequestMapping("/api/v1/categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryServiceImpl service;

    @Operation(
            summary = "Get Product Category by ID",
            description = "Retrieve a specific product category by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product category",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product category not found",
                    content = @Content)
    })
    @GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductCategoryDTO>> getCategory(
            @Parameter(description = "Unique identifier of the product category", required = true)
            @PathVariable Long categoryId
    ) {
        return service.getById(categoryId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create Product Category",
            description = "Create a new product category with its associated attributes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product category created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCategoryDTO.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductCategoryDTO>> createCategory(
            @Parameter(description = "Data for the new product category", required = true,
                    schema = @Schema(implementation = ProductCategoryDTO.class))
            @RequestBody ProductCategoryDTO request
    ) {
        return service.create(request)
                .map(savedCategory -> ResponseEntity.status(201).body(savedCategory));
    }

    @Operation(
            summary = "Update Product Category",
            description = "Update the information of an existing product category by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product category updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product category not found",
                    content = @Content)
    })
    @PutMapping(value = "/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductCategoryDTO>> updateCategory(
            @Parameter(description = "Unique identifier of the product category to update", required = true)
            @PathVariable Long categoryId,
            @Parameter(description = "Updated data for the product category", required = true,
                    schema = @Schema(implementation = ProductCategoryDTO.class))
            @RequestBody ProductCategoryDTO request
    ) {
        return service.update(categoryId, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete Product Category",
            description = "Remove an existing product category by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product category deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product category not found",
                    content = @Content)
    })
    @DeleteMapping(value = "/{categoryId}")
    public Mono<ResponseEntity<Void>> deleteCategory(
            @Parameter(description = "Unique identifier of the product category to delete", required = true)
            @PathVariable Long categoryId
    ) {
        return service.delete(categoryId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}