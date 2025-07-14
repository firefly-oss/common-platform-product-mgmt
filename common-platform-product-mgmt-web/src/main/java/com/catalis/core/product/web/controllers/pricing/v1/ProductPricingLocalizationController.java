package com.catalis.core.product.web.controllers.pricing.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.pricing.v1.ProductPricingLocalizationServiceImpl;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingLocalizationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Product Pricing Localization", description = "APIs for managing localized pricing data under a specific product pricing record")
@RestController
@RequestMapping("/api/v1/products/{productId}/pricings/{pricingId}/localizations")
public class ProductPricingLocalizationController {

    @Autowired
    private ProductPricingLocalizationServiceImpl service;

    @Operation(
            summary = "List Localizations for Product Pricing",
            description = "Retrieve a paginated list of all localization records associated with a specific product pricing."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the localization records",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No localization records found for the specified pricing",
                    content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ProductPricingLocalizationDTO>>> getAllLocalizations(
            @Parameter(description = "Unique identifier of the product (unused in the service, but kept for hierarchy)", required = true)
            @PathVariable Long productId,

            @Parameter(description = "Unique identifier of the product pricing record", required = true)
            @PathVariable Long pricingId,

            @ParameterObject
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return service.getAllLocalizations(pricingId, paginationRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create Localization for Product Pricing",
            description = "Create a new localization record and associate it with the specified product pricing."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Localization record created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductPricingLocalizationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid localization data provided",
                    content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductPricingLocalizationDTO>> createLocalization(
            @Parameter(description = "Unique identifier of the product (unused in the service, but kept for hierarchy)", required = true)
            @PathVariable Long productId,

            @Parameter(description = "Unique identifier of the product pricing record", required = true)
            @PathVariable Long pricingId,

            @Parameter(description = "Data for the new localization record", required = true,
                    schema = @Schema(implementation = ProductPricingLocalizationDTO.class))
            @RequestBody ProductPricingLocalizationDTO request
    ) {
        return service.createLocalization(pricingId, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(
            summary = "Get Localization by ID",
            description = "Retrieve a specific localization record by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the localization record",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductPricingLocalizationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Localization record not found",
                    content = @Content)
    })
    @GetMapping(value = "/{localizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductPricingLocalizationDTO>> getLocalization(
            @Parameter(description = "Unique identifier of the product (unused in the service, but kept for hierarchy)", required = true)
            @PathVariable Long productId,

            @Parameter(description = "Unique identifier of the product pricing record", required = true)
            @PathVariable Long pricingId,

            @Parameter(description = "Unique identifier of the localization record", required = true)
            @PathVariable Long localizationId
    ) {
        return service.getLocalization(pricingId, localizationId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update Localization",
            description = "Update an existing localization record for a product pricing."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Localization record updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductPricingLocalizationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Localization record not found",
                    content = @Content)
    })
    @PutMapping(value = "/{localizationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductPricingLocalizationDTO>> updateLocalization(
            @Parameter(description = "Unique identifier of the product (unused in the service, but kept for hierarchy)", required = true)
            @PathVariable Long productId,

            @Parameter(description = "Unique identifier of the product pricing record", required = true)
            @PathVariable Long pricingId,

            @Parameter(description = "Unique identifier of the localization record to update", required = true)
            @PathVariable Long localizationId,

            @Parameter(description = "Updated data for the localization record", required = true,
                    schema = @Schema(implementation = ProductPricingLocalizationDTO.class))
            @RequestBody ProductPricingLocalizationDTO request
    ) {
        return service.updateLocalization(pricingId, localizationId, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete Localization",
            description = "Remove an existing localization record by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Localization record deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Localization record not found",
                    content = @Content)
    })
    @DeleteMapping(value = "/{localizationId}")
    public Mono<ResponseEntity<Void>> deleteLocalization(
            @Parameter(description = "Unique identifier of the product (unused in the service, but kept for hierarchy)", required = true)
            @PathVariable Long productId,

            @Parameter(description = "Unique identifier of the product pricing record", required = true)
            @PathVariable Long pricingId,

            @Parameter(description = "Unique identifier of the localization record to delete", required = true)
            @PathVariable Long localizationId
    ) {
        return service.deleteLocalization(pricingId, localizationId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}