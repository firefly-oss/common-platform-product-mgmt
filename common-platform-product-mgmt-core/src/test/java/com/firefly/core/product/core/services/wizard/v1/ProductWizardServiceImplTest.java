/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.product.core.services.wizard.v1;

import com.firefly.core.product.core.services.core.v1.ProductService;
import com.firefly.core.product.core.services.feature.v1.ProductFeatureService;
import com.firefly.core.product.core.services.fee.v1.ProductFeeStructureService;
import com.firefly.core.product.core.services.pricing.v1.ProductPricingService;
import com.firefly.core.product.core.services.lifecycle.v1.ProductLifecycleService;
import com.firefly.core.product.core.services.localization.v1.ProductLocalizationService;
import com.firefly.core.product.core.services.documentation.v1.ProductDocumentationRequirementService;
import com.firefly.core.product.interfaces.dtos.wizard.v1.ProductWizardDTO;
import com.firefly.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.firefly.core.product.interfaces.enums.core.v1.ProductStatusEnum;
import com.firefly.core.product.interfaces.enums.core.v1.ProductTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ProductWizardServiceImpl class.
 */
public class ProductWizardServiceImplTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductFeatureService featureService;

    @Mock
    private ProductFeeStructureService feeStructureService;

    @Mock
    private ProductPricingService pricingService;

    @Mock
    private ProductLifecycleService lifecycleService;

    @Mock
    private ProductLocalizationService localizationService;

    @Mock
    private ProductDocumentationRequirementService documentationRequirementService;

    private ProductWizardServiceImpl wizardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        wizardService = new ProductWizardServiceImpl(
                productService,
                featureService,
                feeStructureService,
                pricingService,
                lifecycleService,
                localizationService,
                documentationRequirementService
        );
    }

    @Test
    public void testInitializeWizard() {
        // Test initializing a new wizard
        StepVerifier.create(wizardService.initializeWizard())
                .expectNextMatches(wizard -> 
                    wizard.getCurrentStep() == 1 &&
                    !wizard.getCompleted() &&
                    wizard.getProduct() != null
                )
                .verifyComplete();
    }

    @Test
    public void testInitializeWizardWithTemplate() {
        // Test initializing a wizard with a template
        StepVerifier.create(wizardService.initializeWizardWithTemplate("basic"))
                .expectNextMatches(wizard -> 
                    wizard.getCurrentStep() == 1 &&
                    !wizard.getCompleted() &&
                    wizard.getProduct() != null &&
                    "basic".equals(wizard.getWizardTemplate())
                )
                .verifyComplete();
    }

    @Test
    public void testProcessStep() {
        // Create a test wizard DTO
        ProductWizardDTO wizardDTO = new ProductWizardDTO();
        wizardDTO.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        wizardDTO.setCurrentStep(1);
        wizardDTO.setCompleted(false);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Test Product");
        productDTO.setProductType(ProductTypeEnum.FINANCIAL);
        wizardDTO.setProduct(productDTO);

        // Test processing a step
        StepVerifier.create(wizardService.processStep(wizardDTO))
                .expectNextMatches(wizard -> 
                    wizard.getCurrentStep() == 2 &&
                    !wizard.getCompleted()
                )
                .verifyComplete();
    }

    @Test
    public void testCompleteWizard() {
        // Create a completed wizard DTO
        ProductWizardDTO wizardDTO = new ProductWizardDTO();
        wizardDTO.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        wizardDTO.setCurrentStep(6);
        wizardDTO.setCompleted(true);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Test Product");
        productDTO.setProductType(ProductTypeEnum.FINANCIAL);
        productDTO.setProductStatus(ProductStatusEnum.DRAFT);
        wizardDTO.setProduct(productDTO);

        // Mock the product service to return a created product
        ProductDTO createdProductDTO = new ProductDTO();
        createdProductDTO.setProductId(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        createdProductDTO.setProductName("Test Product");
        createdProductDTO.setProductType(ProductTypeEnum.FINANCIAL);
        createdProductDTO.setProductStatus(ProductStatusEnum.DRAFT);

        when(productService.createProduct(any(ProductDTO.class))).thenReturn(Mono.just(createdProductDTO));

        // Test completing the wizard
        StepVerifier.create(wizardService.completeWizard(wizardDTO))
                .expectNextMatches(product ->
                    UUID.fromString("550e8400-e29b-41d4-a716-446655440001").equals(product.getProductId()) &&
                    "Test Product".equals(product.getProductName()) &&
                    product.getProductType() == ProductTypeEnum.FINANCIAL &&
                    product.getProductStatus() == ProductStatusEnum.DRAFT
                )
                .verifyComplete();
    }

    @Test
    public void testGetWizardById() {
        // Initialize a wizard to create a session and get the actual wizard ID
        ProductWizardDTO initializedWizard = wizardService.initializeWizard().block();
        UUID wizardId = initializedWizard.getId();

        // Test retrieving the wizard by ID
        StepVerifier.create(wizardService.getWizardById(wizardId))
                .expectNextMatches(wizard ->
                    wizardId.equals(wizard.getId()) &&
                    wizard.getCurrentStep() == 1 &&
                    !wizard.getCompleted()
                )
                .verifyComplete();
    }

    @Test
    public void testSaveWizardState() {
        // Create a test wizard DTO
        ProductWizardDTO wizardDTO = new ProductWizardDTO();
        wizardDTO.setCurrentStep(3);
        wizardDTO.setCompleted(false);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Test Product");
        wizardDTO.setProduct(productDTO);

        // Test saving the wizard state
        StepVerifier.create(wizardService.saveWizardState(wizardDTO))
                .expectNextMatches(wizard -> 
                    wizard.getId() != null &&
                    wizard.getCurrentStep() == 3 &&
                    !wizard.getCompleted() &&
                    "Test Product".equals(wizard.getProduct().getProductName())
                )
                .verifyComplete();
    }
}
