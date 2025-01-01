package com.catalis.core.product.web.controllers.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.fee.v1.FeeStructureCreateService;
import com.catalis.core.product.core.services.fee.v1.FeeStructureDeleteService;
import com.catalis.core.product.core.services.fee.v1.FeeStructureGetService;
import com.catalis.core.product.core.services.fee.v1.FeeStructureUpdateService;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeStructureDTO;
import com.catalis.core.product.interfaces.enums.fee.v1.FeeStructureTypeEnum;
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
@RequestMapping("/api/v1/fee-structures")
@Tag(name = "Product Fee Management API", description = "APIs for managing and maintaining product fees")
public class FeeStructureController {

    @Autowired
    private FeeStructureCreateService createService;

    @Autowired
    private FeeStructureGetService getService;

    @Autowired
    private FeeStructureUpdateService updateService;

    @Autowired
    private FeeStructureDeleteService deleteService;

    /**
     * Retrieve a Fee Structure by ID.
     *
     * @param feeStructureId The Fee Structure ID.
     * @return A Mono containing the FeeStructureDTO.
     */
    @Operation(summary = "Retrieve a Fee Structure by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fee Structure retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeeStructureDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "Fee Structure not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping("/{feeStructureId}")
    public Mono<FeeStructureDTO> getFeeStructure(@PathVariable Long feeStructureId) {
        return getService.getFeeStructure(feeStructureId);
    }

    /**
     * Retrieve a paginated list of Fee Structures by Fee Structure Type.
     *
     * @param feeStructureType The Fee Structure Type.
     * @param paginationRequest The pagination request details.
     * @return A Mono containing a PaginationResponse of FeeStructureDTOs.
     */
    @Operation(summary = "Retrieve a paginated list of Fee Structures by Fee Structure Type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fee Structures retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid pagination parameters",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @GetMapping("/type/{feeStructureType}")
    public Mono<PaginationResponse<FeeStructureDTO>> findByFeeStructureType(
            @PathVariable FeeStructureTypeEnum feeStructureType, PaginationRequest paginationRequest) {
        return getService.findByFeeStructureType(feeStructureType, paginationRequest);
    }

    /**
     * Create a new Fee Structure.
     *
     * @param feeStructureDTO The details of the Fee Structure to create.
     * @return A Mono containing the created FeeStructureDTO.
     */
    @Operation(summary = "Create a new Fee Structure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Fee Structure created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeeStructureDTO.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FeeStructureDTO> createFeeStructure(@RequestBody FeeStructureDTO feeStructureDTO) {
        return createService.createFeeStructure(feeStructureDTO);
    }

    /**
     * Update an existing Fee Structure by ID.
     *
     * @param id The Fee Structure ID.
     * @param feeStructureDTO The updated details.
     * @return A Mono containing the updated FeeStructureDTO.
     */
    @Operation(summary = "Update an existing Fee Structure by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fee Structure updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeeStructureDTO.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Fee Structure not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<FeeStructureDTO> updateFeeStructure(@PathVariable Long id, @RequestBody FeeStructureDTO feeStructureDTO) {
        return updateService.updateFeeStructure(id, feeStructureDTO);
    }

    /**
     * Delete a Fee Structure by ID.
     *
     * @param id The Fee Structure ID.
     * @return A Mono completing upon successful deletion.
     */
    @Operation(summary = "Delete a Fee Structure by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Fee Structure deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Fee Structure not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteFeeStructure(@PathVariable Long id) {
        return deleteService.deleteFeeStructure(id);
    }
}