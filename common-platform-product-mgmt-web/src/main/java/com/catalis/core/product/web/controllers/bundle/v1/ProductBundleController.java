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

    /**
     * Retrieves all product bundles with pagination support.
     *
     * @param paginationRequest the pagination request that contains paging and sorting information
     * @return a reactive Mono emitting the pagination response containing a list of product bundle DTOs
     */
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
    public Mono<PaginationResponse<ProductBundleDTO>> getAllProductBundles(PaginationRequest paginationRequest) {
        return productBundleGetService.getAllProductBundles(paginationRequest);
    }

    /**
     * Retrieves a single product bundle by its ID.
     *
     * @param id the unique identifier of the product bundle to retrieve
     * @return a {@link Mono} emitting the {@link ProductBundleDTO} representing the product bundle if found,
     * or an empty {@link Mono} if the product bundle does not exist
     */
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
    public Mono<ProductBundleDTO> getProductBundleById(@PathVariable Long id) {
        return productBundleGetService.getById(id);
    }

    /**
     * Creates a new product bundle based on the provided ProductBundleDTO details.
     *
     * @param request the ProductBundleDTO containing the details of the product bundle to be created
     * @return a Mono containing the created ProductBundleDTO
     */
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
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductBundleDTO> createProductBundle(@RequestBody ProductBundleDTO request) {
        return productBundleCreateService.create(request);
    }

    /**
     * Updates an existing product bundle by its ID.
     *
     * @param id the unique identifier of the product bundle to be updated
     * @param request the details of the product bundle to be updated, encapsulated in a ProductBundleDTO object
     * @return a Mono emitting the updated ProductBundleDTO if the operation is successful
     */
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
    public Mono<ProductBundleDTO> updateProductBundle(@PathVariable Long id, @RequestBody ProductBundleDTO request) {
        return productBundleUpdateService.update(id, request);
    }

    /**
     * Deletes a product bundle by its ID.
     *
     * @param id the unique identifier of the product bundle to be deleted
     * @return a Mono that completes when the product bundle is deleted, or emits an error
     *         if the product bundle is not found or an internal error occurs
     */
    @Operation(summary = "Delete a product bundle by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product bundle", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product bundle not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductBundle(@PathVariable Long id) {
        return productBundleDeleteService.delete(id);
    }

}
