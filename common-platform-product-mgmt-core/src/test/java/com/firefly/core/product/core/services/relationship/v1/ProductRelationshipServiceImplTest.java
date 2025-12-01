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


package com.firefly.core.product.core.services.relationship.v1;

import com.firefly.core.product.core.mappers.ProductRelationshipMapper;
import com.firefly.core.product.core.services.impl.ProductRelationshipServiceImpl;
import com.firefly.core.product.interfaces.dtos.ProductRelationshipDTO;
import com.firefly.core.product.interfaces.enums.RelationshipTypeEnum;
import com.firefly.core.product.models.entities.ProductRelationship;
import com.firefly.core.product.models.repositories.ProductRelationshipRepository;
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
class ProductRelationshipServiceImplTest {

    @Mock
    private ProductRelationshipRepository repository;

    @Mock
    private ProductRelationshipMapper mapper;

    @InjectMocks
    private ProductRelationshipServiceImpl service;

    private ProductRelationship relationship;
    private ProductRelationshipDTO relationshipDTO;
    private final UUID PRODUCT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    private final UUID RELATIONSHIP_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
    private final UUID RELATED_PRODUCT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();

        relationship = new ProductRelationship();
        relationship.setProductRelationshipId(RELATIONSHIP_ID);
        relationship.setProductId(PRODUCT_ID);
        relationship.setRelatedProductId(RELATED_PRODUCT_ID);
        relationship.setRelationshipType(RelationshipTypeEnum.COMPLIMENTARY);
        relationship.setDescription("Complimentary product relationship");
        relationship.setDateCreated(now);
        relationship.setDateUpdated(now);

