package com.catalis.core.product.interfaces.dtos.wizard.v1;

import com.catalis.core.product.interfaces.dtos.BaseDTO;
import com.catalis.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.catalis.core.product.interfaces.dtos.documentation.v1.ProductDocumentationRequirementDTO;
import com.catalis.core.product.interfaces.dtos.feature.v1.ProductFeatureDTO;
import com.catalis.core.product.interfaces.dtos.fee.v1.ProductFeeStructureDTO;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingDTO;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import com.catalis.core.product.interfaces.dtos.localization.v1.ProductLocalizationDTO;
import com.catalis.core.product.interfaces.enums.core.v1.ProductTypeEnum;
import com.catalis.core.product.interfaces.enums.core.v1.ProductStatusEnum;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for the Product Wizard functionality.
 * This DTO encapsulates all the information needed to create a product
 * through the wizard interface, including core product details and
 * optional related entities like features, pricing, etc.
 */
public class ProductWizardDTO extends BaseDTO {

    // Wizard ID
    private Long id;

    // Core product information
    private ProductDTO product;

    // Optional related entities
    private List<ProductFeatureDTO> features;
    private List<ProductFeeStructureDTO> feeStructures;
    private List<ProductPricingDTO> pricing;
    private List<ProductLifecycleDTO> lifecycle;
    private List<ProductLocalizationDTO> localizations;
    private List<ProductDocumentationRequirementDTO> documentationRequirements;

    // Wizard-specific fields
    private String wizardTemplate; // Optional template name for pre-configured products
    private Integer currentStep;   // Track wizard progress
    private Integer totalSteps;    // Total number of steps in the wizard
    private Double progressPercentage; // Progress percentage (0-100)
    private Boolean completed;     // Flag to indicate if wizard is complete

    // Validation and error handling
    private Map<String, List<String>> validationErrors; // Map of field names to error messages
    private Boolean isCurrentStepValid; // Flag to indicate if the current step is valid

    // Preview functionality
    private ProductDTO previewProduct; // Preview of the product being created
    private Boolean previewMode; // Flag to indicate if the wizard is in preview mode

    // Template details
    private Map<String, Object> templateMetadata; // Additional metadata for the template

    // Bulk operations
    private List<ProductDTO> bulkProducts; // List of products for bulk operations
    private Boolean bulkMode; // Flag to indicate if the wizard is in bulk mode

    // Timestamps for tracking
    private LocalDateTime lastUpdated; // When the wizard was last updated
    private LocalDateTime expiresAt; // When the wizard session expires

    // Constructors
    public ProductWizardDTO() {
        this.completed = false;
        this.currentStep = 1;
        this.totalSteps = 7; // Default to 7 steps (added documentation requirements)
        this.progressPercentage = 0.0;
        this.isCurrentStepValid = false;
        this.validationErrors = new HashMap<>();
        this.previewMode = false;
        this.bulkMode = false;
        this.lastUpdated = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusHours(24); // Default 24-hour expiration
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public List<ProductFeatureDTO> getFeatures() {
        return features;
    }

    public void setFeatures(List<ProductFeatureDTO> features) {
        this.features = features;
    }

    public List<ProductFeeStructureDTO> getFeeStructures() {
        return feeStructures;
    }

    public void setFeeStructures(List<ProductFeeStructureDTO> feeStructures) {
        this.feeStructures = feeStructures;
    }

    public List<ProductPricingDTO> getPricing() {
        return pricing;
    }

    public void setPricing(List<ProductPricingDTO> pricing) {
        this.pricing = pricing;
    }

    public List<ProductLifecycleDTO> getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(List<ProductLifecycleDTO> lifecycle) {
        this.lifecycle = lifecycle;
    }

    public List<ProductLocalizationDTO> getLocalizations() {
        return localizations;
    }

    public void setLocalizations(List<ProductLocalizationDTO> localizations) {
        this.localizations = localizations;
    }

    public List<ProductDocumentationRequirementDTO> getDocumentationRequirements() {
        return documentationRequirements;
    }

    public void setDocumentationRequirements(List<ProductDocumentationRequirementDTO> documentationRequirements) {
        this.documentationRequirements = documentationRequirements;
    }

    public String getWizardTemplate() {
        return wizardTemplate;
    }

    public void setWizardTemplate(String wizardTemplate) {
        this.wizardTemplate = wizardTemplate;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(Integer totalSteps) {
        this.totalSteps = totalSteps;
    }

    public Double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public Map<String, List<String>> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, List<String>> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Boolean getIsCurrentStepValid() {
        return isCurrentStepValid;
    }

    public void setIsCurrentStepValid(Boolean isCurrentStepValid) {
        this.isCurrentStepValid = isCurrentStepValid;
    }

    public ProductDTO getPreviewProduct() {
        return previewProduct;
    }

    public void setPreviewProduct(ProductDTO previewProduct) {
        this.previewProduct = previewProduct;
    }

    public Boolean getPreviewMode() {
        return previewMode;
    }

    public void setPreviewMode(Boolean previewMode) {
        this.previewMode = previewMode;
    }

    public Map<String, Object> getTemplateMetadata() {
        return templateMetadata;
    }

    public void setTemplateMetadata(Map<String, Object> templateMetadata) {
        this.templateMetadata = templateMetadata;
    }

    public List<ProductDTO> getBulkProducts() {
        return bulkProducts;
    }

    public void setBulkProducts(List<ProductDTO> bulkProducts) {
        this.bulkProducts = bulkProducts;
    }

    public Boolean getBulkMode() {
        return bulkMode;
    }

    public void setBulkMode(Boolean bulkMode) {
        this.bulkMode = bulkMode;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
