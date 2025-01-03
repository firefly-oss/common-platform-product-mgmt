package com.catalis.core.product.web.controllers.category.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.category.v1.ProductSubtypeCreateService;
import com.catalis.core.product.core.services.category.v1.ProductSubtypeDeleteService;
import com.catalis.core.product.core.services.category.v1.ProductSubtypeGetService;
import com.catalis.core.product.core.services.category.v1.ProductSubtypeUpdateService;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductSubtypeDTO;
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
@RequestMapping("/api/v1/product-subtypes")
@Tag(name = "Product Category Management API", description = "Operations for managing product categories")
public class ProductSubtypeController {

    @Autowired
    private ProductSubtypeCreateService createService;

    @Autowired
    private ProductSubtypeGetService getService;

    @Autowired
    private ProductSubtypeUpdateService updateService;

    @Autowired
    private ProductSubtypeDeleteService deleteService;

    /**
     * Retrieves a paginated list of product subtypes for a given category ID.
     *
     * @param categoryId        the ID of the product category associated with the subtypes
     * @param paginationRequest the pagination request containing page size and page number
     * @return a Mono emitting a paginated list of ProductSubtypeDTOs
     */
    @Operation(summary = "Retrieve a paginated list of product subtypes by category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product subtypes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/category/{categoryId}")
    public Mono<ResponseEntity<PaginationResponse<ProductSubtypeDTO>>> getAllProductSubtypesByCategoryId(
            @PathVariable Long categoryId, PaginationRequest paginationRequest) {
        return getService.getAllProductSubtypesByCategoryId(categoryId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * Retrieves a single product subtype by its ID.
     *
     * @param id the ID of the product subtype to retrieve
     * @return a Mono emitting the requested ProductSubtypeDTO
     */
    @Operation(summary = "Retrieve a product subtype by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product subtype",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductSubtypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product subtype not found for the ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductSubtypeDTO>> getProductSubtypeById(@PathVariable Long id) {
        return getService.getProductSubtype(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new product subtype.
     *
     * @param request the ProductSubtypeDTO containing the details of the new subtype
     * @return a Mono emitting the newly created ProductSubtypeDTO
     */
    @Operation(summary = "Create a new product subtype")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the product subtype",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductSubtypeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ProductSubtypeDTO data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<ProductSubtypeDTO>> createProductSubtype(@RequestBody ProductSubtypeDTO request) {
        return createService.create(request)
                .map(createdSubtype -> ResponseEntity.status(HttpStatus.CREATED).body(createdSubtype));
    }

    /**
     * Updates an existing product subtype by its ID.
     *
     * @param id      the ID of the product subtype to update
     * @param request the ProductSubtypeDTO containing the updated details
     * @return a Mono emitting the updated ProductSubtypeDTO
     */
    @Operation(summary = "Update an existing product subtype by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product subtype",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductSubtypeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product subtype not found for the ID", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ProductSubtypeDTO data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductSubtypeDTO>> updateProductSubtype(@PathVariable Long id, @RequestBody ProductSubtypeDTO request) {
        return updateService.update(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a specific product subtype by its ID.
     *
     * @param id the ID of the product subtype to delete
     * @return a Mono indicating the completion of the delete operation
     */
    @Operation(summary = "Delete a product subtype by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product subtype", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product subtype not found for the ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProductSubtype(@PathVariable Long id) {
        return deleteService.deleteProductSubtype(id).
                        <ResponseEntity<Void>> map(deleted -> ResponseEntity.noContent().build())
                .onErrorReturn(ResponseEntity.notFound().build());
    }
}