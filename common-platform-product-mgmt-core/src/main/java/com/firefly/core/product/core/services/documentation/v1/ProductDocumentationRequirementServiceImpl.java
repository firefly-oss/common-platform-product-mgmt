package com.firefly.core.product.core.services.documentation.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.product.core.mappers.documentation.v1.ProductDocumentationRequirementMapper;
import com.firefly.core.product.interfaces.dtos.documentation.v1.ProductDocumentationRequirementDTO;
import com.firefly.core.product.interfaces.enums.documentation.v1.ContractingDocTypeEnum;
import com.firefly.core.product.models.entities.documentation.v1.ProductDocumentationRequirement;
import com.firefly.core.product.models.repositories.documentation.v1.ProductDocumentationRequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the ProductDocumentationRequirementService interface.
 */
@Service
@Transactional
public class ProductDocumentationRequirementServiceImpl implements ProductDocumentationRequirementService {

    @Autowired
    private ProductDocumentationRequirementRepository repository;

    @Autowired
    private ProductDocumentationRequirementMapper mapper;

    @Override
    public Mono<PaginationResponse<ProductDocumentationRequirementDTO>> getAllDocumentationRequirements(
            UUID productId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        ).onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve documentation requirements", e)));
    }

    @Override
    public Mono<ProductDocumentationRequirementDTO> createDocumentationRequirement(
            UUID productId, ProductDocumentationRequirementDTO requirementDTO) {
        requirementDTO.setProductId(productId);
        return repository.save(mapper.toEntity(requirementDTO))
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to create documentation requirement", e)));
    }

    @Override
    public Mono<ProductDocumentationRequirementDTO> getDocumentationRequirement(
            UUID productId, UUID requirementId) {
        return repository.findById(requirementId)
                .filter(entity -> entity.getProductId().equals(productId))
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Documentation requirement not found")))
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve documentation requirement", e)));
    }

    @Override
    public Mono<ProductDocumentationRequirementDTO> getDocumentationRequirementByType(
            UUID productId, ContractingDocTypeEnum docType) {
        return repository.findByProductIdAndDocType(productId, docType)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Documentation requirement not found for the specified type")))
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve documentation requirement by type", e)));
    }

    @Override
    public Mono<ProductDocumentationRequirementDTO> updateDocumentationRequirement(
            UUID productId, UUID requirementId, ProductDocumentationRequirementDTO requirementDTO) {
        return repository.findById(requirementId)
                .filter(entity -> entity.getProductId().equals(productId))
                .switchIfEmpty(Mono.error(new RuntimeException("Documentation requirement not found for update")))
                .flatMap(existingEntity -> {
                    ProductDocumentationRequirement updatedEntity = mapper.toEntity(requirementDTO);
                    updatedEntity.setProductDocRequirementId(existingEntity.getProductDocRequirementId());
                    updatedEntity.setProductId(productId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to update documentation requirement", e)));
    }

    @Override
    public Mono<Void> deleteDocumentationRequirement(UUID productId, UUID requirementId) {
        return repository.findById(requirementId)
                .filter(entity -> entity.getProductId().equals(productId))
                .switchIfEmpty(Mono.error(new RuntimeException("Documentation requirement not found for deletion")))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete documentation requirement", e)));
    }

    @Override
    public Flux<ProductDocumentationRequirementDTO> getMandatoryDocumentationRequirements(UUID productId) {
        return repository.findByProductIdAndIsMandatory(productId, true)
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve mandatory documentation requirements", e)));
    }
}