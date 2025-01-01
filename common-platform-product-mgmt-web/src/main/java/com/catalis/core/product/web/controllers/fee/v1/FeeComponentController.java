package com.catalis.core.product.web.controllers.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.fee.v1.*;
        import com.catalis.core.product.interfaces.dtos.fee.v1.FeeComponentDTO;
import com.catalis.core.product.interfaces.enums.fee.v1.FeeTypeEnum;
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
@RequestMapping("/api/v1/fee-components")
@Tag(name = "Product Fee Management API", description = "APIs for managing and maintaining product fees")
public class FeeComponentController {

    @Autowired
    private FeeComponentCreateService createService;

    @Autowired
    private FeeComponentGetService getService;

    @Autowired
    private FeeComponentUpdateService updateService;

    @Autowired
    private FeeComponentDeleteService deleteService;

    /**
     * Retrieve a Fee Component by ID.
     *
     * @param feeComponentId The Fee Component ID to retrieve.
     * @return The requested Fee Component.
     */
    @Operation(summary = "Retrieve a Fee Component by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fee Component retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeeComponentDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "Fee Component not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping("/{feeComponentId}")
    public Mono<FeeComponentDTO> getFeeComponent(@PathVariable Long feeComponentId) {
        return getService.getFeeComponent(feeComponentId);
    }

    /**
     * Retrieve a paginated list of Fee Components by Fee Type.
     *
     * @param feeType The Fee Type to filter by.
     * @param paginationRequest The pagination request details.
     * @return A paginated list of Fee Components.
     */
    @Operation(summary = "Retrieve a paginated list of Fee Components by Fee Type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fee Components retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid pagination request",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping("/fee-type/{feeType}")
    public Mono<PaginationResponse<FeeComponentDTO>> findByFeeType(
            @PathVariable FeeTypeEnum feeType, PaginationRequest paginationRequest) {
        return getService.findByFeeType(feeType, paginationRequest);
    }

    /**
     * Retrieve a paginated list of Fee Components associated with a Fee Structure ID.
     *
     * @param feeStructureId The Fee Structure ID to filter by.
     * @param paginationRequest The pagination request details.
     * @return A paginated list of Fee Components.
     */
    @Operation(summary = "Retrieve a paginated list of Fee Components by Fee Structure ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fee Components retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid pagination request",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping("/fee-structure/{feeStructureId}")
    public Mono<PaginationResponse<FeeComponentDTO>> findByFeeStructureId(
            @PathVariable Long feeStructureId, PaginationRequest paginationRequest) {
        return getService.findByFeeStructureId(feeStructureId, paginationRequest);
    }

    /**
     * Create a new Fee Component.
     *
     * @param feeComponentDTO The Fee Component details.
     * @return The created Fee Component.
     */
    @Operation(summary = "Create a new Fee Component")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Fee Component created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeeComponentDTO.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FeeComponentDTO> createFeeComponent(@RequestBody FeeComponentDTO feeComponentDTO) {
        return createService.createFeeComponent(feeComponentDTO);
    }

    /**
     * Update a Fee Component by ID.
     *
     * @param id The Fee Component ID to update.
     * @param feeComponentDTO The updated details.
     * @return The updated Fee Component.
     */
    @Operation(summary = "Update a Fee Component by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fee Component updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeeComponentDTO.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Fee Component not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<FeeComponentDTO> updateFeeComponent(@PathVariable Long id, @RequestBody FeeComponentDTO feeComponentDTO) {
        return updateService.updateFeeComponent(id, feeComponentDTO);
    }

    /**
     * Delete a Fee Component by ID.
     *
     * @param id The Fee Component ID to delete.
     * @return A response indicating successful deletion.
     */
    @Operation(summary = "Delete a Fee Component by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Fee Component deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Fee Component not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteFeeComponent(@PathVariable Long id) {
        return deleteService.deleteFeeComponent(id);
    }
}