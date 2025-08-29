package com.firefly.core.product.core.services.wizard.v1;

import com.firefly.core.product.interfaces.dtos.wizard.v1.ProductWizardDTO;
import com.firefly.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.firefly.core.product.interfaces.enums.core.v1.ProductStatusEnum;
import com.firefly.core.product.core.services.core.v1.ProductService;
import com.firefly.core.product.core.services.documentation.v1.ProductDocumentationRequirementService;
import com.firefly.core.product.core.services.feature.v1.ProductFeatureService;
import com.firefly.core.product.core.services.fee.v1.ProductFeeStructureService;
import com.firefly.core.product.core.services.pricing.v1.ProductPricingService;
import com.firefly.core.product.core.services.lifecycle.v1.ProductLifecycleService;
import com.firefly.core.product.core.services.localization.v1.ProductLocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ProductWizardService interface.
 * This service manages the product creation wizard workflow, providing a step-by-step
 * process for creating and configuring products with their related entities.
 */
@Service
public class ProductWizardServiceImpl implements ProductWizardService {

    private final ProductService productService;
    private final ProductFeatureService featureService;
    private final ProductFeeStructureService feeStructureService;
    private final ProductPricingService pricingService;
    private final ProductLifecycleService lifecycleService;
    private final ProductLocalizationService localizationService;
    private final ProductDocumentationRequirementService documentationRequirementService;

    // In-memory storage for wizard sessions (in a real application, this would be persisted to a database)
    private final Map<Long, ProductWizardDTO> wizardSessions = new HashMap<>();
    private Long nextWizardId = 1L;

    // Predefined templates for common product configurations
    private final Map<String, ProductWizardDTO> templates = new HashMap<>();

    @Autowired
    public ProductWizardServiceImpl(
            ProductService productService,
            ProductFeatureService featureService,
            ProductFeeStructureService feeStructureService,
            ProductPricingService pricingService,
            ProductLifecycleService lifecycleService,
            ProductLocalizationService localizationService,
            ProductDocumentationRequirementService documentationRequirementService) {
        this.productService = productService;
        this.featureService = featureService;
        this.feeStructureService = feeStructureService;
        this.pricingService = pricingService;
        this.lifecycleService = lifecycleService;
        this.localizationService = localizationService;
        this.documentationRequirementService = documentationRequirementService;

        // Initialize predefined templates
        initializeTemplates();
    }

    /**
     * Initialize predefined templates for common product configurations.
     */
    private void initializeTemplates() {
        // Example: Basic product template
        ProductWizardDTO basicTemplate = new ProductWizardDTO();
        ProductDTO basicProduct = new ProductDTO();
        basicProduct.setProductStatus(ProductStatusEnum.PROPOSED);
        basicTemplate.setProduct(basicProduct);
        basicTemplate.setFeatures(new ArrayList<>());
        basicTemplate.setFeeStructures(new ArrayList<>());
        basicTemplate.setPricing(new ArrayList<>());
        basicTemplate.setDocumentationRequirements(new ArrayList<>());
        basicTemplate.setWizardTemplate("basic");
        templates.put("basic", basicTemplate);

        // Example: Premium product template
        ProductWizardDTO premiumTemplate = new ProductWizardDTO();
        ProductDTO premiumProduct = new ProductDTO();
        premiumProduct.setProductStatus(ProductStatusEnum.PROPOSED);
        premiumTemplate.setProduct(premiumProduct);
        premiumTemplate.setFeatures(new ArrayList<>());
        premiumTemplate.setFeeStructures(new ArrayList<>());
        premiumTemplate.setPricing(new ArrayList<>());
        premiumTemplate.setDocumentationRequirements(new ArrayList<>());
        premiumTemplate.setWizardTemplate("premium");
        templates.put("premium", premiumTemplate);
    }

