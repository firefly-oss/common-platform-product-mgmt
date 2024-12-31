package com.catalis.core.product.core.services.relationship.v1;

import com.catalis.core.product.core.mappers.relationship.v1.ProductRelationshipMapper;
import com.catalis.core.product.interfaces.dtos.relationship.v1.ProductRelationshipDTO;
import com.catalis.core.product.models.repositories.relationship.v1.ProductRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductRelationshipUpdateService {

    @Autowired
    private ProductRelationshipRepository repository;

    @Autowired
    private ProductRelationshipMapper mapper;

    public Mono<ProductRelationshipDTO> updateProductRelationship(Long id, ProductRelationshipDTO request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Relationship not found for ID: " + id)))
                .flatMap(existingEntity -> {
                    existingEntity.setProductId(request.getProductId());
                    existingEntity.setRelatedProductId(request.getRelatedProductId());
                    existingEntity.setRelationshipType(request.getRelationshipType());
                    existingEntity.setDescription(request.getDescription());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDto);
    }

}