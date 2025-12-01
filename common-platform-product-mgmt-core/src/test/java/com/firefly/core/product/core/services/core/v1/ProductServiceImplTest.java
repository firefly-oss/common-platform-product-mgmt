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


package com.firefly.core.product.core.services.core.v1;

import com.firefly.core.product.core.mappers.ProductMapper;
import com.firefly.core.product.core.services.impl.ProductServiceImpl;
import com.firefly.core.product.interfaces.dtos.ProductDTO;
import com.firefly.core.product.interfaces.enums.ProductStatusEnum;
import com.firefly.core.product.interfaces.enums.ProductTypeEnum;
import com.firefly.core.product.models.entities.Product;
import com.firefly.core.product.models.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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
    private final UUID PRODUCT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    private final UUID CATEGORY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
    private final UUID TENANT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();
        LocalDate launchDate = LocalDate.now();
        LocalDate endDate = launchDate.plusYears(1);

        product = new Product();
        product.setProductId(PRODUCT_ID);
        product.setTenantId(TENANT_ID);
        product.setProductCategoryId(CATEGORY_ID);
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
                .tenantId(TENANT_ID)
                .productCategoryId(CATEGORY_ID)
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

    // Note: filterProducts test is not included because it uses FilterUtils which is a static utility
    // that works directly with the database and cannot be easily mocked in unit tests.

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
                    throwable.getMessage().equals("Database error"))
                .verify();

        verify(mapper).toEntity(productDTO);
        verify(repository).save(product);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getProductById_Success() {
        // Arrange
        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.just(product));
        when(mapper.toDto(product)).thenReturn(productDTO);

        // Act & Assert
        StepVerifier.create(service.getProductById(PRODUCT_ID))
                .expectNext(productDTO)
                .verifyComplete();

        verify(repository).findById(PRODUCT_ID);
        verify(mapper).toDto(product);
    }

    @Test
    void getProductById_NotFound() {
        // Arrange
        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getProductById(PRODUCT_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Product not found with ID"))
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

        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.just(existingProduct));
        doNothing().when(mapper).updateEntityFromDto(productDTO, existingProduct);
        when(repository.save(existingProduct)).thenReturn(Mono.just(existingProduct));
        when(mapper.toDto(existingProduct)).thenReturn(productDTO);

        // Act & Assert
        StepVerifier.create(service.updateProduct(PRODUCT_ID, productDTO))
                .expectNext(productDTO)
                .verifyComplete();

        verify(repository).findById(PRODUCT_ID);
        verify(mapper).updateEntityFromDto(productDTO, existingProduct);
        verify(repository).save(existingProduct);
        verify(mapper).toDto(existingProduct);
    }

    @Test
    void updateProduct_NotFound() {
        // Arrange
        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateProduct(PRODUCT_ID, productDTO))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Product not found with ID"))
                .verify();

        verify(repository).findById(PRODUCT_ID);
        verify(mapper, never()).updateEntityFromDto(any(), any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void deleteProduct_Success() {
        // Arrange
        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.just(product));
        when(repository.deleteById(PRODUCT_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteProduct(PRODUCT_ID))
                .verifyComplete();

        verify(repository).findById(PRODUCT_ID);
        verify(repository).deleteById(PRODUCT_ID);
    }

    @Test
    void deleteProduct_NotFound() {
        // Arrange
        when(repository.findById(PRODUCT_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteProduct(PRODUCT_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Product not found with ID"))
                .verify();

        verify(repository).findById(PRODUCT_ID);
        verify(repository, never()).deleteById(any(UUID.class));
    }

    @Test
    void createProduct_MissingTenantId() {
        // Arrange
        ProductDTO dtoWithoutTenant = ProductDTO.builder()
                .productCategoryId(CATEGORY_ID)
                .productType(ProductTypeEnum.FINANCIAL)
                .productName("Test Product")
                .productCode("TP001")
                .productDescription("Test Description")
                .productStatus(ProductStatusEnum.ACTIVE)
                .build();

        // Act & Assert
        StepVerifier.create(service.createProduct(dtoWithoutTenant))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Tenant ID is required"))
                .verify();

        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
    }

    @Test
    void getProductsByTenantId_Success() {
        // Arrange
        when(repository.findByTenantId(TENANT_ID)).thenReturn(reactor.core.publisher.Flux.just(product));
        when(mapper.toDto(product)).thenReturn(productDTO);

        // Act & Assert
        StepVerifier.create(service.getProductsByTenantId(TENANT_ID))
                .expectNext(productDTO)
                .verifyComplete();

        verify(repository).findByTenantId(TENANT_ID);
        verify(mapper).toDto(product);
    }

    @Test
    void getProductsByTenantId_Empty() {
        // Arrange
        when(repository.findByTenantId(TENANT_ID)).thenReturn(reactor.core.publisher.Flux.empty());

        // Act & Assert
        StepVerifier.create(service.getProductsByTenantId(TENANT_ID))
                .verifyComplete();

        verify(repository).findByTenantId(TENANT_ID);
        verify(mapper, never()).toDto(any());
    }
}
