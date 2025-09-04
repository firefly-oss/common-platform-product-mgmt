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


package com.firefly.core.product.core.services.bundle.v1;

import com.firefly.core.product.core.mappers.bundle.v1.ProductBundleItemMapper;
import com.firefly.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
import com.firefly.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import com.firefly.core.product.models.entities.bundle.v1.ProductBundleItem;
import com.firefly.core.product.models.repositories.bundle.v1.ProductBundleItemRepository;
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
class ProductBundleItemServiceImplTest {

    @Mock
    private ProductBundleItemRepository repository;

    @Mock
    private ProductBundleService bundleService;

    @Mock
    private ProductBundleItemMapper mapper;

    @InjectMocks
    private ProductBundleItemServiceImpl service;

    private ProductBundleItem productBundleItem;
    private ProductBundleItemDTO productBundleItemDTO;
    private ProductBundleDTO productBundleDTO;
    private final UUID BUNDLE_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    private final UUID ITEM_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440002");
    private final UUID PRODUCT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440003");

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();

        productBundleItem = new ProductBundleItem();
        productBundleItem.setProductBundleItemId(ITEM_ID);
        productBundleItem.setProductBundleId(BUNDLE_ID);
        productBundleItem.setProductId(PRODUCT_ID);
        productBundleItem.setSpecialConditions("Test Special Conditions");
        productBundleItem.setDateCreated(now);
        productBundleItem.setDateUpdated(now);

        productBundleItemDTO = ProductBundleItemDTO.builder()
                .productBundleItemId(ITEM_ID)
                .productBundleId(BUNDLE_ID)
                .productId(PRODUCT_ID)
                .specialConditions("Test Special Conditions")
                .dateCreated(now)
                .dateUpdated(now)
                .build();

