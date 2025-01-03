package com.catalis.core.product.web.controllers.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.fee.v1.FeeComponentCreateService;
import com.catalis.core.product.core.services.fee.v1.FeeComponentDeleteService;
import com.catalis.core.product.core.services.fee.v1.FeeComponentGetService;
import com.catalis.core.product.core.services.fee.v1.FeeComponentUpdateService;
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
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Retrieve a Fee Component by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee Component retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FeeComponentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Fee Component not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{feeComponentId}")
    public Mono<ResponseEntity<FeeComponentDTO>> getFeeComponent(@PathVariable Long feeComponentId) {
        return getService.getFeeComponent(feeComponentId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Retrieve a paginated list of Fee Components by Fee Type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee Components retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/fee-type/{feeType}")
    public Mono<ResponseEntity<PaginationResponse<FeeComponentDTO>>> findByFeeType(
            @PathVariable FeeTypeEnum feeType, PaginationRequest paginationRequest) {
        return getService.findByFeeType(feeType, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Retrieve a paginated list of Fee Components by Fee Structure ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee Components retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/fee-structure/{feeStructureId}")
    public Mono<ResponseEntity<PaginationResponse<FeeComponentDTO>>> findByFeeStructureId(
            @PathVariable Long feeStructureId, PaginationRequest paginationRequest) {
        return getService.findByFeeStructureId(feeStructureId, paginationRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create a new Fee Component")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fee Component created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FeeComponentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    public Mono<ResponseEntity<FeeComponentDTO>> createFeeComponent(@RequestBody FeeComponentDTO feeComponentDTO) {
        return createService.createFeeComponent(feeComponentDTO)
                .map(component -> ResponseEntity.status(HttpStatus.CREATED).body(component));
    }

    @Operation(summary = "Update a Fee Component by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee Component updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FeeComponentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Fee Component not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<FeeComponentDTO>> updateFeeComponent(@PathVariable Long id, @RequestBody FeeComponentDTO feeComponentDTO) {
        return updateService.updateFeeComponent(id, feeComponentDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Delete a Fee Component by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fee Component deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Fee Component not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteFeeComponent(@PathVariable Long id) {
        return deleteService.deleteFeeComponent(id)
                .<ResponseEntity<Void>> map(deleted -> ResponseEntity.noContent().build())
                .onErrorReturn(ResponseEntity.notFound().build());
    }
}