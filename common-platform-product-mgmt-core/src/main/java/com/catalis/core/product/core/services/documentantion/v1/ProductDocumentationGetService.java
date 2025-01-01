package com.catalis.core.product.core.services.documentantion.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.documentantion.v1.ProductDocumentationMapper;
import com.catalis.core.product.interfaces.dtos.documentation.v1.ProductDocumentationDTO;
import com.catalis.core.product.models.entities.documentation.v1.ProductDocumentation;
import com.catalis.core.product.models.repositories.documentation.v1.ProductDocumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    /**
     * Retrieves paginated documentation records associated with the specified product ID.
     *
     * @param productId the ID of the product for which documentation records are being requested
     * @param paginationRequest an object containing pagination details such as page number and page size
     * @return a Mono containing a PaginationResponse with a list of ProductDocumentationDTO objects,
     *         the total number of records, total pages, and the current page number
     */
    public Mono<PaginationResponse<ProductDocumentationDTO>> getProductDocumentationsByProductId(
            Long productId, PaginationRequest paginationRequest) {

        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        );

    }

}