        productBundleDTO = ProductBundleDTO.builder()
                .productBundleId(BUNDLE_ID)
                .bundleName("Test Bundle")
                .bundleDescription("Test Description")
                .dateCreated(now)
                .dateUpdated(now)
                .build();
    }

    @Test
    void getItem_Success() {
        // Arrange
        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.just(productBundleDTO));
        when(repository.findById(ITEM_ID)).thenReturn(Mono.just(productBundleItem));
        when(mapper.toDto(productBundleItem)).thenReturn(productBundleItemDTO);

        // Act & Assert
        StepVerifier.create(service.getItem(BUNDLE_ID, ITEM_ID))
                .expectNext(productBundleItemDTO)
                .verifyComplete();

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository).findById(ITEM_ID);
        verify(mapper).toDto(productBundleItem);
    }

    @Test
    void getItem_BundleNotFound() {
        // Arrange
        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.error(new IllegalArgumentException("Product bundle not found")));

        // Act & Assert
        StepVerifier.create(service.getItem(BUNDLE_ID, ITEM_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException && 
                    throwable.getMessage().equals("Product bundle not found"))
                .verify();

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository, never()).findById(any(UUID.class));
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getItem_ItemNotFound() {
        // Arrange
        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.just(productBundleDTO));
        when(repository.findById(ITEM_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getItem(BUNDLE_ID, ITEM_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException && 
                    throwable.getMessage().equals("Product bundle item not found"))
                .verify();

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository).findById(ITEM_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getItem_ItemNotInBundle() {
        // Arrange
        ProductBundleItem itemFromDifferentBundle = new ProductBundleItem();
        itemFromDifferentBundle.setProductBundleItemId(ITEM_ID);
        itemFromDifferentBundle.setProductBundleId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different bundle ID

        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.just(productBundleDTO));
        when(repository.findById(ITEM_ID)).thenReturn(Mono.just(itemFromDifferentBundle));

        // Act & Assert
        StepVerifier.create(service.getItem(BUNDLE_ID, ITEM_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException && 
                    throwable.getMessage().equals("Product bundle item not found"))
                .verify();

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository).findById(ITEM_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void createItem_Success() {
        // Arrange
        ProductBundleItem spyItem = spy(new ProductBundleItem());
        spyItem.setProductBundleItemId(ITEM_ID);
        spyItem.setProductId(PRODUCT_ID);
        spyItem.setSpecialConditions("Test Special Conditions");

        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.just(productBundleDTO));
        when(mapper.toEntity(productBundleItemDTO)).thenReturn(spyItem);
        when(repository.save(spyItem)).thenReturn(Mono.just(spyItem));
        when(mapper.toDto(spyItem)).thenReturn(productBundleItemDTO);

        // Act & Assert
        StepVerifier.create(service.createItem(BUNDLE_ID, productBundleItemDTO))
                .expectNext(productBundleItemDTO)
                .verifyComplete();

        verify(bundleService).getById(BUNDLE_ID);
        verify(mapper).toEntity(productBundleItemDTO);
        verify(repository).save(spyItem);
        verify(mapper).toDto(spyItem);
        verify(spyItem).setProductBundleId(BUNDLE_ID);
    }

    @Test
    void createItem_BundleNotFound() {
        // Arrange
        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.error(new IllegalArgumentException("Product bundle not found")));

        // Act & Assert
        StepVerifier.create(service.createItem(BUNDLE_ID, productBundleItemDTO))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException && 
                    throwable.getMessage().equals("Product bundle not found"))
                .verify();

        verify(bundleService).getById(BUNDLE_ID);
        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateItem_Success() {
        // Arrange
        ProductBundleItem existingItem = spy(new ProductBundleItem());
        existingItem.setProductBundleItemId(ITEM_ID);
        existingItem.setProductBundleId(BUNDLE_ID);
        existingItem.setProductId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different product ID
        existingItem.setSpecialConditions("Old Special Conditions");

        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.just(productBundleDTO));
        when(repository.findById(ITEM_ID)).thenReturn(Mono.just(existingItem));
        when(repository.save(existingItem)).thenReturn(Mono.just(existingItem));
        when(mapper.toDto(existingItem)).thenReturn(productBundleItemDTO);

        // Act & Assert
        StepVerifier.create(service.updateItem(BUNDLE_ID, ITEM_ID, productBundleItemDTO))
                .expectNext(productBundleItemDTO)
                .verifyComplete();

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository).findById(ITEM_ID);
        verify(repository).save(existingItem);
        verify(mapper).toDto(existingItem);

        // Verify that the fields were updated
        verify(existingItem).setProductId(productBundleItemDTO.getProductId());
        verify(existingItem).setSpecialConditions(productBundleItemDTO.getSpecialConditions());
    }

    @Test
    void updateItem_BundleNotFound() {
        // Arrange
        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.error(new IllegalArgumentException("Product bundle not found")));

        // Act & Assert
        StepVerifier.create(service.updateItem(BUNDLE_ID, ITEM_ID, productBundleItemDTO))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException && 
                    throwable.getMessage().equals("Product bundle not found"))
                .verify();

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository, never()).findById(any(UUID.class));
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateItem_ItemNotFound() {
        // Arrange
        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.just(productBundleDTO));
        when(repository.findById(ITEM_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateItem(BUNDLE_ID, ITEM_ID, productBundleItemDTO))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException && 
                    throwable.getMessage().equals("Product bundle item not found"))
                .verify();

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository).findById(ITEM_ID);
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateItem_ItemNotInBundle() {
        // Arrange
        ProductBundleItem itemFromDifferentBundle = new ProductBundleItem();
        itemFromDifferentBundle.setProductBundleItemId(ITEM_ID);
        itemFromDifferentBundle.setProductBundleId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different bundle ID

        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.just(productBundleDTO));
        when(repository.findById(ITEM_ID)).thenReturn(Mono.just(itemFromDifferentBundle));

        // Act & Assert
        StepVerifier.create(service.updateItem(BUNDLE_ID, ITEM_ID, productBundleItemDTO))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException && 
                    throwable.getMessage().equals("Product bundle item not found"))
                .verify();

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository).findById(ITEM_ID);
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void deleteItem_Success() {
        // Arrange
        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.just(productBundleDTO));
        when(repository.findById(ITEM_ID)).thenReturn(Mono.just(productBundleItem));
        when(repository.delete(productBundleItem)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteItem(BUNDLE_ID, ITEM_ID))
                .verifyComplete();

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository).findById(ITEM_ID);
        verify(repository).delete(productBundleItem);
    }

    @Test
    void deleteItem_BundleNotFound() {
        // Arrange
        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.error(new IllegalArgumentException("Product bundle not found")));

        // Act & Assert
        StepVerifier.create(service.deleteItem(BUNDLE_ID, ITEM_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof IllegalArgumentException && 
                    throwable.getMessage().equals("Product bundle not found"))
                .verify();

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository, never()).findById(any(UUID.class));
        verify(repository, never()).delete(any());
    }

    @Test
    void deleteItem_ItemNotFound() {
        // Arrange
        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.just(productBundleDTO));
        when(repository.findById(ITEM_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteItem(BUNDLE_ID, ITEM_ID))
                .verifyComplete(); // Note: The service doesn't throw an error if the item doesn't exist

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository).findById(ITEM_ID);
        verify(repository, never()).delete(any());
    }

    @Test
    void deleteItem_ItemNotInBundle() {
        // Arrange
        ProductBundleItem itemFromDifferentBundle = new ProductBundleItem();
        itemFromDifferentBundle.setProductBundleItemId(ITEM_ID);
        itemFromDifferentBundle.setProductBundleId(UUID.fromString("550e8400-e29b-41d4-a716-446655440999")); // Different bundle ID

        when(bundleService.getById(BUNDLE_ID)).thenReturn(Mono.just(productBundleDTO));
        when(repository.findById(ITEM_ID)).thenReturn(Mono.just(itemFromDifferentBundle));

        // Act & Assert
        StepVerifier.create(service.deleteItem(BUNDLE_ID, ITEM_ID))
                .verifyComplete(); // Note: The service doesn't throw an error if the item is not in the bundle

        verify(bundleService).getById(BUNDLE_ID);
        verify(repository).findById(ITEM_ID);
        verify(repository, never()).delete(any());
    }
}
