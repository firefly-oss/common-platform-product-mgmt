package com.catalis.core.product.web.controllers.bundle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleItemCreateService;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleItemDeleteService;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleItemGetService;
import com.catalis.core.product.core.services.bundle.v1.ProductBundleItemUpdateService;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
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
@RequestMapping("/api/v1/product-bundle-items")
@Tag(name = "Product Bundle Management API", description = "Operations for managing product bundles")
public class ProductBundleItemController {

    @Autowired
    private ProductBundleItemCreateService createService;

    @Autowired
    private ProductBundleItemGetService getService;

    @Autowired
    private ProductBundleItemUpdateService updateService;

    @Autowired
    private ProductBundleItemDeleteService deleteService;

    /**
     * Retrieves all product bundle items for a given product, with pagination support.
     *
     * @param productId         the ID of the product to fetch its bundle items
     * @param paginationRequest pagination parameters
     * @return a Mono emitting a paginated response of ProductBundleItemDTOs
     */
    @Operation(summary = "Retrieve all product bundle items for a given product ID with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product bundle items",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{productId}")
    public Mono<PaginationResponse<ProductBundleItemDTO>> getAllBundleItemsByProductId(
            @PathVariable Long productId, PaginationRequest paginationRequest) {
        return getService.getAllProductCategories(productId, paginationRequest);
    }

    /**
     * Retrieves a specific product bundle item by its ID.
     *
     * @param id the ID of the product bundle item to retrieve
     * @return a Mono emitting the requested ProductBundleItemDTO
     */
    @Operation(summary = "Retrieve a specific product bundle item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product bundle item",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductBundleItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product bundle item not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/item/{id}")
    public Mono<ProductBundleItemDTO> getBundleItemById(@PathVariable Long id) {
        return getService.get(id);
    }

    /**
     * Creates a new product bundle item.
     *
     * @param request the details of the product bundle item to create
     * @return a Mono emitting the newly created ProductBundleItemDTO
     */
    @Operation(summary = "Create a new product bundle item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the product bundle item",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductBundleItemDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ProductBundleItemDTO data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductBundleItemDTO> createBundleItem(@RequestBody ProductBundleItemDTO request) {
        return createService.create(request);
    }

    /**
     * Updates an existing product bundle item.
     *
     * @param id      the ID of the product bundle item to update
     * @param request the updated details of the product bundle item
     * @return a Mono emitting the updated ProductBundleItemDTO
     */
    @Operation(summary = "Update an existing product bundle item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product bundle item",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductBundleItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product bundle item not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ProductBundleItemDTO data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ProductBundleItemDTO> updateBundleItem(@PathVariable Long id, @RequestBody ProductBundleItemDTO request) {
        return updateService.update(id, request);
    }

    /**
     * Deletes a specific product bundle item by its ID.
     *
     * @param id the ID of the product bundle item to delete
     * @return a Mono indicating completion of the delete operation
     */
    @Operation(summary = "Delete a specific product bundle item by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product bundle item", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product bundle item not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBundleItem(@PathVariable Long id) {
        return deleteService.delete(id);
    }
}