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


package com.firefly.core.product.core.services.documentation.v1;

import com.firefly.core.product.core.mappers.ProductDocumentationRequirementMapper;
import com.firefly.core.product.core.services.impl.ProductDocumentationRequirementServiceImpl;
import com.firefly.core.product.interfaces.dtos.ProductDocumentationRequirementDTO;
import com.firefly.core.product.interfaces.enums.ContractingDocTypeEnum;
import com.firefly.core.product.models.entities.ProductDocumentationRequirement;
import com.firefly.core.product.models.repositories.ProductDocumentationRequirementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductDocumentationRequirementServiceImplTest {

    @Mock
    private ProductDocumentationRequirementRepository repository;

    @Mock
    private ProductDocumentationRequirementMapper mapper;

    @InjectMocks
    private ProductDocumentationRequirementServiceImpl service;

    private ProductDocumentationRequirement entity;
    private ProductDocumentationRequirementDTO dto;
    private final UUID productId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    private final UUID requirementId = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");

    @BeforeEach
    void setUp() {
        // Setup test data
        entity = new ProductDocumentationRequirement();
        entity.setProductDocRequirementId(requirementId);
        entity.setProductId(productId);
        entity.setDocType(ContractingDocTypeEnum.IDENTIFICATION);
        entity.setIsMandatory(true);
        entity.setDescription("Identification document");

        dto = new ProductDocumentationRequirementDTO();
        dto.setProductDocRequirementId(requirementId);
        dto.setProductId(productId);
        dto.setDocType(ContractingDocTypeEnum.IDENTIFICATION);
        dto.setIsMandatory(true);
        dto.setDescription("Identification document");
    }

    // Note: filterDocumentationRequirements test is not included because it uses FilterUtils which is a static utility
    // that works directly with the database and cannot be easily mocked in unit tests.

    @Test
    void createDocumentationRequirement_ShouldCreateAndReturnDTO() {
        // Arrange
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        // Act & Assert
        StepVerifier.create(service.createDocumentationRequirement(productId, dto))
                .expectNext(dto)
                .verifyComplete();
    }

    @Test
    void getDocumentationRequirementById_ShouldReturnDTO() {
        // Arrange
        when(repository.findById(requirementId)).thenReturn(Mono.just(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        // Act & Assert
        StepVerifier.create(service.getDocumentationRequirementById(productId, requirementId))
                .expectNext(dto)
                .verifyComplete();
    }

    @Test
    void getDocumentationRequirementByType_ShouldReturnDTO() {
        // Arrange
        ContractingDocTypeEnum docType = ContractingDocTypeEnum.IDENTIFICATION;
        when(repository.findByProductIdAndDocType(productId, docType)).thenReturn(Mono.just(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        // Act & Assert
        StepVerifier.create(service.getDocumentationRequirementByType(productId, docType))
                .expectNext(dto)
                .verifyComplete();
    }

    @Test
    void updateDocumentationRequirement_ShouldUpdateAndReturnDTO() {
        // Arrange
        when(repository.findById(requirementId)).thenReturn(Mono.just(entity));
        doNothing().when(mapper).updateEntityFromDto(dto, entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        // Act & Assert
        StepVerifier.create(service.updateDocumentationRequirement(productId, requirementId, dto))
                .expectNext(dto)
                .verifyComplete();
    }

    @Test
    void deleteDocumentationRequirement_ShouldDeleteAndReturnVoid() {
        // Arrange
        when(repository.findById(requirementId)).thenReturn(Mono.just(entity));
        when(repository.deleteById(requirementId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDocumentationRequirement(productId, requirementId))
                .verifyComplete();
    }

    @Test
    void filterMandatoryDocumentationRequirements_ShouldReturnMandatoryRequirements() {
        // Arrange
        when(repository.findByProductIdAndIsMandatory(productId, true))
                .thenReturn(Flux.just(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        // Act & Assert
        StepVerifier.create(service.filterMandatoryDocumentationRequirements(productId))
                .expectNext(dto)
                .verifyComplete();
    }
}