    @Override
    public Mono<ProductWizardDTO> initializeWizard() {
        ProductWizardDTO wizardDTO = new ProductWizardDTO();
        wizardDTO.setProduct(new ProductDTO());
        wizardDTO.setFeatures(new ArrayList<>());
        wizardDTO.setFeeStructures(new ArrayList<>());
        wizardDTO.setPricing(new ArrayList<>());
        wizardDTO.setLifecycle(new ArrayList<>());
        wizardDTO.setLocalizations(new ArrayList<>());
        wizardDTO.setDocumentationRequirements(new ArrayList<>());
        wizardDTO.setCurrentStep(1);
        wizardDTO.setCompleted(false);

        return saveWizardState(wizardDTO);
    }

    @Override
    public Mono<ProductWizardDTO> initializeWizardWithTemplate(String templateName) {
        if (!templates.containsKey(templateName)) {
            return Mono.error(new IllegalArgumentException("Template not found: " + templateName));
        }

        // Create a deep copy of the template
        ProductWizardDTO wizardDTO = templates.get(templateName);
        ProductWizardDTO newWizard = new ProductWizardDTO();
        newWizard.setProduct(wizardDTO.getProduct());
        newWizard.setFeatures(wizardDTO.getFeatures());
        newWizard.setFeeStructures(wizardDTO.getFeeStructures());
        newWizard.setPricing(wizardDTO.getPricing());
        newWizard.setLifecycle(wizardDTO.getLifecycle());
        newWizard.setLocalizations(wizardDTO.getLocalizations());
        newWizard.setDocumentationRequirements(wizardDTO.getDocumentationRequirements());
        newWizard.setWizardTemplate(templateName);
        newWizard.setCurrentStep(1);
        newWizard.setCompleted(false);

        return saveWizardState(newWizard);
    }

    @Override
    public Mono<ProductWizardDTO> processStep(ProductWizardDTO wizardDTO) {
        // Validate the current step
        if (wizardDTO.getCompleted()) {
            return Mono.error(new IllegalStateException("Wizard is already completed"));
        }

        // Process the current step based on step number
        switch (wizardDTO.getCurrentStep()) {
            case 1:
                // Step 1: Basic product information
                if (wizardDTO.getProduct() == null) {
                    return Mono.error(new IllegalArgumentException("Product information is required"));
                }
                // Validate basic product information
                if (wizardDTO.getProduct().getProductName() == null || wizardDTO.getProduct().getProductName().isEmpty()) {
                    return Mono.error(new IllegalArgumentException("Product name is required"));
                }
                // Advance to next step
                wizardDTO.setCurrentStep(2);
                break;

            case 2:
                // Step 2: Product features
                // Features are optional, so just advance to next step
                wizardDTO.setCurrentStep(3);
                break;

            case 3:
                // Step 3: Fee structures
                // Fee structures are optional, so just advance to next step
                wizardDTO.setCurrentStep(4);
                break;

            case 4:
                // Step 4: Pricing
                // Pricing is optional, so just advance to next step
                wizardDTO.setCurrentStep(5);
                break;

            case 5:
                // Step 5: Lifecycle
                // Lifecycle is optional, so just advance to next step
                wizardDTO.setCurrentStep(6);
                break;

            case 6:
                // Step 6: Localization
                // Localization is optional, so just advance to next step
                wizardDTO.setCurrentStep(7);
                break;

            case 7:
                // Step 7: Documentation Requirements
                // Documentation requirements are optional, so mark as completed
                wizardDTO.setCompleted(true);
                break;

            default:
                return Mono.error(new IllegalStateException("Invalid step: " + wizardDTO.getCurrentStep()));
        }

        // Save the updated wizard state
        return saveWizardState(wizardDTO);
    }

