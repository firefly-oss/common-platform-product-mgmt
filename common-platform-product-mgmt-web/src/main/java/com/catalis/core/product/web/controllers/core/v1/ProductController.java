package com.catalis.core.product.web.controllers.core.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.core.v1.ProductCreateService;
import com.catalis.core.product.core.services.core.v1.ProductDeleteService;
import com.catalis.core.product.core.services.core.v1.ProductGetService;
import com.catalis.core.product.core.services.core.v1.ProductUpdateService;
import com.catalis.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.catalis.core.product.interfaces.enums.core.v1.ProductTypeEnum;
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
@RequestMapping("/api/v1/products")
@Tag(name = "Product Management API", description = "APIs for creating, retrieving, updating, and deleting products, " +
        "with support for advanced search and pagination features")
public class ProductController {

    @Autowired
    private ProductCreateService createService;

    @Autowired
    private ProductGetService getService;

    @Autowired
    private ProductUpdateService updateService;

    @Autowired
    private ProductDeleteService deleteService;

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return a Mono emitting the ProductDTO if found
     */
    @Operation(summary = "Retrieve a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found for the given ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> getProductById(@PathVariable Long id) {
        return getService.getProduct(id)
                .map(product -> ResponseEntity.ok(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves products by product type with pagination.
     *
     * @param type              the product type filter
     * @param paginationRequest pagination details (page size and page number)
     * @return a Mono emitting a PaginationResponse of ProductDTOs
     */
    @Operation(summary = "Retrieve products by product type with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the products",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/type/{type}")
    public Mono<ResponseEntity<PaginationResponse<ProductDTO>>> getProductsByType(@PathVariable ProductTypeEnum type,
                                                                                  PaginationRequest paginationRequest) {
        return getService.getProductsByType(type, paginationRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * Searches for products by name containing a specific string (case-insensitive) with pagination.
     *
     * @param productName       the product name search string
     * @param paginationRequest pagination details (page size and page number)
     * @return a Mono emitting a PaginationResponse of ProductDTOs
     */
    @Operation(summary = "Search for products by name containing a specific string (case-insensitive) with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the matching products",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/search")
    public Mono<ResponseEntity<PaginationResponse<ProductDTO>>> searchProductsByName(@RequestParam String productName,
                                                                                     PaginationRequest paginationRequest) {
        return getService.findByProductNameContainingIgnoreCase(productName, paginationRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * Creates a new product.
     *
     * @param request the ProductDTO containing product details
     * @return a Mono emitting the created ProductDTO
     */
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the product",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product details", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    public Mono<ResponseEntity<ProductDTO>> createProduct(@RequestBody ProductDTO request) {
        return createService.createProduct(request)
                .map(product -> ResponseEntity.status(HttpStatus.CREATED).body(product));
    }

    /**
     * Updates an existing product by its ID.
     *
     * @param id      the ID of the product to update
     * @param request the ProductDTO containing updated fields
     * @return a Mono emitting the updated ProductDTO
     */
    @Operation(summary = "Update an existing product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid update request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found for the given ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> updateProduct(@PathVariable Long id, @RequestBody ProductDTO request) {
        return updateService.updateProduct(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @return a Mono indicating the completion of the delete operation
     */
    @Operation(summary = "Delete a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found for the given ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable Long id) {
        return deleteService.deleteProduct(id)
                .<ResponseEntity<Void>> map(deleted -> ResponseEntity.noContent().build())
                .onErrorReturn(ResponseEntity.notFound().build());
    }
}