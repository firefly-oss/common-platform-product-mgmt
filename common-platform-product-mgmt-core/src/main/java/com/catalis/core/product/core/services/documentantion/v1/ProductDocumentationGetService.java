package com.catalis.core.product.core.services.documentantion.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.documentantion.v1.ProductDocumentationMapper;
import com.catalis.core.product.interfaces.dtos.documentation.v1.ProductDocumentationDTO;
import com.catalis.core.product.models.repositories.documentation.v1.ProductDocumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
public class ProductDocumentationGetService {

    @Autowired
    private ProductDocumentationRepository repository;

    @Autowired
    private ProductDocumentationMapper mapper;

    /**
     * Retrieves a specific product documentation record by its ID.
     *
     * @param productDocumentationId the ID of the product documentation to be retrieved
     * @return a Mono containing the ProductDocumentationDTO if found, or an error if the documentation does not exist
     */
    public Mono<ProductDocumentationDTO> getProductDocumentation(Long productDocumentationId) {
        return repository.findById(productDocumentationId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Documentation not found for ID: " + productDocumentationId)))
                .map(mapper::toDto);
    }

    public Mono<PaginationResponse<ProductDocumentationDTO>> getProductDocumentationsByProductId(Long productId, PaginationRequest pagination) {
        return null;
    }

}