    @Override
    public Mono<ProductDTO> completeWizard(ProductWizardDTO wizardDTO) {
        if (!wizardDTO.getCompleted()) {
            return Mono.error(new IllegalStateException("Wizard is not completed yet"));
        }

        // Create the product
        return productService.createProduct(wizardDTO.getProduct())
            .flatMap(createdProduct -> {
                Long productId = createdProduct.getProductId();

                // Create features if any
                Mono<Void> featuresCreation = Mono.empty();
                if (wizardDTO.getFeatures() != null && !wizardDTO.getFeatures().isEmpty()) {
                    featuresCreation = Mono.when(
                        wizardDTO.getFeatures().stream()
                            .map(feature -> {
                                feature.setProductId(productId);
                                return featureService.createFeature(productId, feature);
                            })
                            .toArray(Mono[]::new)
                    ).then();
                }

                // Create fee structures if any
                Mono<Void> feeStructuresCreation = Mono.empty();
                if (wizardDTO.getFeeStructures() != null && !wizardDTO.getFeeStructures().isEmpty()) {
                    feeStructuresCreation = Mono.when(
                        wizardDTO.getFeeStructures().stream()
                            .map(feeStructure -> {
                                feeStructure.setProductId(productId);
                                return feeStructureService.createFeeStructure(productId, feeStructure);
                            })
                            .toArray(Mono[]::new)
                    ).then();
                }

                // Create pricing if any
                Mono<Void> pricingCreation = Mono.empty();
                if (wizardDTO.getPricing() != null && !wizardDTO.getPricing().isEmpty()) {
                    pricingCreation = Mono.when(
                        wizardDTO.getPricing().stream()
                            .map(pricing -> {
                                pricing.setProductId(productId);
                                return pricingService.createPricing(productId, pricing);
                            })
                            .toArray(Mono[]::new)
                    ).then();
                }

                // Create lifecycle if any
                Mono<Void> lifecycleCreation = Mono.empty();
                if (wizardDTO.getLifecycle() != null && !wizardDTO.getLifecycle().isEmpty()) {
                    lifecycleCreation = Mono.when(
                        wizardDTO.getLifecycle().stream()
                            .map(lifecycle -> {
                                lifecycle.setProductId(productId);
                                return lifecycleService.createProductLifecycle(productId, lifecycle);
                            })
                            .toArray(Mono[]::new)
                    ).then();
                }

                // Create localizations if any
                Mono<Void> localizationsCreation = Mono.empty();
                if (wizardDTO.getLocalizations() != null && !wizardDTO.getLocalizations().isEmpty()) {
                    localizationsCreation = Mono.when(
                        wizardDTO.getLocalizations().stream()
                            .map(localization -> {
                                localization.setProductId(productId);
                                return localizationService.createLocalization(productId, localization);
                            })
                            .toArray(Mono[]::new)
                    ).then();
                }

                // Create documentation requirements if any
                Mono<Void> documentationRequirementsCreation = Mono.empty();
                if (wizardDTO.getDocumentationRequirements() != null && !wizardDTO.getDocumentationRequirements().isEmpty()) {
                    documentationRequirementsCreation = Mono.when(
                        wizardDTO.getDocumentationRequirements().stream()
                            .map(requirement -> {
                                requirement.setProductId(productId);
                                return documentationRequirementService.createDocumentationRequirement(productId, requirement);
                            })
                            .toArray(Mono[]::new)
                    ).then();
                }

                // Wait for all creations to complete and return the created product
                return Mono.when(
                    featuresCreation,
                    feeStructuresCreation,
                    pricingCreation,
                    lifecycleCreation,
                    localizationsCreation,
                    documentationRequirementsCreation
                ).thenReturn(createdProduct);
            });
    }

    @Override
    public Mono<ProductWizardDTO> getWizardById(Long wizardId) {
        if (!wizardSessions.containsKey(wizardId)) {
            return Mono.error(new IllegalArgumentException("Wizard session not found: " + wizardId));
        }
        return Mono.just(wizardSessions.get(wizardId));
    }

    @Override
    public Mono<ProductWizardDTO> saveWizardState(ProductWizardDTO wizardDTO) {
        // Assign an ID if this is a new wizard session
        if (wizardDTO.getId() == null) {
            wizardDTO.setId(nextWizardId++);
        }

        // Update last updated timestamp
        wizardDTO.setLastUpdated(LocalDateTime.now());

        // Save the wizard session
        wizardSessions.put(wizardDTO.getId(), wizardDTO);

        return Mono.just(wizardDTO);
    }

