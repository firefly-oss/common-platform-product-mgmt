package com.firefly.core.product.core.services.lifecycle.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.core.mappers.lifecycle.v1.ProductLifecycleMapper;
import com.firefly.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import com.firefly.core.product.interfaces.enums.lifecycle.v1.LifecycleStatusEnum;
import com.firefly.core.product.models.entities.lifecycle.v1.ProductLifecycle;
import com.firefly.core.product.models.repositories.lifecycle.v1.ProductLifecycleRepository;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductLifecycleServiceImplTest {

    @Mock
    private ProductLifecycleRepository repository;

    @Mock
    private ProductLifecycleMapper mapper;

    @InjectMocks
    private ProductLifecycleServiceImpl service;

    private ProductLifecycle lifecycle;
    private ProductLifecycleDTO lifecycleDTO;
    private final Long PRODUCT_ID = 1L;
    private final Long LIFECYCLE_ID = 2L;

    @BeforeEach
    void setUp() {
        // Setup test data
        LocalDateTime now = LocalDateTime.now();

        lifecycle = new ProductLifecycle();
        lifecycle.setProductLifecycleId(LIFECYCLE_ID);
        lifecycle.setProductId(PRODUCT_ID);
        lifecycle.setLifecycleStatus(LifecycleStatusEnum.ACTIVE);
        lifecycle.setStatusStartDate(now);
        lifecycle.setStatusEndDate(now.plusMonths(6));
        lifecycle.setReason("Initial activation");
        lifecycle.setDateCreated(now);
        lifecycle.setDateUpdated(now);

        lifecycleDTO = ProductLifecycleDTO.builder()
                .productLifecycleId(LIFECYCLE_ID)
                .productId(PRODUCT_ID)
                .lifecycleStatus(LifecycleStatusEnum.ACTIVE)
                .statusStartDate(now)
                .statusEndDate(now.plusMonths(6))
                .reason("Initial activation")
                .dateCreated(now)
                .dateUpdated(now)
                .build();
    }

    @Test
    void getProductLifecycles_Success() {
        // Arrange
        // Mock PaginationRequest
        PaginationRequest paginationRequest = Mockito.mock(PaginationRequest.class);

        // Mock Pageable
        Pageable pageable = Mockito.mock(Pageable.class);

        // Mock PaginationRequest behavior
        doReturn(pageable).when(paginationRequest).toPageable();

        // Set up repository and mapper mocks
        when(repository.findByProductId(eq(PRODUCT_ID), eq(pageable))).thenReturn(Flux.just(lifecycle));
        when(repository.countByProductId(PRODUCT_ID)).thenReturn(Mono.just(1L));
        when(mapper.toDto(lifecycle)).thenReturn(lifecycleDTO);

        // Act & Assert
        StepVerifier.create(service.getProductLifecycles(PRODUCT_ID, paginationRequest))
                .expectNextMatches(response -> {
                    // Verify response contains our DTO
                    List<ProductLifecycleDTO> content = response.getContent();
                    return content != null && 
                           content.size() == 1 && 
                           content.get(0).equals(lifecycleDTO);
                })
                .verifyComplete();

        // Verify interactions
        verify(repository).findByProductId(eq(PRODUCT_ID), eq(pageable));
        verify(repository).countByProductId(PRODUCT_ID);
        verify(mapper).toDto(lifecycle);
    }

    @Test
    void createProductLifecycle_Success() {
        // Arrange
        ProductLifecycleDTO requestDTO = ProductLifecycleDTO.builder()
                .lifecycleStatus(LifecycleStatusEnum.ACTIVE)
                .statusStartDate(LocalDateTime.now())
                .reason("Initial activation")
                .build();

        when(mapper.toEntity(requestDTO)).thenReturn(lifecycle);
        when(repository.save(lifecycle)).thenReturn(Mono.just(lifecycle));
        when(mapper.toDto(lifecycle)).thenReturn(lifecycleDTO);

        // Act & Assert
        StepVerifier.create(service.createProductLifecycle(PRODUCT_ID, requestDTO))
                .expectNext(lifecycleDTO)
                .verifyComplete();

        // Verify interactions
        verify(mapper).toEntity(requestDTO);
        verify(repository).save(lifecycle);
        verify(mapper).toDto(lifecycle);
    }

    @Test
    void createProductLifecycle_Error() {
        // Arrange
        ProductLifecycleDTO requestDTO = ProductLifecycleDTO.builder()
                .lifecycleStatus(LifecycleStatusEnum.ACTIVE)
                .statusStartDate(LocalDateTime.now())
                .reason("Initial activation")
                .build();

        when(mapper.toEntity(requestDTO)).thenReturn(lifecycle);
        when(repository.save(lifecycle)).thenReturn(Mono.error(new RuntimeException("Database error")));

        // Act & Assert
        StepVerifier.create(service.createProductLifecycle(PRODUCT_ID, requestDTO))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().contains("Error creating product lifecycle"))
                .verify();

        // Verify interactions
        verify(mapper).toEntity(requestDTO);
        verify(repository).save(lifecycle);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getProductLifecycle_Success() {
        // Arrange
        when(repository.findById(LIFECYCLE_ID)).thenReturn(Mono.just(lifecycle));
        when(mapper.toDto(lifecycle)).thenReturn(lifecycleDTO);

        // Act & Assert
        StepVerifier.create(service.getProductLifecycle(PRODUCT_ID, LIFECYCLE_ID))
                .expectNext(lifecycleDTO)
                .verifyComplete();

        // Verify interactions
        verify(repository).findById(LIFECYCLE_ID);
        verify(mapper).toDto(lifecycle);
    }

    @Test
    void getProductLifecycle_NotFound() {
        // Arrange
        when(repository.findById(LIFECYCLE_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getProductLifecycle(PRODUCT_ID, LIFECYCLE_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().contains("Error retrieving product lifecycle"))
                .verify();

        // Verify interactions
        verify(repository).findById(LIFECYCLE_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getProductLifecycle_WrongProduct() {
        // Arrange
        ProductLifecycle lifecycleFromDifferentProduct = new ProductLifecycle();
        lifecycleFromDifferentProduct.setProductLifecycleId(LIFECYCLE_ID);
        lifecycleFromDifferentProduct.setProductId(999L); // Different product ID

        when(repository.findById(LIFECYCLE_ID)).thenReturn(Mono.just(lifecycleFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.getProductLifecycle(PRODUCT_ID, LIFECYCLE_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().contains("Error retrieving product lifecycle"))
                .verify();

        // Verify interactions
        verify(repository).findById(LIFECYCLE_ID);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateProductLifecycle_Success() {
        // Arrange
        ProductLifecycleDTO updateRequest = ProductLifecycleDTO.builder()
                .lifecycleStatus(LifecycleStatusEnum.SUSPENDED)
                .statusStartDate(LocalDateTime.now())
                .statusEndDate(LocalDateTime.now().plusMonths(1))
                .reason("Temporary suspension")
                .build();

        ProductLifecycle updatedEntity = new ProductLifecycle();
        updatedEntity.setProductLifecycleId(LIFECYCLE_ID);
        updatedEntity.setProductId(PRODUCT_ID);
        updatedEntity.setLifecycleStatus(LifecycleStatusEnum.SUSPENDED);
        updatedEntity.setStatusStartDate(updateRequest.getStatusStartDate());
        updatedEntity.setStatusEndDate(updateRequest.getStatusEndDate());
        updatedEntity.setReason("Temporary suspension");

        when(repository.findById(LIFECYCLE_ID)).thenReturn(Mono.just(lifecycle));
        when(mapper.toEntity(updateRequest)).thenReturn(updatedEntity);
        when(repository.save(any(ProductLifecycle.class))).thenReturn(Mono.just(updatedEntity));
        when(mapper.toDto(updatedEntity)).thenReturn(updateRequest);

        // Act & Assert
        StepVerifier.create(service.updateProductLifecycle(PRODUCT_ID, LIFECYCLE_ID, updateRequest))
                .expectNext(updateRequest)
                .verifyComplete();

        // Verify interactions
        verify(repository).findById(LIFECYCLE_ID);
        verify(mapper).toEntity(updateRequest);
        verify(repository).save(any(ProductLifecycle.class));
        verify(mapper).toDto(any(ProductLifecycle.class));
    }

    @Test
    void updateProductLifecycle_NotFound() {
        // Arrange
        ProductLifecycleDTO updateRequest = ProductLifecycleDTO.builder()
                .lifecycleStatus(LifecycleStatusEnum.SUSPENDED)
                .statusStartDate(LocalDateTime.now())
                .reason("Temporary suspension")
                .build();

        when(repository.findById(LIFECYCLE_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateProductLifecycle(PRODUCT_ID, LIFECYCLE_ID, updateRequest))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().contains("Error updating product lifecycle"))
                .verify();

        // Verify interactions
        verify(repository).findById(LIFECYCLE_ID);
        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateProductLifecycle_WrongProduct() {
        // Arrange
        ProductLifecycleDTO updateRequest = ProductLifecycleDTO.builder()
                .lifecycleStatus(LifecycleStatusEnum.SUSPENDED)
                .statusStartDate(LocalDateTime.now())
                .reason("Temporary suspension")
                .build();

        ProductLifecycle lifecycleFromDifferentProduct = new ProductLifecycle();
        lifecycleFromDifferentProduct.setProductLifecycleId(LIFECYCLE_ID);
        lifecycleFromDifferentProduct.setProductId(999L); // Different product ID

        when(repository.findById(LIFECYCLE_ID)).thenReturn(Mono.just(lifecycleFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.updateProductLifecycle(PRODUCT_ID, LIFECYCLE_ID, updateRequest))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().contains("Error updating product lifecycle"))
                .verify();

        // Verify interactions
        verify(repository).findById(LIFECYCLE_ID);
        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void updateProductLifecycle_InvalidData() {
        // Arrange
        ProductLifecycleDTO updateRequest = ProductLifecycleDTO.builder()
                .lifecycleStatus(LifecycleStatusEnum.SUSPENDED)
                .statusStartDate(LocalDateTime.now())
                .reason("Temporary suspension")
                .build();

        when(repository.findById(LIFECYCLE_ID)).thenReturn(Mono.just(lifecycle));
        when(mapper.toEntity(updateRequest)).thenThrow(new RuntimeException("Invalid data"));

        // Act & Assert
        StepVerifier.create(service.updateProductLifecycle(PRODUCT_ID, LIFECYCLE_ID, updateRequest))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().contains("Error updating product lifecycle"))
                .verify();

        // Verify interactions
        verify(repository).findById(LIFECYCLE_ID);
        verify(mapper).toEntity(updateRequest);
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }

    @Test
    void deleteProductLifecycle_Success() {
        // Arrange
        when(repository.findById(LIFECYCLE_ID)).thenReturn(Mono.just(lifecycle));
        when(repository.delete(lifecycle)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteProductLifecycle(PRODUCT_ID, LIFECYCLE_ID))
                .verifyComplete();

        // Verify interactions
        verify(repository).findById(LIFECYCLE_ID);
        verify(repository).delete(lifecycle);
    }

    @Test
    void deleteProductLifecycle_NotFound() {
        // Arrange
        when(repository.findById(LIFECYCLE_ID)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteProductLifecycle(PRODUCT_ID, LIFECYCLE_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().contains("Error deleting product lifecycle"))
                .verify();

        // Verify interactions
        verify(repository).findById(LIFECYCLE_ID);
        verify(repository, never()).delete(any());
    }

    @Test
    void deleteProductLifecycle_WrongProduct() {
        // Arrange
        ProductLifecycle lifecycleFromDifferentProduct = new ProductLifecycle();
        lifecycleFromDifferentProduct.setProductLifecycleId(LIFECYCLE_ID);
        lifecycleFromDifferentProduct.setProductId(999L); // Different product ID

        when(repository.findById(LIFECYCLE_ID)).thenReturn(Mono.just(lifecycleFromDifferentProduct));

        // Act & Assert
        StepVerifier.create(service.deleteProductLifecycle(PRODUCT_ID, LIFECYCLE_ID))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().contains("Error deleting product lifecycle"))
                .verify();

        // Verify interactions
        verify(repository).findById(LIFECYCLE_ID);
        verify(repository, never()).delete(any());
    }
}
