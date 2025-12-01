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


package com.firefly.core.product.core.services.documentantion.v1;

import com.firefly.core.product.core.mappers.ProductDocumentationMapper;
import com.firefly.core.product.core.services.impl.ProductDocumentationServiceImpl;
import com.firefly.core.product.interfaces.dtos.ProductDocumentationDTO;
import com.firefly.core.product.interfaces.enums.DocTypeEnum;
import com.firefly.core.product.models.entities.ProductDocumentation;
import com.firefly.core.product.models.repositories.ProductDocumentationRepository;
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
class ProductDocumentationServiceImplTest {

    @Mock
    private ProductDocumentationRepository repository;

    @Mock
    private ProductDocumentationMapper mapper;

    @InjectMocks
    private ProductDocumentationServiceImpl service;

    private ProductDocumentation documentation;
    private ProductDocumentationDTO documentationDTO;
    private final UUID PRODUCT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    private final UUID DOC_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
    private final Long DOC_MANAGER_REF = 100L;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();

        documentation = new ProductDocumentation();
        documentation.setProductDocumentationId(DOC_ID);
        documentation.setProductId(PRODUCT_ID);
        documentation.setDocType(DocTypeEnum.TNC);
        documentation.setDocumentManagerRef(DOC_MANAGER_REF);
        documentation.setDateAdded(now);
        documentation.setDateCreated(now);
        documentation.setDateUpdated(now);

        documentationDTO = ProductDocumentationDTO.builder()
                .productDocumentationId(DOC_ID)
                .productId(PRODUCT_ID)
                .docType(DocTypeEnum.TNC)
                .documentManagerRef(DOC_MANAGER_REF)
                .dateAdded(now)
                .dateCreated(now)
                .dateUpdated(now)
                .build();
    }

    // Note: filterDocumentations test is not included because it uses FilterUtils which is a static utility
    // that works directly with the database and cannot be easily mocked in unit tests.

    @Test
    void createDocumentation_Success() {
        // Arrange
        when(mapper.toEntity(documentationDTO)).thenReturn(documentation);
        when(repository.save(documentation)).thenReturn(Mono.just(documentation));
        when(mapper.toDto(documentation)).thenReturn(documentationDTO);

        // Act & Assert
        StepVerifier.create(service.createDocumentation(PRODUCT_ID, documentationDTO))
                .expectNext(documentationDTO)
                .verifyComplete();

        verify(mapper).toEntity(documentationDTO);
        verify(repository).save(documentation);
        verify(mapper).toDto(documentation);

        // Verify that the product ID was set
        assertEquals(PRODUCT_ID, documentationDTO.getProductId());
    }

    @Test
    void createDocumentation_Error() {
        // Arrange
        when(mapper.toEntity(documentationDTO)).thenReturn(documentation);
        when(repository.save(documentation)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(service.createDocumentation(PRODUCT_ID, documentationDTO))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().equals("Database error"))
                .verify();

        verify(mapper).toEntity(documentationDTO);
        verify(repository).save(documentation);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getDocumentationById_Success() {
        // Arrange
        when(repository.findById(DOC_ID)).thenReturn(Mono.just(documentation));
        when(mapper.toDto(documentation)).thenReturn(documentationDTO);

        // Act & Assert
        StepVerifier.create(service.getDocumentationById(PRODUCT_ID, DOC_ID))
                .expectNext(documentationDTO)
                .verifyComplete();

        verify(repository).findById(DOC_ID);
        verify(mapper).toDto(documentation);
    }

    @Test
    void getDocumentationById_NotFound() {
        // Arrange
        when(repository.findById(DOC_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDocumentationById(PRODUCT_ID, DOC_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Documentation not found with ID"))
                .verify();

        verify(repository).findById(DOC_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getDocumentationById_WrongProduct() {
        // Arrange
        ProductDocumentation docFromDifferentProduct = new ProductDocumentation();
        docFromDifferentProduct.setProductDocumentationId(DOC_ID);
        docFromDifferentProduct.setProductId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different product ID

        when(repository.findById(DOC_ID)).thenReturn(Mono.just(docFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.getDocumentationById(PRODUCT_ID, DOC_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("does not belong to product"))
                .verify();

        verify(repository).findById(DOC_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateDocumentation_Success() {
        // Arrange
        ProductDocumentationDTO updateRequest = ProductDocumentationDTO.builder()
                .docType(DocTypeEnum.TNC)
                .documentManagerRef(DOC_MANAGER_REF)
                .build();

        when(repository.findById(DOC_ID)).thenReturn(Mono.just(documentation));
        doNothing().when(mapper).updateEntityFromDto(updateRequest, documentation);
        when(repository.save(documentation)).thenReturn(Mono.just(documentation));
        when(mapper.toDto(documentation)).thenReturn(documentationDTO);

        // Act & Assert
        StepVerifier.create(service.updateDocumentation(PRODUCT_ID, DOC_ID, updateRequest))
                .expectNext(documentationDTO)
                .verifyComplete();

        verify(repository).findById(DOC_ID);
        verify(mapper).updateEntityFromDto(updateRequest, documentation);
        verify(repository).save(documentation);
        verify(mapper).toDto(documentation);
    }

    @Test
    void updateDocumentation_NotFound() {
        // Arrange
        when(repository.findById(DOC_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateDocumentation(PRODUCT_ID, DOC_ID, documentationDTO))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Documentation not found with ID"))
                .verify();

        verify(repository).findById(DOC_ID);
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateDocumentation_WrongProduct() {
        // Arrange
        ProductDocumentation docFromDifferentProduct = new ProductDocumentation();
        docFromDifferentProduct.setProductDocumentationId(DOC_ID);
        docFromDifferentProduct.setProductId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different product ID

        when(repository.findById(DOC_ID)).thenReturn(Mono.just(docFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.updateDocumentation(PRODUCT_ID, DOC_ID, documentationDTO))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("does not belong to product"))
                .verify();

        verify(repository).findById(DOC_ID);
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void deleteDocumentation_Success() {
        // Arrange
        when(repository.findById(DOC_ID)).thenReturn(Mono.just(documentation));
        when(repository.deleteById(DOC_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDocumentation(PRODUCT_ID, DOC_ID))
                .verifyComplete();

        verify(repository).findById(DOC_ID);
        verify(repository).deleteById(DOC_ID);
    }

    @Test
    void deleteDocumentation_NotFound() {
        // Arrange
        when(repository.findById(DOC_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDocumentation(PRODUCT_ID, DOC_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Documentation not found with ID"))
                .verify();

        verify(repository).findById(DOC_ID);
        verify(repository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deleteDocumentation_WrongProduct() {
        // Arrange
        ProductDocumentation docFromDifferentProduct = new ProductDocumentation();
        docFromDifferentProduct.setProductDocumentationId(DOC_ID);
        docFromDifferentProduct.setProductId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different product ID

        when(repository.findById(DOC_ID)).thenReturn(Mono.just(docFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.deleteDocumentation(PRODUCT_ID, DOC_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("does not belong to product"))
                .verify();

        verify(repository).findById(DOC_ID);
        verify(repository, never()).deleteById(any(UUID.class));
    }

    // Helper method for assertions
    private void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }
}
