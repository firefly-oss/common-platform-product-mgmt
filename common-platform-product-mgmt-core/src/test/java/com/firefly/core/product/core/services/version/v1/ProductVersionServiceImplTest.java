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


package com.firefly.core.product.core.services.version.v1;

import com.firefly.core.product.core.mappers.ProductVersionMapper;
import com.firefly.core.product.core.services.impl.ProductVersionServiceImpl;
import com.firefly.core.product.interfaces.dtos.ProductVersionDTO;
import com.firefly.core.product.models.entities.ProductVersion;
import com.firefly.core.product.models.repositories.ProductVersionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductVersionServiceImplTest {

    @Mock
    private ProductVersionRepository repository;

    @Mock
    private ProductVersionMapper mapper;

    @InjectMocks
    private ProductVersionServiceImpl service;

    private ProductVersion version;
    private ProductVersionDTO versionDTO;
    private final UUID PRODUCT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    private final UUID VERSION_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
    private final Long VERSION_NUMBER = 1L;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();

        version = new ProductVersion();
        version.setProductVersionId(VERSION_ID);
        version.setProductId(PRODUCT_ID);
        version.setVersionNumber(VERSION_NUMBER);
        version.setVersionDescription("Initial version");
        version.setEffectiveDate(now);
        version.setDateCreated(now);
        version.setDateUpdated(now);

        versionDTO = ProductVersionDTO.builder()
                .productVersionId(VERSION_ID)
                .productId(PRODUCT_ID)
                .versionNumber(VERSION_NUMBER)
                .versionDescription("Initial version")
                .effectiveDate(now)
                .dateCreated(now)
                .dateUpdated(now)
                .build();
    }

    // Note: filterProductVersions test is not included because it uses FilterUtils which is a static utility
    // that works directly with the database and cannot be easily mocked in unit tests.

    @Test
    void createProductVersion_Success() {
        // Arrange
        ProductVersionDTO requestDTO = ProductVersionDTO.builder()
                .versionNumber(VERSION_NUMBER)
                .versionDescription("Initial version")
                .effectiveDate(LocalDateTime.now())
                .build();

        when(mapper.toEntity(requestDTO)).thenReturn(version);
        when(repository.save(version)).thenReturn(Mono.just(version));
        when(mapper.toDto(version)).thenReturn(versionDTO);

        // Act & Assert
        StepVerifier.create(service.createProductVersion(PRODUCT_ID, requestDTO))
                .expectNext(versionDTO)
                .verifyComplete();

        // Verify interactions
        verify(mapper).toEntity(requestDTO);
        verify(repository).save(version);
        verify(mapper).toDto(version);
    }

    @Test
    void createProductVersion_Error() {
        // Arrange
        ProductVersionDTO requestDTO = ProductVersionDTO.builder()
                .versionNumber(VERSION_NUMBER)
                .versionDescription("Initial version")
                .effectiveDate(LocalDateTime.now())
                .build();

        when(mapper.toEntity(requestDTO)).thenReturn(version);
        when(repository.save(version)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(service.createProductVersion(PRODUCT_ID, requestDTO))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().equals("Database error"))
                .verify();

        // Verify interactions
        verify(mapper).toEntity(requestDTO);
        verify(repository).save(version);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getProductVersionById_Success() {
        // Arrange
        when(repository.findById(VERSION_ID)).thenReturn(Mono.just(version));
        when(mapper.toDto(version)).thenReturn(versionDTO);

        // Act & Assert
        StepVerifier.create(service.getProductVersionById(PRODUCT_ID, VERSION_ID))
                .expectNext(versionDTO)
                .verifyComplete();

        // Verify interactions
        verify(repository).findById(VERSION_ID);
        verify(mapper).toDto(version);
    }

    @Test
    void getProductVersionById_NotFound() {
        // Arrange
        when(repository.findById(VERSION_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getProductVersionById(PRODUCT_ID, VERSION_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Product version not found with ID"))
                .verify();

        // Verify interactions
        verify(repository).findById(VERSION_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getProductVersionById_WrongProduct() {
        // Arrange
        ProductVersion versionFromDifferentProduct = new ProductVersion();
        versionFromDifferentProduct.setProductVersionId(VERSION_ID);
        versionFromDifferentProduct.setProductId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different product ID

        when(repository.findById(VERSION_ID)).thenReturn(Mono.just(versionFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.getProductVersionById(PRODUCT_ID, VERSION_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("does not belong to product"))
                .verify();

        // Verify interactions
        verify(repository).findById(VERSION_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateProductVersion_Success() {
        // Arrange
        ProductVersionDTO updateRequest = ProductVersionDTO.builder()
                .versionNumber(2L)
                .versionDescription("Updated version")
                .effectiveDate(LocalDateTime.now())
                .build();

        when(repository.findById(VERSION_ID)).thenReturn(Mono.just(version));
        doNothing().when(mapper).updateEntityFromDto(updateRequest, version);
        when(repository.save(version)).thenReturn(Mono.just(version));
        when(mapper.toDto(version)).thenReturn(updateRequest);

        // Act & Assert
        StepVerifier.create(service.updateProductVersion(PRODUCT_ID, VERSION_ID, updateRequest))
                .expectNext(updateRequest)
                .verifyComplete();

        // Verify interactions
        verify(repository).findById(VERSION_ID);
        verify(mapper).updateEntityFromDto(updateRequest, version);
        verify(repository).save(version);
        verify(mapper).toDto(version);
    }

    @Test
    void updateProductVersion_NotFound() {
        // Arrange
        ProductVersionDTO updateRequest = ProductVersionDTO.builder()
                .versionNumber(2L)
                .versionDescription("Updated version")
                .effectiveDate(LocalDateTime.now())
                .build();

        when(repository.findById(VERSION_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateProductVersion(PRODUCT_ID, VERSION_ID, updateRequest))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Product version not found with ID"))
                .verify();

        // Verify interactions
        verify(repository).findById(VERSION_ID);
        verify(mapper, never()).updateEntityFromDto(any(), any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateProductVersion_WrongProduct() {
        // Arrange
        ProductVersionDTO updateRequest = ProductVersionDTO.builder()
                .versionNumber(2L)
                .versionDescription("Updated version")
                .effectiveDate(LocalDateTime.now())
                .build();

        ProductVersion versionFromDifferentProduct = new ProductVersion();
        versionFromDifferentProduct.setProductVersionId(VERSION_ID);
        versionFromDifferentProduct.setProductId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different product ID

        when(repository.findById(VERSION_ID)).thenReturn(Mono.just(versionFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.updateProductVersion(PRODUCT_ID, VERSION_ID, updateRequest))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("does not belong to product"))
                .verify();

        // Verify interactions
        verify(repository).findById(VERSION_ID);
        verify(mapper, never()).updateEntityFromDto(any(), any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void deleteProductVersion_Success() {
        // Arrange
        when(repository.findById(VERSION_ID)).thenReturn(Mono.just(version));
        when(repository.deleteById(VERSION_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteProductVersion(PRODUCT_ID, VERSION_ID))
                .verifyComplete();

        // Verify interactions
        verify(repository).findById(VERSION_ID);
        verify(repository).deleteById(VERSION_ID);
    }

    @Test
    void deleteProductVersion_NotFound() {
        // Arrange
        when(repository.findById(VERSION_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteProductVersion(PRODUCT_ID, VERSION_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Product version not found with ID"))
                .verify();

        // Verify interactions
        verify(repository).findById(VERSION_ID);
        verify(repository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deleteProductVersion_WrongProduct() {
        // Arrange
        ProductVersion versionFromDifferentProduct = new ProductVersion();
        versionFromDifferentProduct.setProductVersionId(VERSION_ID);
        versionFromDifferentProduct.setProductId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different product ID

        when(repository.findById(VERSION_ID)).thenReturn(Mono.just(versionFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.deleteProductVersion(PRODUCT_ID, VERSION_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("does not belong to product"))
                .verify();

        // Verify interactions
        verify(repository).findById(VERSION_ID);
        verify(repository, never()).deleteById(any(UUID.class));
    }
}
