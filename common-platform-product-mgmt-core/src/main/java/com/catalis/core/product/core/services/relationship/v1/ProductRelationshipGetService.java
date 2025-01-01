package com.catalis.core.product.core.services.relationship.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.relationship.v1.ProductRelationshipMapper;
import com.catalis.core.product.interfaces.dtos.relationship.v1.ProductRelationshipDTO;
import com.catalis.core.product.models.entities.relationship.v1.ProductRelationship;
import com.catalis.core.product.models.repositories.relationship.v1.ProductRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductRelationshipGetService {

    @Autowired
    private ProductRelationshipRepository repository;

    @Autowired
    private ProductRelationshipMapper mapper;

    /**
     * Retrieves a product relationship by its ID and maps it to a {@code ProductRelationshipDTO}.
     * If the relationship does not exist, an error is returned.
     *
     * @param productRelationshipId the ID of the product relationship to retrieve
     * @return a {@code Mono<ProductRelationshipDTO>} containing the product relationship details,
     *         or an error if no relationship is found for the given ID
     */
    public Mono<ProductRelationshipDTO> getProductRelationship(Long productRelationshipId) {
        return repository.findById(productRelationshipId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Relationship not found for ID: " + productRelationshipId)))
                .map(mapper::toDto);
    }

    /**
     * Retrieves a paginated list of product relationships for a given product ID.
     * The results are mapped to {@code ProductRelationshipDTO} objects.
     *
     * @param productId the ID of the product for which relationships are to be retrieved
     * @param paginationRequest the pagination request containing page size and other pagination parameters
     * @return a {@code Mono<PaginationResponse<ProductRelationshipDTO>>} containing the paginated list of
     *         product relationship details
     */
    public Mono<PaginationResponse<ProductRelationshipDTO>> findByProductId(Long productId,
                                                                            PaginationRequest paginationRequest) {

        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        );

    }

}