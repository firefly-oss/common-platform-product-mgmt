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


package com.firefly.core.product.core.services.category.v1;

import com.firefly.core.product.core.mappers.category.v1.ProductCategoryMapper;
import com.firefly.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
import com.firefly.core.product.models.entities.category.v1.ProductCategory;
import com.firefly.core.product.models.repositories.category.v1.ProductCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceImplTest {

    @Mock
    private ProductCategoryRepository repository;

    @Mock
    private ProductCategoryMapper mapper;

    @InjectMocks
    private ProductCategoryServiceImpl service;

    private ProductCategory productCategory;
    private ProductCategoryDTO productCategoryDTO;
    private final UUID CATEGORY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    private final UUID PARENT_CATEGORY_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();

        productCategory = new ProductCategory();
        productCategory.setProductCategoryId(CATEGORY_ID);
        productCategory.setCategoryName("Test Category");
        productCategory.setCategoryDescription("Test Description");
        productCategory.setParentCategoryId(PARENT_CATEGORY_ID);
        productCategory.setDateCreated(now);
        productCategory.setDateUpdated(now);

        productCategoryDTO = ProductCategoryDTO.builder()
                .productCategoryId(CATEGORY_ID)
                .categoryName("Test Category")
                .categoryDescription("Test Description")
                .parentCategoryId(PARENT_CATEGORY_ID)
                .dateCreated(now)
                .dateUpdated(now)
                .build();
    }

    @Test
    void getById_Success() {
        // Arrange
        when(repository.findById(CATEGORY_ID)).thenReturn(Mono.just(productCategory));
        when(mapper.toDto(productCategory)).thenReturn(productCategoryDTO);

        // Act & Assert
        StepVerifier.create(service.getById(CATEGORY_ID))
                .expectNext(productCategoryDTO)
                .verifyComplete();

        verify(repository).findById(CATEGORY_ID);
        verify(mapper).toDto(productCategory);
    }

    @Test
    void getById_NotFound() {
        // Arrange
        when(repository.findById(CATEGORY_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getById(CATEGORY_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException && 
                    throwable.getMessage().equals("Product category not found for the given ID"))
                .verify();

        verify(repository).findById(CATEGORY_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void create_Success() {
        // Arrange
        when(mapper.toEntity(productCategoryDTO)).thenReturn(productCategory);
        when(repository.save(productCategory)).thenReturn(Mono.just(productCategory));
        when(mapper.toDto(productCategory)).thenReturn(productCategoryDTO);

        // Act & Assert
        StepVerifier.create(service.create(productCategoryDTO))
                .expectNext(productCategoryDTO)
                .verifyComplete();

        verify(mapper).toEntity(productCategoryDTO);
        verify(repository).save(productCategory);
        verify(mapper).toDto(productCategory);
    }

    @Test
    void create_Error() {
        // Arrange
        when(mapper.toEntity(productCategoryDTO)).thenReturn(productCategory);
        when(repository.save(productCategory)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(service.create(productCategoryDTO))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().equals("Failed to create product category"))
                .verify();

        verify(mapper).toEntity(productCategoryDTO);
        verify(repository).save(productCategory);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void update_Success() {
        // Arrange
        ProductCategory existingCategory = spy(new ProductCategory());
        existingCategory.setProductCategoryId(CATEGORY_ID);
        existingCategory.setCategoryName("Old Name");
        existingCategory.setCategoryDescription("Old Description");
        existingCategory.setParentCategoryId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different parent ID

        when(repository.findById(CATEGORY_ID)).thenReturn(Mono.just(existingCategory));
        when(repository.save(existingCategory)).thenReturn(Mono.just(existingCategory));
        when(mapper.toDto(existingCategory)).thenReturn(productCategoryDTO);

        // Act & Assert
        StepVerifier.create(service.update(CATEGORY_ID, productCategoryDTO))
                .expectNext(productCategoryDTO)
                .verifyComplete();

        verify(repository).findById(CATEGORY_ID);
        verify(repository).save(existingCategory);
        verify(mapper).toDto(existingCategory);

        // Verify that the fields were updated
        verify(existingCategory).setCategoryName(productCategoryDTO.getCategoryName());
        verify(existingCategory).setCategoryDescription(productCategoryDTO.getCategoryDescription());
        verify(existingCategory).setParentCategoryId(productCategoryDTO.getParentCategoryId());
    }

    @Test
    void update_NotFound() {
        // Arrange
        when(repository.findById(CATEGORY_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.update(CATEGORY_ID, productCategoryDTO))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException && 
                    throwable.getMessage().equals("Product category not found for the given ID"))
                .verify();

        verify(repository).findById(CATEGORY_ID);
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void update_Error() {
        // Arrange
        when(repository.findById(CATEGORY_ID)).thenReturn(Mono.just(productCategory));
        when(repository.save(productCategory)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(service.update(CATEGORY_ID, productCategoryDTO))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().equals("Failed to update product category"))
                .verify();

        verify(repository).findById(CATEGORY_ID);
        verify(repository).save(productCategory);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void delete_Success() {
        // Arrange
        when(repository.findById(CATEGORY_ID)).thenReturn(Mono.just(productCategory));
        when(repository.delete(productCategory)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.delete(CATEGORY_ID))
                .verifyComplete();

        verify(repository).findById(CATEGORY_ID);
        verify(repository).delete(productCategory);
    }

    @Test
    void delete_NotFound() {
        // Arrange
        when(repository.findById(CATEGORY_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.delete(CATEGORY_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException && 
                    throwable.getMessage().equals("Product category not found for the given ID"))
                .verify();

        verify(repository).findById(CATEGORY_ID);
        verify(repository, never()).delete(any());
    }

    @Test
    void delete_Error() {
        // Arrange
        when(repository.findById(CATEGORY_ID)).thenReturn(Mono.just(productCategory));
        when(repository.delete(productCategory)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(service.delete(CATEGORY_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().equals("Failed to delete product category"))
                .verify();

        verify(repository).findById(CATEGORY_ID);
        verify(repository).delete(productCategory);
    }
}