    @Override
    public Mono<ProductWizardDTO> validateCurrentStep(ProductWizardDTO wizardDTO) {
        Map<String, List<String>> validationErrors = new HashMap<>();
        boolean isValid = true;

        // Validate based on current step
        switch (wizardDTO.getCurrentStep()) {
            case 1:
                // Step 1: Basic product information
                if (wizardDTO.getProduct() == null) {
                    List<String> errors = new ArrayList<>();
                    errors.add("Product information is required");
                    validationErrors.put("product", errors);
                    isValid = false;
                } else {
                    // Validate product name
                    if (wizardDTO.getProduct().getProductName() == null || wizardDTO.getProduct().getProductName().isEmpty()) {
                        List<String> errors = new ArrayList<>();
                        errors.add("Product name is required");
                        validationErrors.put("product.productName", errors);
                        isValid = false;
                    }

                    // Validate product code
                    if (wizardDTO.getProduct().getProductCode() == null || wizardDTO.getProduct().getProductCode().isEmpty()) {
                        List<String> errors = new ArrayList<>();
                        errors.add("Product code is required");
                        validationErrors.put("product.productCode", errors);
                        isValid = false;
                    }

                    // Validate product type
                    if (wizardDTO.getProduct().getProductType() == null) {
                        List<String> errors = new ArrayList<>();
                        errors.add("Product type is required");
                        validationErrors.put("product.productType", errors);
                        isValid = false;
                    }
                }
                break;

            case 2:
                // Step 2: Product features
                // Features are optional, but if provided, validate them
                if (wizardDTO.getFeatures() != null && !wizardDTO.getFeatures().isEmpty()) {
                    for (int i = 0; i < wizardDTO.getFeatures().size(); i++) {
                        if (wizardDTO.getFeatures().get(i).getFeatureName() == null || 
                            wizardDTO.getFeatures().get(i).getFeatureName().isEmpty()) {
                            List<String> errors = new ArrayList<>();
                            errors.add("Feature name is required");
                            validationErrors.put("features[" + i + "].featureName", errors);
                            isValid = false;
                        }
                    }
                }
                break;

            case 3:
                // Step 3: Fee structures
                // Fee structures are optional, validation logic would go here
                break;

            case 4:
                // Step 4: Pricing
                // Pricing is optional, but if provided, validate them
                if (wizardDTO.getPricing() != null && !wizardDTO.getPricing().isEmpty()) {
                    for (int i = 0; i < wizardDTO.getPricing().size(); i++) {
                        if (wizardDTO.getPricing().get(i).getAmountValue() == null) {
                            List<String> errors = new ArrayList<>();
                            errors.add("Amount value is required");
                            validationErrors.put("pricing[" + i + "].amountValue", errors);
                            isValid = false;
                        }
                    }
                }
                break;

            case 5:
                // Step 5: Lifecycle
                // Lifecycle validation logic would go here
                break;

            case 6:
                // Step 6: Localization
                // Localization validation logic would go here
                break;

            case 7:
                // Step 7: Documentation Requirements
                // Documentation requirements are optional, but if provided, validate them
                if (wizardDTO.getDocumentationRequirements() != null && !wizardDTO.getDocumentationRequirements().isEmpty()) {
                    for (int i = 0; i < wizardDTO.getDocumentationRequirements().size(); i++) {
                        if (wizardDTO.getDocumentationRequirements().get(i).getDocType() == null) {
                            List<String> errors = new ArrayList<>();
                            errors.add("Document type is required");
                            validationErrors.put("documentationRequirements[" + i + "].docType", errors);
                            isValid = false;
                        }
                    }
                }
                break;

            default:
                List<String> errors = new ArrayList<>();
                errors.add("Invalid step: " + wizardDTO.getCurrentStep());
                validationErrors.put("currentStep", errors);
                isValid = false;
        }

        // Update validation status
        wizardDTO.setValidationErrors(validationErrors);
        wizardDTO.setIsCurrentStepValid(isValid);

        return Mono.just(wizardDTO);
    }

