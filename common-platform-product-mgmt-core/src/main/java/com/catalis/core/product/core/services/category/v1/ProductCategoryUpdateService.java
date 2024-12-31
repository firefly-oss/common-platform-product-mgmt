package com.catalis.core.product.core.services.category.v1;

import com.catalis.core.product.core.mappers.category.v1.ProductCategoryMapper;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
import com.catalis.core.product.models.repositories.category.v1.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductCategoryUpdateService {
    
    @Autowired
    private ProductCategoryRepository repository;

    @Autowired
    private ProductCategoryMapper mapper;


    /**
     * Updates an existing product category with the given ID using the details from the provided ProductCategoryDTO.
     * If the product category with the specified ID is not found, an error is returned.
     *
     * @param id the unique identifier of the product category to be updated
     * @param request the ProductCategoryDTO containing the updated details for the product category
     * @return a Mono emitting the updated ProductCategoryDTO, or an error if the ID is not found or the update fails
     */
    public Mono<ProductCategoryDTO> update(Long id, ProductCategoryDTO request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product category not found for ID: " + id)))
                .flatMap(existingEntity -> {
                    // Map updates from DTO to the existing entity
                    existingEntity.setCategoryName(request.getCategoryName());
                    existingEntity.setCategoryDescription(request.getCategoryDescription());
                    existingEntity.setParentCategoryId(request.getParentCategoryId());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDto);
    }
    
}