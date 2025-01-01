package com.catalis.core.product.web.controllers.documentantion.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.documentantion.v1.*;
        import com.catalis.core.product.interfaces.dtos.documentation.v1.ProductDocumentationDTO;
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
@RequestMapping("/api/v1/product-documentations")
@Tag(name = "Product Documentation Management API", description = "Operations for managing product documentations")
public class ProductDocumentationController {

    @Autowired
    private ProductDocumentationCreateService createService;

    @Autowired
    private ProductDocumentationGetService getService;

    @Autowired
    private ProductDocumentationUpdateService updateService;

    @Autowired
    private ProductDocumentationDeleteService deleteService;

    /**
     * Retrieves a specific product documentation by its ID.
     *
     * @param productDocumentationId the ID of the documentation to retrieve
     * @return a Mono emitting the requested ProductDocumentationDTO
     */
    @Operation(summary = "Retrieve a product documentation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product documentation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDocumentationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product documentation not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{productDocumentationId}")
    public Mono<ProductDocumentationDTO> getProductDocumentationById(@PathVariable Long productDocumentationId) {
        return getService.getProductDocumentation(productDocumentationId);
    }

    /**
     * Retrieves a paginated list of documentation for a specific product by product ID.
     *
     * @param productId the ID of the product
     * @param paginationRequest pagination details
     * @return a Mono emitting a paginated list of ProductDocumentationDTOs
     */
    @Operation(summary = "Retrieve product documentations for a specific product with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product documentation list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No product documentations found for the given product ID", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/product/{productId}")
    public Mono<PaginationResponse<ProductDocumentationDTO>> getProductDocumentations(
            @PathVariable Long productId, PaginationRequest paginationRequest) {
        return getService.getProductDocumentationsByProductId(productId, paginationRequest);
    }

    /**
     * Creates a new product documentation record.
     *
     * @param request the ProductDocumentationDTO with the details of the new documentation
     * @return a Mono emitting the created ProductDocumentationDTO
     */
    @Operation(summary = "Create a new product documentation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the product documentation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDocumentationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductDocumentationDTO> createProductDocumentation(@RequestBody ProductDocumentationDTO request) {
        return createService.createProductDocumentation(request);
    }

    /**
     * Updates an existing product documentation by its ID.
     *
     * @param id      the ID of the documentation to update
     * @param request the ProductDocumentationDTO containing updated fields
     * @return a Mono emitting the updated ProductDocumentationDTO
     */
    @Operation(summary = "Update an existing product documentation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product documentation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDocumentationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product documentation not found for the given ID", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid update data provided", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ProductDocumentationDTO> updateProductDocumentation(@PathVariable Long id,
                                                                    @RequestBody ProductDocumentationDTO request) {
        return updateService.updateProductDocumentation(id, request);
    }

    /**
     * Deletes a product documentation record by its ID.
     *
     * @param id the ID of the documentation to delete
     * @return a Mono indicating the delete operation has completed
     */
    @Operation(summary = "Delete a product documentation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the product documentation", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product documentation not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductDocumentation(@PathVariable Long id) {
        return deleteService.deleteProductDocumentation(id);
    }
}