    @Override
    public Mono<ProductWizardDTO> generatePreview(ProductWizardDTO wizardDTO) {
        // Create a preview of the product being created
        wizardDTO.setPreviewProduct(wizardDTO.getProduct());
        wizardDTO.setPreviewMode(true);

        return Mono.just(wizardDTO);
    }

    @Override
    public Mono<ProductWizardDTO> initializeBulkWizard() {
        ProductWizardDTO wizardDTO = new ProductWizardDTO();
        wizardDTO.setProduct(new ProductDTO()); // Template product
        wizardDTO.setBulkProducts(new ArrayList<>());
        wizardDTO.setBulkMode(true);
        wizardDTO.setCurrentStep(1);
        wizardDTO.setCompleted(false);

        return saveWizardState(wizardDTO);
    }

    @Override
    public Flux<ProductDTO> completeBulkWizard(ProductWizardDTO wizardDTO) {
        if (!wizardDTO.getBulkMode()) {
            return Flux.error(new IllegalStateException("Wizard is not in bulk mode"));
        }

        if (wizardDTO.getBulkProducts() == null || wizardDTO.getBulkProducts().isEmpty()) {
            return Flux.error(new IllegalArgumentException("No products to create in bulk"));
        }

        // Create all products in the bulk list
        return Flux.fromIterable(wizardDTO.getBulkProducts())
            .flatMap(productDTO -> productService.createProduct(productDTO));
    }

    @Override
    public Flux<Map<String, Object>> getAvailableTemplates() {
        return Flux.fromIterable(templates.entrySet())
            .map(entry -> {
                Map<String, Object> templateInfo = new HashMap<>();
                templateInfo.put("name", entry.getKey());
                templateInfo.put("description", "Template for " + entry.getKey() + " products");
                return templateInfo;
            });
    }

    @Override
    public Mono<Map<String, Object>> getTemplateDetails(String templateName) {
        if (!templates.containsKey(templateName)) {
            return Mono.error(new IllegalArgumentException("Template not found: " + templateName));
        }

        Map<String, Object> templateDetails = new HashMap<>();
        templateDetails.put("name", templateName);
        templateDetails.put("description", "Template for " + templateName + " products");
        templateDetails.put("steps", templates.get(templateName).getTotalSteps());

        // Add more template details as needed

        return Mono.just(templateDetails);
    }

    @Override
    public Mono<ProductWizardDTO> updateProgress(ProductWizardDTO wizardDTO) {
        // Calculate progress percentage
        if (wizardDTO.getTotalSteps() > 0) {
            double progressPercentage = (double) (wizardDTO.getCurrentStep() - 1) / wizardDTO.getTotalSteps() * 100.0;
            wizardDTO.setProgressPercentage(progressPercentage);
        }

        return Mono.just(wizardDTO);
    }

    @Override
    public Mono<ProductWizardDTO> jumpToStep(ProductWizardDTO wizardDTO, Integer stepNumber) {
        if (stepNumber < 1 || stepNumber > wizardDTO.getTotalSteps()) {
            return Mono.error(new IllegalArgumentException("Invalid step number: " + stepNumber));
        }

        wizardDTO.setCurrentStep(stepNumber);

        // Update progress
        return updateProgress(wizardDTO);
    }

    @Override
    public Mono<ProductWizardDTO> resetWizard(Long wizardId) {
        if (!wizardSessions.containsKey(wizardId)) {
            return Mono.error(new IllegalArgumentException("Wizard session not found: " + wizardId));
        }

        ProductWizardDTO wizardDTO = wizardSessions.get(wizardId);
        String templateName = wizardDTO.getWizardTemplate();

        // If wizard was created from a template, reset to that template
        if (templateName != null && !templateName.isEmpty() && templates.containsKey(templateName)) {
            return initializeWizardWithTemplate(templateName)
                .flatMap(newWizard -> {
                    newWizard.setId(wizardId);
                    return saveWizardState(newWizard);
                });
        }

        // Otherwise, reset to a new wizard
        return initializeWizard()
            .flatMap(newWizard -> {
                newWizard.setId(wizardId);
                return saveWizardState(newWizard);
            });
    }
}
