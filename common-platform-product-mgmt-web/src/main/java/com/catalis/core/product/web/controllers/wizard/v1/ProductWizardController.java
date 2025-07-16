package com.catalis.core.product.web.controllers.wizard.v1;

import com.catalis.core.product.core.services.wizard.v1.ProductWizardServiceImpl;
import com.catalis.core.product.interfaces.dtos.wizard.v1.ProductWizardDTO;
import com.catalis.core.product.interfaces.dtos.core.v1.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.Map;

/**
 * REST controller for the Product Wizard functionality.
 * This controller provides endpoints to create and manage products through a step-by-step wizard process,
 * simplifying the product creation and configuration workflow.
 */
@RestController
@Tag(name = "Product Wizard", description = "APIs for creating products through a step-by-step wizard process")
@RequestMapping("/api/v1/product-wizard")
public class ProductWizardController {

    private final ProductWizardServiceImpl service;

    @Autowired
    public ProductWizardController(ProductWizardServiceImpl service) {
        this.service = service;
    }

    @Operation(
        summary = "Initialize Wizard",
        description = "Start a new product creation wizard with default settings."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Wizard initialized successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductWizardDTO.class))
    )
    @PostMapping("/initialize")
    public Mono<ResponseEntity<ProductWizardDTO>> initializeWizard() {
        return service.initializeWizard()
            .map(ResponseEntity::ok);
    }

    @Operation(
        summary = "Initialize Wizard with Template",
        description = "Start a new product creation wizard using a predefined template."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Wizard initialized with template successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductWizardDTO.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Template not found",
        content = @Content
    )
    @PostMapping("/initialize/{templateName}")
    public Mono<ResponseEntity<ProductWizardDTO>> initializeWizardWithTemplate(
        @Parameter(description = "Name of the template to use", required = true)
        @PathVariable String templateName
    ) {
        return service.initializeWizardWithTemplate(templateName)
            .map(ResponseEntity::ok)
            .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @Operation(
        summary = "Process Wizard Step",
        description = "Process the current step of the wizard and advance to the next step."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Step processed successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductWizardDTO.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Invalid step data or wizard state",
        content = @Content
    )
    @PostMapping("/process-step")
    public Mono<ResponseEntity<ProductWizardDTO>> processStep(
        @RequestBody ProductWizardDTO wizardDTO
    ) {
        return service.processStep(wizardDTO)
            .map(ResponseEntity::ok)
            .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @Operation(
        summary = "Complete Wizard",
        description = "Complete the wizard process and create the product with all its related entities."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Product created successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
    )
    @ApiResponse(
        responseCode = "400",
        description = "Wizard not completed or invalid data",
        content = @Content
    )
    @PostMapping("/complete")
    public Mono<ResponseEntity<ProductDTO>> completeWizard(
        @RequestBody ProductWizardDTO wizardDTO
    ) {
        return service.completeWizard(wizardDTO)
            .map(ResponseEntity::ok)
            .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @Operation(
        summary = "Get Wizard by ID",
        description = "Retrieve a wizard session by its ID to continue a previously started wizard."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Wizard retrieved successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductWizardDTO.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Wizard not found",
        content = @Content
    )
    @GetMapping("/{wizardId}")
    public Mono<ResponseEntity<ProductWizardDTO>> getWizardById(
        @Parameter(description = "ID of the wizard to retrieve", required = true)
        @PathVariable Long wizardId
    ) {
        return service.getWizardById(wizardId)
            .map(ResponseEntity::ok)
            .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(
        summary = "Save Wizard State",
        description = "Save the current state of the wizard to resume later."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Wizard state saved successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductWizardDTO.class))
    )
    @PostMapping("/save")
    public Mono<ResponseEntity<ProductWizardDTO>> saveWizardState(
        @RequestBody ProductWizardDTO wizardDTO
    ) {
        return service.saveWizardState(wizardDTO)
            .map(ResponseEntity::ok);
    }
}
