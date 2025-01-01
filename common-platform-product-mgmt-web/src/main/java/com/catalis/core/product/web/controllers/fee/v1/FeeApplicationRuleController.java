package com.catalis.core.product.web.controllers.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.fee.v1.FeeApplicationRuleCreateService;
import com.catalis.core.product.core.services.fee.v1.FeeApplicationRuleDeleteService;
import com.catalis.core.product.core.services.fee.v1.FeeApplicationRuleGetService;
import com.catalis.core.product.core.services.fee.v1.FeeApplicationRuleUpdateService;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeApplicationRuleDTO;
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
@RequestMapping("/api/v1/fee-application-rules")
@Tag(name = "Product Fee Management API", description = "APIs for managing and maintaining product fees")
public class FeeApplicationRuleController {

    @Autowired
    private FeeApplicationRuleCreateService createService;

    @Autowired
    private FeeApplicationRuleGetService getService;

    @Autowired
    private FeeApplicationRuleUpdateService updateService;

    @Autowired
    private FeeApplicationRuleDeleteService deleteService;

    /**
     * Retrieves a Fee Application Rule by its ID.
     *
     * @param feeApplicationRuleId The ID of the Fee Application Rule to retrieve.
     * @return The requested Fee Application Rule.
     */
    @Operation(summary = "Retrieve a Fee Application Rule by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee Application Rule found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FeeApplicationRuleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Fee Application Rule not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{feeApplicationRuleId}")
    public Mono<FeeApplicationRuleDTO> getFeeApplicationRule(@PathVariable Long feeApplicationRuleId) {
        return getService.getFeeApplicationRule(feeApplicationRuleId);
    }

    /**
     * Retrieves a paginated list of Fee Application Rules for a specific fee component.
     *
     * @param feeComponentId The Fee Component ID to filter by.
     * @param paginationRequest The pagination details (page size, page number, etc.).
     * @return A paginated response containing Fee Application Rules.
     */
    @Operation(summary = "Retrieve Fee Application Rules for a specific Fee Component with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee Application Rules retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/fee-component/{feeComponentId}")
    public Mono<PaginationResponse<FeeApplicationRuleDTO>> getFeeApplicationRulesByComponentId(
            @PathVariable Long feeComponentId, PaginationRequest paginationRequest) {
        return getService.findByFeeComponentId(feeComponentId, paginationRequest);
    }

    /**
     * Creates a new Fee Application Rule.
     *
     * @param feeApplicationRuleDTO The details of the Fee Application Rule to be created.
     * @return The created Fee Application Rule.
     */
    @Operation(summary = "Create a new Fee Application Rule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fee Application Rule created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FeeApplicationRuleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FeeApplicationRuleDTO> createFeeApplicationRule(@RequestBody FeeApplicationRuleDTO feeApplicationRuleDTO) {
        return createService.createFeeApplicationRule(feeApplicationRuleDTO);
    }

    /**
     * Updates an existing Fee Application Rule by its ID.
     *
     * @param id The ID of the Fee Application Rule to update.
     * @param feeApplicationRuleDTO The updated Fee Application Rule details.
     * @return The updated Fee Application Rule.
     */
    @Operation(summary = "Update an existing Fee Application Rule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee Application Rule updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FeeApplicationRuleDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Fee Application Rule not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<FeeApplicationRuleDTO> updateFeeApplicationRule(
            @PathVariable Long id, @RequestBody FeeApplicationRuleDTO feeApplicationRuleDTO) {
        return updateService.updateFeeApplicationRule(id, feeApplicationRuleDTO);
    }

    /**
     * Deletes a Fee Application Rule by its ID.
     *
     * @param id The ID of the Fee Application Rule to delete.
     * @return A response indicating the deletion was successful.
     */
    @Operation(summary = "Delete a Fee Application Rule by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fee Application Rule deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Fee Application Rule not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteFeeApplicationRule(@PathVariable Long id) {
        return deleteService.deleteFeeApplicationRule(id);
    }
}
