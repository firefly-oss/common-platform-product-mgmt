package com.catalis.core.product.core.services.core.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.core.v1.ProductMapper;
import com.catalis.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.catalis.core.product.interfaces.enums.core.v1.ProductStatusEnum;
import com.catalis.core.product.interfaces.enums.core.v1.ProductTypeEnum;
import com.catalis.core.product.models.entities.core.v1.Product;
import com.catalis.core.product.models.repositories.product.v1.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl service;

    private Product product;
    private ProductDTO productDTO;
    private final Long PRODUCT_ID = 1L;
    private final Long SUBTYPE_ID = 2L;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        LocalDate launchDate = LocalDate.now();
        LocalDate endDate = launchDate.plusYears(1);

        product = new Product();
        product.setProductId(PRODUCT_ID);
        product.setProductSubtypeId(SUBTYPE_ID);
        product.setProductType(ProductTypeEnum.FINANCIAL);
        product.setProductName("Test Product");
        product.setProductCode("TP001");
        product.setProductDescription("Test Description");
        product.setProductStatus(ProductStatusEnum.ACTIVE);
        product.setLaunchDate(launchDate);
        product.setEndDate(endDate);
        product.setDateCreated(now);
        product.setDateUpdated(now);

        productDTO = ProductDTO.builder()
                .productId(PRODUCT_ID)
                .productSubtypeId(SUBTYPE_ID)
                .productType(ProductTypeEnum.FINANCIAL)
                .productName("Test Product")
                .productCode("TP001")
                .productDescription("Test Description")
                .productStatus(ProductStatusEnum.ACTIVE)
                .launchDate(launchDate)
                .endDate(endDate)
                .dateCreated(now)
                .dateUpdated(now)
                .build();
    }

    @Test
    void getAllProducts_Success() {
        // Arrange
        // Mock PaginationRequest
        PaginationRequest paginationRequest = Mockito.mock(PaginationRequest.class);

        // Mock Pageable
        Pageable pageable = Mockito.mock(Pageable.class);

        // Mock PaginationRequest behavior
        doReturn(pageable).when(paginationRequest).toPageable();

        // Set up repository and mapper mocks
        when(repository.findAllBy(pageable)).thenReturn(Flux.just(product));
        when(repository.count()).thenReturn(Mono.just(1L));
        when(mapper.toDto(product)).thenReturn(productDTO);

        // Act & Assert
        StepVerifier.create(service.getAllProducts(paginationRequest))
                .expectNextMatches(response -> {
                    // Verify response contains our DTO
                    List<ProductDTO> content = response.getContent();
                    return content != null && 
                           content.size() == 1 && 
                           content.get(0).equals(productDTO);
                })
                .verifyComplete();

        // Verify interactions
        verify(repository).findAllBy(pageable);
        verify(repository).count();
        verify(mapper).toDto(product);
    }

    @Test
    void createProduct_Success() {
        // Arrange
        when(mapper.toEntity(productDTO)).thenReturn(product);
        when(repository.save(product)).thenReturn(Mono.just(product));
        when(mapper.toDto(product)).thenReturn(productDTO);

        // Act & Assert
        StepVerifier.create(service.createProduct(productDTO))
                .expectNext(productDTO)
                .verifyComplete();

        verify(mapper).toEntity(productDTO);
        verify(repository).save(product);
        verify(mapper).toDto(product);
    }

    @Test
    void createProduct_Error() {
        // Arrange
        when(mapper.toEntity(productDTO)).thenReturn(product);
        when(repository.save(product)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(service.createProduct(productDTO))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().equals("Failed to create product"))
                .verify();

        verify(mapper).toEntity(productDTO);
        verify(repository).save(product);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getProduct_Success() {
        // Arrange
        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.just(product));
        when(mapper.toDto(product)).thenReturn(productDTO);

        // Act & Assert
        StepVerifier.create(service.getProduct(PRODUCT_ID))
                .expectNext(productDTO)
                .verifyComplete();

        verify(repository).findById(PRODUCT_ID);
        verify(mapper).toDto(product);
    }

    @Test
    void getProduct_NotFound() {
        // Arrange
        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getProduct(PRODUCT_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().contains("Failed to retrieve product"))
                .verify();

        verify(repository).findById(PRODUCT_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateProduct_Success() {
        // Arrange
        Product existingProduct = new Product();
        existingProduct.setProductId(PRODUCT_ID);
        existingProduct.setProductName("Old Name");
        existingProduct.setProductDescription("Old Description");
        LocalDateTime createdDate = LocalDateTime.now().minusDays(1);
        existingProduct.setDateCreated(createdDate);

        // Create a spy of the product that will be returned by mapper.toEntity
        Product spyProduct = spy(new Product());

        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.just(existingProduct));
        when(mapper.toEntity(productDTO)).thenReturn(spyProduct);
        when(repository.save(spyProduct)).thenReturn(Mono.just(spyProduct));
        when(mapper.toDto(spyProduct)).thenReturn(productDTO);

        // Act & Assert
        StepVerifier.create(service.updateProduct(PRODUCT_ID, productDTO))
                .expectNext(productDTO)
                .verifyComplete();

        verify(repository).findById(PRODUCT_ID);
        verify(mapper).toEntity(productDTO);
        verify(repository).save(spyProduct);
        verify(mapper).toDto(spyProduct);

        // Verify that the ID and dateCreated were preserved
        verify(spyProduct).setProductId(PRODUCT_ID);
        verify(spyProduct).setDateCreated(createdDate);
    }

    @Test
    void updateProduct_NotFound() {
        // Arrange
        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateProduct(PRODUCT_ID, productDTO))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().contains("Failed to update product"))
                .verify();

        verify(repository).findById(PRODUCT_ID);
        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void deleteProduct_Success() {
        // Arrange
        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.just(product));
        when(repository.delete(product)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteProduct(PRODUCT_ID))
                .verifyComplete();

        verify(repository).findById(PRODUCT_ID);
        verify(repository).delete(product);
    }

    @Test
    void deleteProduct_Error() {
        // Arrange
        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.just(product));
        when(repository.delete(product)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(service.deleteProduct(PRODUCT_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().equals("Failed to delete product"))
                .verify();

        verify(repository).findById(PRODUCT_ID);
        verify(repository).delete(product);
    }
}
