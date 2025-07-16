package com.catalis.core.product.core.services.wizard.v1;

import com.catalis.core.product.core.services.core.v1.ProductService;
import com.catalis.core.product.core.services.feature.v1.ProductFeatureService;
import com.catalis.core.product.core.services.fee.v1.ProductFeeStructureService;
import com.catalis.core.product.core.services.pricing.v1.ProductPricingService;
import com.catalis.core.product.core.services.lifecycle.v1.ProductLifecycleService;
import com.catalis.core.product.core.services.localization.v1.ProductLocalizationService;
import com.catalis.core.product.core.services.documentation.v1.ProductDocumentationRequirementService;
import com.catalis.core.product.interfaces.dtos.wizard.v1.ProductWizardDTO;
import com.catalis.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.catalis.core.product.interfaces.enums.core.v1.ProductStatusEnum;
import com.catalis.core.product.interfaces.enums.core.v1.ProductTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
        wizardDTO.setId(1L);
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
        wizardDTO.setId(1L);
        wizardDTO.setCurrentStep(6);
        wizardDTO.setCompleted(true);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Test Product");
        productDTO.setProductType(ProductTypeEnum.FINANCIAL);
        productDTO.setProductStatus(ProductStatusEnum.DRAFT);
        wizardDTO.setProduct(productDTO);

        // Mock the product service to return a created product
        ProductDTO createdProductDTO = new ProductDTO();
        createdProductDTO.setProductId(1L);
        createdProductDTO.setProductName("Test Product");
        createdProductDTO.setProductType(ProductTypeEnum.FINANCIAL);
        createdProductDTO.setProductStatus(ProductStatusEnum.DRAFT);

        when(productService.createProduct(any(ProductDTO.class))).thenReturn(Mono.just(createdProductDTO));

        // Test completing the wizard
        StepVerifier.create(wizardService.completeWizard(wizardDTO))
                .expectNextMatches(product -> 
                    product.getProductId() == 1L &&
                    "Test Product".equals(product.getProductName()) &&
                    product.getProductType() == ProductTypeEnum.FINANCIAL &&
                    product.getProductStatus() == ProductStatusEnum.DRAFT
                )
                .verifyComplete();
    }

    @Test
    public void testGetWizardById() {
        // Initialize a wizard to create a session
        wizardService.initializeWizard().block();

        // Test retrieving the wizard by ID
        StepVerifier.create(wizardService.getWizardById(1L))
                .expectNextMatches(wizard -> 
                    wizard.getId() == 1L &&
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
