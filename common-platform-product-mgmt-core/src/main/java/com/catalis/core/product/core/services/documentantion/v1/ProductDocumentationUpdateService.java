package com.catalis.core.product.core.services.documentantion.v1;

import com.catalis.core.product.core.mappers.documentantion.v1.ProductDocumentationMapper;
import com.catalis.core.product.interfaces.dtos.documentation.v1.ProductDocumentationDTO;
import com.catalis.core.product.models.entities.documentation.v1.ProductDocumentation;
import com.catalis.core.product.models.repositories.documentation.v1.ProductDocumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductDocumentationUpdateService {

    @Autowired
    private ProductDocumentationRepository repository;

    @Autowired
    private ProductDocumentationMapper mapper;

    /**
     * Updates the product documentation record for the given ID with the provided request details.
     *
     * @param id the ID of the product documentation to be updated
     * @param request the ProductDocumentationDTO containing the updated details
     * @return a Mono containing the updated ProductDocumentationDTO if the update is successful,
     *         or an error if the documentation does not exist or the update fails
     */
    public Mono<ProductDocumentationDTO> updateProductDocumentation(Long id, ProductDocumentationDTO request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Documentation not found for ID: " + id)))
                .flatMap(existingEntity -> {
                    ProductDocumentation updatedEntity = mapper.toEntity(request);
                    updatedEntity.setProductId(existingEntity.getProductId() != null ? existingEntity.getProductId() : updatedEntity.getProductId());
                    updatedEntity.setDocType(request.getDocType() != null ? request.getDocType() : existingEntity.getDocType());
                    updatedEntity.setDocumentManagerRef(request.getDocumentManagerRef() != null ? request.getDocumentManagerRef() : existingEntity.getDocumentManagerRef());
                    updatedEntity.setDateAdded(existingEntity.getDateAdded() != null ? existingEntity.getDateAdded() : updatedEntity.getDateAdded());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDto);
    }

}