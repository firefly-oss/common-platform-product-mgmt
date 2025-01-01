package com.catalis.core.product.web.controllers.category.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.category.v1.ProductCategoryCreateService;
import com.catalis.core.product.core.services.category.v1.ProductCategoryDeleteService;
import com.catalis.core.product.core.services.category.v1.ProductCategoryGetService;
import com.catalis.core.product.core.services.category.v1.ProductCategoryUpdateService;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
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
@RequestMapping("/api/v1/product-categories")
@Tag(name = "Product Category Management API", description = "Operations for managing product categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryCreateService createService;

    @Autowired
    private ProductCategoryGetService getService;

    @Autowired
    private ProductCategoryUpdateService updateService;

    @Autowired
    private ProductCategoryDeleteService deleteService;

    /**
     * Retrieves a paginated list of all product categories.
     *
     * @param paginationRequest Pagination details such as page size and page number
     * @return A Mono emitting a paginated list of ProductCategoryDTOs
     */
    @Operation(summary = "Retrieve a paginated list of all product categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product categories",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping
    public Mono<PaginationResponse<ProductCategoryDTO>> getAllProductCategories(PaginationRequest paginationRequest) {
        return getService.getAllProductCategories(paginationRequest);
    }

    /**
     * Retrieves a product category by its unique ID.
     *
     * @param id The ID of the product category
     * @return A Mono emitting the requested ProductCategoryDTO
     */
    @Operation(summary = "Retrieve a product category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product category",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product category not found for the ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{id}")
    public Mono<ProductCategoryDTO> getProductCategoryById(@PathVariable Long id) {
        return getService.getProductCategory(id);
    }

    /**
     * Creates a new product category.
     *
     * @param request The ProductCategoryDTO containing the details of the new category
     * @return A Mono emitting the newly created ProductCategoryDTO
     */
    @Operation(summary = "Create a new product category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the product category",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ProductCategoryDTO data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductCategoryDTO> createProductCategory(@RequestBody ProductCategoryDTO request) {
        return createService.create(request);
    }

    /**
     * Updates an existing product category by its ID.
     *
     * @param id      The ID of the product category to update
     * @param request The ProductCategoryDTO containing the updated details
     * @return A Mono emitting the updated ProductCategoryDTO
     */
    @Operation(summary = "Update an existing product category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product category",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product category not found for the ID", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ProductCategoryDTO data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ProductCategoryDTO> updateProductCategory(@PathVariable Long id, @RequestBody ProductCategoryDTO request) {
        return updateService.update(id, request);
    }

    /**
     * Deletes a product category by its ID.
     *
     * @param id The ID of the product category to delete
     * @return A Mono indicating completion of the delete operation
     */
    @Operation(summary = "Delete a product category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product category", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product category not found for the ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductCategory(@PathVariable Long id) {
        return deleteService.deleteProductCategory(id);
    }
}
