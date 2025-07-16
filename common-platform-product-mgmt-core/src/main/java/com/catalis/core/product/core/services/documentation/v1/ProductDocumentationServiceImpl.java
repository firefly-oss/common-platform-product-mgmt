package com.catalis.core.product.core.services.documentation.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.documentation.v1.ProductDocumentationMapper;
import com.catalis.core.product.interfaces.dtos.documentation.v1.ProductDocumentationDTO;
import com.catalis.core.product.models.repositories.documentation.v1.ProductDocumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductDocumentationServiceImpl implements ProductDocumentationService {

    @Autowired
    private ProductDocumentationRepository repository;

    @Autowired
    private ProductDocumentationMapper mapper;

    @Override
    public Mono<PaginationResponse<ProductDocumentationDTO>> getAllDocumentations(Long productId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        ).onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve documentations", e)));
    }

    @Override
    public Mono<ProductDocumentationDTO> createDocumentation(Long productId, ProductDocumentationDTO documentationDTO) {
        documentationDTO.setProductId(productId);
        return repository.save(mapper.toEntity(documentationDTO))
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to create documentation", e)));
    }

    @Override
    public Mono<ProductDocumentationDTO> getDocumentation(Long productId, Long docId) {
        return repository.findById(docId)
                .filter(entity -> entity.getProductId().equals(productId))
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Documentation not found")))
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve documentation", e)));
    }

    @Override
    public Mono<ProductDocumentationDTO> updateDocumentation(Long productId, Long docId, ProductDocumentationDTO documentationDTO) {
        return repository.findById(docId)
                .filter(entity -> entity.getProductId().equals(productId))
                .switchIfEmpty(Mono.error(new RuntimeException("Documentation not found for update")))
                .flatMap(existingEntity -> {
                    ProductDocumentationDTO updatedDTO = mapper.toDto(existingEntity);
                    updatedDTO.setDocType(documentationDTO.getDocType());
                    updatedDTO.setDocumentManagerRef(documentationDTO.getDocumentManagerRef());
                    return repository.save(mapper.toEntity(updatedDTO));
                })
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to update documentation", e)));
    }

    @Override
    public Mono<Void> deleteDocumentation(Long productId, Long docId) {
        return repository.findById(docId)
                .filter(entity -> entity.getProductId().equals(productId))
                .switchIfEmpty(Mono.error(new RuntimeException("Documentation not found for deletion")))
                .flatMap(repository::delete)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete documentation", e)));
    }
}