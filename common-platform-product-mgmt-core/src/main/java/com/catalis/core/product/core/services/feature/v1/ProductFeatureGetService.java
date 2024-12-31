package com.catalis.core.product.core.services.feature.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
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
     * Retrieves a paginated list of product features filtered by the specified feature type.
     * The method performs the following steps:
     * - Converts the pagination request into pagination settings.
     * - Fetches the product features matching the given feature type and pagination parameters.
     * - Counts the total number of product features matching the specified feature type.
     * - Maps the product features to their DTO representations.
     * - Combines the results with their count and constructs a paginated response.
     *
     * @param featureType the type of product features to filter by
     * @param paginationRequest the pagination details including page number and page size
     * @return a {@code Mono<PaginationResponse<ProductFeatureDTO>>} containing the paginated list of product features,
     *         the total number of matching features, the total pages, and the current page number
     */
    public Mono<PaginationResponse<ProductFeatureDTO>> findByFeatureType(FeatureTypeEnum featureType,
                                                                         PaginationRequest paginationRequest) {

        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of ProductFeature entities from the repository
        Flux<ProductFeature> features = repository.findByFeatureType(featureType, pageable);

        // Fetch the total count of ProductFeature entities for the given feature type
        Mono<Long> count = repository.countByFeatureType(featureType);

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return features
                // Map each ProductFeature entity to a ProductFeatureDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<ProductFeatureDTO> featureDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            featureDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Set the current page number
                    );
                });

    }

}