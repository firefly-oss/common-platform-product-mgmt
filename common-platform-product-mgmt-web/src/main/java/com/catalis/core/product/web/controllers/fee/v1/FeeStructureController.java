package com.catalis.core.product.web.controllers.fee.v1;

import com.catalis.core.product.core.services.fee.v1.FeeStructureServiceImpl;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeStructureDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Product Fee Structure", description = "APIs for managing fee structures in the product platform")
@RestController
@RequestMapping("/api/v1/fee-structures")
public class FeeStructureController {

    @Autowired
    private FeeStructureServiceImpl service;

    @Operation(
            summary = "Get Fee Structure by ID",
            description = "Retrieve a specific fee structure by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the fee structure",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeeStructureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Fee structure not found", content = @Content)
    })
    @GetMapping(value = "/{feeStructureId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<FeeStructureDTO>> getFeeStructure(
            @Parameter(description = "Unique identifier of the fee structure", required = true)
            @PathVariable Long feeStructureId
    ) {
        return service.getFeeStructure(feeStructureId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create Fee Structure",
            description = "Create a new fee structure to define fees within the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee structure created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeeStructureDTO.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<FeeStructureDTO>> createFeeStructure(
            @Parameter(description = "Data for the new fee structure", required = true,
                    schema = @Schema(implementation = FeeStructureDTO.class))
            @RequestBody FeeStructureDTO feeStructureDTO
    ) {
        return service.createFeeStructure(feeStructureDTO)
                .map(ResponseEntity::ok);
    }

    @Operation(
            summary = "Update Fee Structure",
            description = "Update an existing fee structure by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee structure updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FeeStructureDTO.class))),
            @ApiResponse(responseCode = "404", description = "Fee structure not found", content = @Content)
    })
    @PutMapping(value = "/{feeStructureId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<FeeStructureDTO>> updateFeeStructure(
            @Parameter(description = "Unique identifier of the fee structure to update", required = true)
            @PathVariable Long feeStructureId,

            @Parameter(description = "Updated data for the fee structure", required = true,
                    schema = @Schema(implementation = FeeStructureDTO.class))
            @RequestBody FeeStructureDTO feeStructureDTO
    ) {
        return service.updateFeeStructure(feeStructureId, feeStructureDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete Fee Structure",
            description = "Remove an existing fee structure by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fee structure deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Fee structure not found", content = @Content)
    })
    @DeleteMapping(value = "/{feeStructureId}")
    public Mono<ResponseEntity<Void>> deleteFeeStructure(
            @Parameter(description = "Unique identifier of the fee structure", required = true)
            @PathVariable Long feeStructureId
    ) {
        return service.deleteFeeStructure(feeStructureId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}