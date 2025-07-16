package com.catalis.core.product.core.services.documentation.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.documentation.v1.ProductDocumentationRequirementMapper;
import com.catalis.core.product.interfaces.dtos.documentation.v1.ProductDocumentationRequirementDTO;
import com.catalis.core.product.interfaces.enums.documentation.v1.ContractingDocTypeEnum;
import com.catalis.core.product.models.entities.documentation.v1.ProductDocumentationRequirement;
import com.catalis.core.product.models.repositories.documentation.v1.ProductDocumentationRequirementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

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
    private final Long productId = 1L;
    private final Long requirementId = 1L;

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

    @Test
    void getAllDocumentationRequirements_ShouldReturnPaginatedList() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Mock PaginationRequest
        PaginationRequest paginationRequest = Mockito.mock(PaginationRequest.class);

        // Mock PaginationRequest behavior
        doReturn(pageable).when(paginationRequest).toPageable();

        when(repository.findByProductId(eq(productId), eq(pageable)))
                .thenReturn(Flux.just(entity));
        when(repository.countByProductId(productId))
                .thenReturn(Mono.just(1L));
        when(mapper.toDto(entity)).thenReturn(dto);

        // Act & Assert
        StepVerifier.create(service.getAllDocumentationRequirements(productId, paginationRequest))
                .expectNextMatches(response -> 
                    response.getContent().size() == 1 &&
                    response.getContent().get(0).getProductDocRequirementId().equals(requirementId) &&
                    response.getTotalElements() == 1)
                .verifyComplete();
    }

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
    void getDocumentationRequirement_ShouldReturnDTO() {
        // Arrange
        when(repository.findById(requirementId)).thenReturn(Mono.just(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        // Act & Assert
        StepVerifier.create(service.getDocumentationRequirement(productId, requirementId))
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
        when(mapper.toEntity(dto)).thenReturn(entity);
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
        when(repository.delete(entity)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDocumentationRequirement(productId, requirementId))
                .verifyComplete();
    }

    @Test
    void getMandatoryDocumentationRequirements_ShouldReturnMandatoryRequirements() {
        // Arrange
        when(repository.findByProductIdAndIsMandatory(productId, true))
                .thenReturn(Flux.just(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        // Act & Assert
        StepVerifier.create(service.getMandatoryDocumentationRequirements(productId))
                .expectNext(dto)
                .verifyComplete();
    }
}
