package com.catalis.core.product.core.services.documentantion.v1;

import com.catalis.core.product.core.mappers.documentantion.v1.ProductDocumentationMapper;
import com.catalis.core.product.interfaces.dtos.documentation.v1.ProductDocumentationDTO;
import com.catalis.core.product.models.repositories.documentation.v1.ProductDocumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductDocumentationCreateService {

    @Autowired
    private ProductDocumentationRepository repository;

    @Autowired
    private ProductDocumentationMapper mapper;

    /**
     * Creates a new product documentation record.
     *
     * @param request the ProductDocumentationDTO containing the details of the product documentation to be created
     * @return a Mono containing the created ProductDocumentationDTO with the assigned product documentation ID
     */
    public Mono<ProductDocumentationDTO> createProductDocumentation(ProductDocumentationDTO request) {
        return Mono.just(request)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(savedEntity -> {
                    ProductDocumentationDTO dto = mapper.toDto(savedEntity);
                    dto.setProductDocumentationId(savedEntity.getProductDocumentationId());
                    return dto;
                });
    }

}