package com.catalis.core.product.web.controllers.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.fee.v1.*;
        import com.catalis.core.product.interfaces.dtos.fee.v1.ProductFeeStructureDTO;
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
@RequestMapping("/api/v1/product-fee-structures")
@Tag(name = "Product Fee Management API", description = "APIs for managing and maintaining product fees")
public class ProductFeeStructureController {

    @Autowired
    private ProductFeeStructureCreateService createService;

    @Autowired
    private ProductFeeStructureGetService getService;

    @Autowired
    private ProductFeeStructureUpdateService updateService;

    @Autowired
    private ProductFeeStructureDeleteService deleteService;

    /**
     * Retrieve a Product Fee Structure by ID.
     *
     * @param productFeeStructureId The Product Fee Structure ID.
     * @return A Mono containing the ProductFeeStructureDTO.
     */
    @Operation(summary = "Retrieve a Product Fee Structure by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Product Fee Structure retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductFeeStructureDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "Product Fee Structure not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping("/{productFeeStructureId}")
    public Mono<ProductFeeStructureDTO> getProductFeeStructure(@PathVariable Long productFeeStructureId) {
        return getService.getProductFeeStructure(productFeeStructureId);
    }

    /**
     * Retrieve a paginated list of Product Fee Structures for a specific Product ID.
     *
     * @param productId The Product ID.
     * @param paginationRequest The pagination request details.
     * @return A Mono containing a PaginationResponse of ProductFeeStructureDTOs.
     */
    @Operation(summary = "Retrieve a paginated list of Product Fee Structures by Product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Product Fee Structures retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid pagination request",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping("/product/{productId}")
    public Mono<PaginationResponse<ProductFeeStructureDTO>> findByProductId(
            @PathVariable Long productId, PaginationRequest paginationRequest) {
        return getService.findByProductId(productId, paginationRequest);
    }

    /**
     * Create a new Product Fee Structure.
     *
     * @param request The ProductFeeStructureDTO containing the details for the new Product Fee Structure.
     * @return A Mono containing the created ProductFeeStructureDTO.
     */
    @Operation(summary = "Create a new Product Fee Structure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Product Fee Structure created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductFeeStructureDTO.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductFeeStructureDTO> createProductFeeStructure(@RequestBody ProductFeeStructureDTO request) {
        return createService.createProductFeeStructure(request);
    }

    /**
     * Update an existing Product Fee Structure by ID.
     *
     * @param id The Product Fee Structure ID.
     * @param request The ProductFeeStructureDTO containing the updated details.
     * @return A Mono containing the updated ProductFeeStructureDTO.
     */
    @Operation(summary = "Update a Product Fee Structure by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Product Fee Structure updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductFeeStructureDTO.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Product Fee Structure not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ProductFeeStructureDTO> updateProductFeeStructure(@PathVariable Long id, @RequestBody ProductFeeStructureDTO request) {
        return updateService.updateProductFeeStructure(id, request);
    }

    /**
     * Delete a Product Fee Structure by ID.
     *
     * @param id The Product Fee Structure ID.
     * @return A Mono completing upon successful deletion.
     */
    @Operation(summary = "Delete a Product Fee Structure by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Product Fee Structure deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Product Fee Structure not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductFeeStructure(@PathVariable Long id) {
        return deleteService.deleteProductFeeStructure(id);
    }
}