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
public class ProductRelationshipCreateService {
    
    @Autowired
    private ProductRelationshipRepository repository;
    
    @Autowired
    private ProductRelationshipMapper mapper;

    /**
     * Creates and persists a new product relationship based on the provided data.
     *
     * @param request the {@code ProductRelationshipDTO} containing the details of the product relationship to be created
     * @return a {@code Mono<ProductRelationshipDTO>} containing the saved product relationship with its associated ID and other details
     */
    public Mono<ProductRelationshipDTO> createProductRelationship(ProductRelationshipDTO request) {
        return Mono.just(request)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .flatMap(savedEntity -> repository.findById(savedEntity.getProductRelationshipId()))
                .map(mapper::toDto);
    }
    
}
