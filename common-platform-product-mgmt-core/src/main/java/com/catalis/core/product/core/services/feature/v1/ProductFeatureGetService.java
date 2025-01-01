package com.catalis.core.product.core.services.feature.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.feature.v1.ProductFeatureMapper;
import com.catalis.core.product.interfaces.dtos.feature.v1.ProductFeatureDTO;
import com.catalis.core.product.interfaces.enums.feature.v1.FeatureTypeEnum;
import com.catalis.core.product.models.entities.feature.v1.ProductFeature;
import com.catalis.core.product.models.repositories.feature.v1.ProductFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductFeatureGetService {

    @Autowired
    private ProductFeatureRepository repository;

    @Autowired
    private ProductFeatureMapper mapper;

    /**
     * Retrieves a product feature based on the provided identifier.
     * Searches the repository for the product feature with the specified ID.
     * If the product feature is found, it is mapped to a DTO and returned.
     * If not found, an {@code IllegalArgumentException} is emitted.
     *
     * @param productFeatureId the unique identifier of the product feature to be retrieved
     * @return a {@code Mono<ProductFeatureDTO>} emitting the product feature data transfer object if found,
     *         or an error if the product feature does not exist
     */
    public Mono<ProductFeatureDTO> getProductFeature(Long productFeatureId) {
        return repository.findById(productFeatureId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Feature not found for ID: " + productFeatureId)))
                .map(mapper::toDto);
    }

    /**
     * Retrieves a paginated list of product feature DTOs filtered by the specified feature type.
     *
     * @param featureType the type of the features to filter by (e.g., STANDARD, OPTIONAL, PREMIUM)
     * @param paginationRequest the pagination details including page size, page number, and sorting options
     * @return a {@code Mono<PaginationResponse<ProductFeatureDTO>>} containing the paginated list of product feature DTOs
     */
    public Mono<PaginationResponse<ProductFeatureDTO>> findByFeatureType(FeatureTypeEnum featureType,
                                                                         PaginationRequest paginationRequest) {

        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByFeatureType(featureType, pageable),
                () -> repository.countByFeatureType(featureType)
        );

    }

}