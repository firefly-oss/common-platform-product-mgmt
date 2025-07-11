package com.catalis.core.product.web.controllers.localization.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.services.localization.v1.ProductLocalizationServiceImpl;
import com.catalis.core.product.interfaces.dtos.localization.v1.ProductLocalizationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

@Tag(name = "Product Localization", description = "APIs for managing localized data (translations, region-specific info) for a product")
@RestController
@RequestMapping("/api/v1/products/{productId}/localizations")
public class ProductLocalizationController {

    @Autowired
    private ProductLocalizationServiceImpl service;

    @Operation(
            summary = "List Product Localizations",
            description = "Retrieve a paginated list of all localizations associated with the specified product."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the list of product localizations"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No localizations found for the specified product",
                    content = @Content
            )
    })
    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ProductLocalizationDTO>>> getAllLocalizations(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Unique identifier of the product",
                    required = true
            )
            @PathVariable Long productId,

            @RequestBody PaginationRequest paginationRequest
    ) {
        return service.getAllLocalizations(productId, paginationRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create Product Localization",
            description = "Create a new localization record associated with a specific product."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product localization created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductLocalizationDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product localization data provided",
                    content = @Content
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductLocalizationDTO>> createLocalization(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Unique identifier of the product",
                    required = true
            )
            @PathVariable Long productId,

            @Parameter(
                    description = "Data for the new product localization record",
                    required = true,
                    schema = @Schema(implementation = ProductLocalizationDTO.class)
            )
            @RequestBody ProductLocalizationDTO localizationDTO
    ) {
        return service.createLocalization(productId, localizationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(
            summary = "Get Product Localization by ID",
            description = "Retrieve a specific localization record for the given product."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the product localization record",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductLocalizationDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product localization not found",
                    content = @Content
            )
    })
    @GetMapping(value = "/{localizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductLocalizationDTO>> getLocalizationById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Unique identifier of the product",
                    required = true
            )
            @PathVariable Long productId,

            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Unique identifier of the localization record",
                    required = true
            )
            @PathVariable Long localizationId
    ) {
        return service.getLocalizationById(productId, localizationId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update Product Localization",
            description = "Update an existing product localization record."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product localization updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductLocalizationDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product localization not found",
                    content = @Content
            )
    })
    @PutMapping(value = "/{localizationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductLocalizationDTO>> updateLocalization(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Unique identifier of the product",
                    required = true
            )
            @PathVariable Long productId,

            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Unique identifier of the localization record to update",
                    required = true
            )
            @PathVariable Long localizationId,

            @Parameter(
                    description = "Updated data for the product localization",
                    required = true,
                    schema = @Schema(implementation = ProductLocalizationDTO.class)
            )
            @RequestBody ProductLocalizationDTO localizationDTO
    ) {
        return service.updateLocalization(productId, localizationId, localizationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete Product Localization",
            description = "Remove an existing localization record from the product."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Product localization deleted successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product localization not found",
                    content = @Content
            )
    })
    @DeleteMapping(value = "/{localizationId}")
    public Mono<ResponseEntity<Void>> deleteLocalization(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Unique identifier of the product",
                    required = true
            )
            @PathVariable Long productId,

            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Unique identifier of the localization record to delete",
                    required = true
            )
            @PathVariable Long localizationId
    ) {
        return service.deleteLocalization(productId, localizationId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
