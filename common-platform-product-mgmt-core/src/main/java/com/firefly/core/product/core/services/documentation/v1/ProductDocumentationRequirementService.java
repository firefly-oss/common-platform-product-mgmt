package com.firefly.core.product.core.services.documentation.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.interfaces.dtos.documentation.v1.ProductDocumentationRequirementDTO;
import com.firefly.core.product.interfaces.enums.documentation.v1.ContractingDocTypeEnum;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for managing product documentation requirements during the contracting/opening phase.
 */
public interface ProductDocumentationRequirementService {

    /**
     * Retrieve a paginated list of all documentation requirements for a product.
     *
     * @param productId The ID of the product
     * @param paginationRequest Pagination parameters
     * @return A paginated response of documentation requirements
     */
    Mono<PaginationResponse<ProductDocumentationRequirementDTO>> getAllDocumentationRequirements(
            Long productId, PaginationRequest paginationRequest);

    /**
     * Create a new documentation requirement for a specific product.
     *
     * @param productId The ID of the product
     * @param requirementDTO The documentation requirement to create
     * @return The created documentation requirement
     */
    Mono<ProductDocumentationRequirementDTO> createDocumentationRequirement(
            Long productId, ProductDocumentationRequirementDTO requirementDTO);

    /**
     * Retrieve a specific documentation requirement by its unique identifier within a product.
     *
     * @param productId The ID of the product
     * @param requirementId The ID of the documentation requirement
     * @return The documentation requirement
     */
    Mono<ProductDocumentationRequirementDTO> getDocumentationRequirement(
            Long productId, Long requirementId);

    /**
     * Retrieve a specific documentation requirement by its document type within a product.
     *
     * @param productId The ID of the product
     * @param docType The type of document
     * @return The documentation requirement
     */
    Mono<ProductDocumentationRequirementDTO> getDocumentationRequirementByType(
            Long productId, ContractingDocTypeEnum docType);

    /**
     * Update an existing documentation requirement for a specific product.
     *
     * @param productId The ID of the product
     * @param requirementId The ID of the documentation requirement
     * @param requirementDTO The updated documentation requirement
     * @return The updated documentation requirement
     */
    Mono<ProductDocumentationRequirementDTO> updateDocumentationRequirement(
            Long productId, Long requirementId, ProductDocumentationRequirementDTO requirementDTO);

    /**
     * Delete an existing documentation requirement by its unique identifier, within the context of a product.
     *
     * @param productId The ID of the product
     * @param requirementId The ID of the documentation requirement
     * @return A Mono that completes when the deletion is done
     */
    Mono<Void> deleteDocumentationRequirement(Long productId, Long requirementId);

    /**
     * Get all mandatory documentation requirements for a product.
     *
     * @param productId The ID of the product
     * @return A flux of mandatory documentation requirements
     */
    Flux<ProductDocumentationRequirementDTO> getMandatoryDocumentationRequirements(Long productId);
}