        relationshipDTO = ProductRelationshipDTO.builder()
                .productRelationshipId(RELATIONSHIP_ID)
                .productId(PRODUCT_ID)
                .relatedProductId(RELATED_PRODUCT_ID)
                .relationshipType(RelationshipTypeEnum.COMPLIMENTARY)
                .description("Complimentary product relationship")
                .dateCreated(now)
                .dateUpdated(now)
                .build();
    }

    // Note: filterRelationships test is not included because it uses FilterUtils which is a static utility
    // that works directly with the database and cannot be easily mocked in unit tests.

    @Test
    void createRelationship_Success() {
        // Arrange
        ProductRelationshipDTO requestDTO = ProductRelationshipDTO.builder()
                .relatedProductId(RELATED_PRODUCT_ID)
                .relationshipType(RelationshipTypeEnum.COMPLIMENTARY)
                .description("Complimentary product relationship")
                .build();

        when(mapper.toEntity(requestDTO)).thenReturn(relationship);
        when(repository.save(relationship)).thenReturn(Mono.just(relationship));
        when(mapper.toDto(relationship)).thenReturn(relationshipDTO);

        // Act & Assert
        StepVerifier.create(service.createRelationship(PRODUCT_ID, requestDTO))
                .expectNext(relationshipDTO)
                .verifyComplete();

        // Verify interactions
        verify(mapper).toEntity(requestDTO);
        verify(repository).save(relationship);
        verify(mapper).toDto(relationship);
    }

    @Test
    void createRelationship_Error() {
        // Arrange
        ProductRelationshipDTO requestDTO = ProductRelationshipDTO.builder()
                .relatedProductId(RELATED_PRODUCT_ID)
                .relationshipType(RelationshipTypeEnum.COMPLIMENTARY)
                .description("Complimentary product relationship")
                .build();

        when(mapper.toEntity(requestDTO)).thenReturn(relationship);
        when(repository.save(relationship)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(service.createRelationship(PRODUCT_ID, requestDTO))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().equals("Database error"))
                .verify();

        // Verify interactions
        verify(mapper).toEntity(requestDTO);
        verify(repository).save(relationship);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getRelationshipById_Success() {
        // Arrange
        when(repository.findById(RELATIONSHIP_ID)).thenReturn(Mono.just(relationship));
        when(mapper.toDto(relationship)).thenReturn(relationshipDTO);

        // Act & Assert
        StepVerifier.create(service.getRelationshipById(PRODUCT_ID, RELATIONSHIP_ID))
                .expectNext(relationshipDTO)
                .verifyComplete();

        // Verify interactions
        verify(repository).findById(RELATIONSHIP_ID);
        verify(mapper).toDto(relationship);
    }

    @Test
    void getRelationshipById_NotFound() {
        // Arrange
        when(repository.findById(RELATIONSHIP_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getRelationshipById(PRODUCT_ID, RELATIONSHIP_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Relationship not found with ID"))
                .verify();

        // Verify interactions
        verify(repository).findById(RELATIONSHIP_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getRelationshipById_WrongProduct() {
        // Arrange
        ProductRelationship relationshipFromDifferentProduct = new ProductRelationship();
        relationshipFromDifferentProduct.setProductRelationshipId(RELATIONSHIP_ID);
        relationshipFromDifferentProduct.setProductId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different product ID

        when(repository.findById(RELATIONSHIP_ID)).thenReturn(Mono.just(relationshipFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.getRelationshipById(PRODUCT_ID, RELATIONSHIP_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("does not belong to product"))
                .verify();

        // Verify interactions
        verify(repository).findById(RELATIONSHIP_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateRelationship_Success() {
        // Arrange
        ProductRelationshipDTO updateRequest = ProductRelationshipDTO.builder()
                .relatedProductId(RELATED_PRODUCT_ID)
                .relationshipType(RelationshipTypeEnum.UPGRADE)
                .description("Upgrade product relationship")
                .build();

        when(repository.findById(RELATIONSHIP_ID)).thenReturn(Mono.just(relationship));
        doNothing().when(mapper).updateEntityFromDto(updateRequest, relationship);
        when(repository.save(relationship)).thenReturn(Mono.just(relationship));
        when(mapper.toDto(relationship)).thenReturn(updateRequest);

        // Act & Assert
        StepVerifier.create(service.updateRelationship(PRODUCT_ID, RELATIONSHIP_ID, updateRequest))
                .expectNext(updateRequest)
                .verifyComplete();

        // Verify interactions
        verify(repository).findById(RELATIONSHIP_ID);
        verify(mapper).updateEntityFromDto(updateRequest, relationship);
        verify(repository).save(relationship);
        verify(mapper).toDto(relationship);
    }

    @Test
    void updateRelationship_NotFound() {
        // Arrange
        ProductRelationshipDTO updateRequest = ProductRelationshipDTO.builder()
                .relatedProductId(RELATED_PRODUCT_ID)
                .relationshipType(RelationshipTypeEnum.UPGRADE)
                .description("Upgrade product relationship")
                .build();

        when(repository.findById(RELATIONSHIP_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateRelationship(PRODUCT_ID, RELATIONSHIP_ID, updateRequest))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Relationship not found with ID"))
                .verify();

        // Verify interactions
        verify(repository).findById(RELATIONSHIP_ID);
        verify(mapper, never()).updateEntityFromDto(any(), any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateRelationship_WrongProduct() {
        // Arrange
        ProductRelationshipDTO updateRequest = ProductRelationshipDTO.builder()
                .relatedProductId(RELATED_PRODUCT_ID)
                .relationshipType(RelationshipTypeEnum.UPGRADE)
                .description("Upgrade product relationship")
                .build();

        ProductRelationship relationshipFromDifferentProduct = new ProductRelationship();
        relationshipFromDifferentProduct.setProductRelationshipId(RELATIONSHIP_ID);
        relationshipFromDifferentProduct.setProductId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different product ID

        when(repository.findById(RELATIONSHIP_ID)).thenReturn(Mono.just(relationshipFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.updateRelationship(PRODUCT_ID, RELATIONSHIP_ID, updateRequest))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("does not belong to product"))
                .verify();

        // Verify interactions
        verify(repository).findById(RELATIONSHIP_ID);
        verify(mapper, never()).updateEntityFromDto(any(), any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void deleteRelationship_Success() {
        // Arrange
        when(repository.findById(RELATIONSHIP_ID)).thenReturn(Mono.just(relationship));
        when(repository.deleteById(RELATIONSHIP_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteRelationship(PRODUCT_ID, RELATIONSHIP_ID))
                .verifyComplete();

        // Verify interactions
        verify(repository).findById(RELATIONSHIP_ID);
        verify(repository).deleteById(RELATIONSHIP_ID);
    }

    @Test
    void deleteRelationship_NotFound() {
        // Arrange
        when(repository.findById(RELATIONSHIP_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteRelationship(PRODUCT_ID, RELATIONSHIP_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("Relationship not found with ID"))
                .verify();

        // Verify interactions
        verify(repository).findById(RELATIONSHIP_ID);
        verify(repository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deleteRelationship_WrongProduct() {
        // Arrange
        ProductRelationship relationshipFromDifferentProduct = new ProductRelationship();
        relationshipFromDifferentProduct.setProductRelationshipId(RELATIONSHIP_ID);
        relationshipFromDifferentProduct.setProductId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different product ID

        when(repository.findById(RELATIONSHIP_ID)).thenReturn(Mono.just(relationshipFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.deleteRelationship(PRODUCT_ID, RELATIONSHIP_ID))
                .expectErrorMatches(throwable ->
                    throwable instanceof RuntimeException &&
                    throwable.getMessage().contains("does not belong to product"))
                .verify();

        // Verify interactions
        verify(repository).findById(RELATIONSHIP_ID);
        verify(repository, never()).deleteById(any(UUID.class));
    }
}