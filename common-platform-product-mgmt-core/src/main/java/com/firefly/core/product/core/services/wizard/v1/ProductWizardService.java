package com.firefly.core.product.core.services.wizard.v1;

import com.firefly.core.product.interfaces.dtos.wizard.v1.ProductWizardDTO;
import com.firefly.core.product.interfaces.dtos.core.v1.ProductDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service interface for the Product Wizard functionality.
 * This service provides methods to create and manage products through a step-by-step wizard process,
 * simplifying the product creation and configuration workflow.
 */
public interface ProductWizardService {

    /**
     * Initializes a new product wizard session.
     * 
     * @return A Mono emitting a new ProductWizardDTO with default values
     */
    Mono<ProductWizardDTO> initializeWizard();

    /**
     * Initializes a new product wizard session with a specified template.
     * The template pre-configures certain aspects of the product based on common patterns.
     * 
     * @param templateName The name of the template to use
     * @return A Mono emitting a new ProductWizardDTO pre-configured with template values
     */
    Mono<ProductWizardDTO> initializeWizardWithTemplate(String templateName);

    /**
     * Processes the current wizard step and advances to the next step.
     * 
     * @param wizardDTO The current state of the wizard
     * @return A Mono emitting the updated ProductWizardDTO with the next step
     */
    Mono<ProductWizardDTO> processStep(ProductWizardDTO wizardDTO);

    /**
     * Completes the wizard process and creates the product with all its related entities.
     * 
     * @param wizardDTO The final state of the wizard with all required information
     * @return A Mono emitting the created ProductDTO
     */
    Mono<ProductDTO> completeWizard(ProductWizardDTO wizardDTO);

    /**
     * Retrieves a wizard session by its ID to continue a previously started wizard.
     * 
     * @param wizardId The ID of the wizard session to retrieve
     * @return A Mono emitting the ProductWizardDTO for the specified ID
     */
    Mono<ProductWizardDTO> getWizardById(UUID wizardId);

    /**
     * Saves the current state of the wizard to resume later.
     * 
     * @param wizardDTO The current state of the wizard to save
     * @return A Mono emitting the saved ProductWizardDTO with its ID
     */
    Mono<ProductWizardDTO> saveWizardState(ProductWizardDTO wizardDTO);

    /**
     * Validates the current step of the wizard without advancing to the next step.
     * 
     * @param wizardDTO The current state of the wizard
     * @return A Mono emitting the updated ProductWizardDTO with validation results
     */
    Mono<ProductWizardDTO> validateCurrentStep(ProductWizardDTO wizardDTO);

    /**
     * Generates a preview of the product being created.
     * 
     * @param wizardDTO The current state of the wizard
     * @return A Mono emitting the updated ProductWizardDTO with preview product
     */
    Mono<ProductWizardDTO> generatePreview(ProductWizardDTO wizardDTO);

    /**
     * Initializes a wizard for bulk product creation.
     * 
     * @return A Mono emitting a new ProductWizardDTO configured for bulk operations
     */
    Mono<ProductWizardDTO> initializeBulkWizard();

    /**
     * Completes the wizard process for bulk product creation.
     * 
     * @param wizardDTO The final state of the wizard with all required information
     * @return A Flux emitting the created ProductDTOs
     */
    Flux<ProductDTO> completeBulkWizard(ProductWizardDTO wizardDTO);

    /**
     * Gets a list of all available templates.
     * 
     * @return A Flux emitting template names and their metadata
     */
    Flux<Map<String, Object>> getAvailableTemplates();

    /**
     * Gets detailed information about a specific template.
     * 
     * @param templateName The name of the template
     * @return A Mono emitting detailed template information
     */
    Mono<Map<String, Object>> getTemplateDetails(String templateName);

    /**
     * Updates the progress tracking information for a wizard.
     * 
     * @param wizardDTO The current state of the wizard
     * @return A Mono emitting the updated ProductWizardDTO with progress information
     */
    Mono<ProductWizardDTO> updateProgress(ProductWizardDTO wizardDTO);

    /**
     * Jumps to a specific step in the wizard.
     * 
     * @param wizardDTO The current state of the wizard
     * @param stepNumber The step number to jump to
     * @return A Mono emitting the updated ProductWizardDTO at the specified step
     */
    Mono<ProductWizardDTO> jumpToStep(ProductWizardDTO wizardDTO, Integer stepNumber);

    /**
     * Resets a wizard session to its initial state.
     * 
     * @param wizardId The ID of the wizard session to reset
     * @return A Mono emitting the reset ProductWizardDTO
     */
    Mono<ProductWizardDTO> resetWizard(UUID wizardId);
}
