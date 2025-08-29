package com.firefly.core.product.models.repositories.documentation.v1;

import com.firefly.core.product.interfaces.enums.documentation.v1.ContractingDocTypeEnum;
import com.firefly.core.product.models.entities.documentation.v1.ProductDocumentationRequirement;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository for managing ProductDocumentationRequirement entities.
 */
@Repository
public interface ProductDocumentationRequirementRepository extends BaseRepository<ProductDocumentationRequirement, Long> {
    
    /**
     * Find all documentation requirements for a specific product.
     *
     * @param productId The ID of the product
     * @return A Flux of ProductDocumentationRequirement entities
     */
    Flux<ProductDocumentationRequirement> findByProductId(Long productId);
    
    /**
     * Find all documentation requirements for a specific product with pagination.
     *
     * @param productId The ID of the product
     * @param pageable Pagination information
     * @return A Flux of ProductDocumentationRequirement entities
     */
    Flux<ProductDocumentationRequirement> findByProductId(Long productId, Pageable pageable);
    
    /**
     * Count the number of documentation requirements for a specific product.
     *
     * @param productId The ID of the product
     * @return A Mono with the count
     */
    Mono<Long> countByProductId(Long productId);
    
    /**
     * Find all mandatory documentation requirements for a specific product.
     *
     * @param productId The ID of the product
     * @param isMandatory Whether the requirement is mandatory
     * @return A Flux of ProductDocumentationRequirement entities
     */
    Flux<ProductDocumentationRequirement> findByProductIdAndIsMandatory(Long productId, Boolean isMandatory);
    
    /**
     * Find a specific documentation requirement for a product by document type.
     *
     * @param productId The ID of the product
     * @param docType The type of document
     * @return A Mono with the ProductDocumentationRequirement entity
     */
    Mono<ProductDocumentationRequirement> findByProductIdAndDocType(Long productId, ContractingDocTypeEnum docType);
    
    /**
     * Delete all documentation requirements for a specific product.
     *
     * @param productId The ID of the product
     * @return A Mono with the number of deleted entities
     */
    Mono<Long> deleteByProductId(Long productId);
}