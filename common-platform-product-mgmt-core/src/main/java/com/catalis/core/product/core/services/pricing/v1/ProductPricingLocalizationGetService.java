package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.pricing.v1.ProductPricingLocalizationMapper;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingLocalizationDTO;
import com.catalis.core.product.models.entities.pricing.v1.ProductPricingLocalization;
import com.catalis.core.product.models.repositories.pricing.v1.ProductPricingLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductPricingLocalizationGetService {

    @Autowired
    private ProductPricingLocalizationRepository repository;

    @Autowired
    private ProductPricingLocalizationMapper mapper;

    /**
     * Retrieves the product pricing localization details for the specified ID.
     * Emits an error if no product pricing localization is found for the given ID.
     *
     * @param productPricingLocalizationId the unique identifier of the product pricing localization to retrieve
     * @return a Mono emitting the ProductPricingLocalizationDTO containing the localization details,
     *         or an error if the product pricing localization is not found
     */
    public Mono<ProductPricingLocalizationDTO> getProductPricingLocalization(Long productPricingLocalizationId) {
        return repository.findById(productPricingLocalizationId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Pricing Localization not found for ID: " + productPricingLocalizationId)))
                .map(mapper::toDto);
    }

    /**
     * Retrieves paginated product pricing localization data for the specified pricing ID.
     * Applies pagination and maps the results to their DTO representation.
     *
     * @param pricingId the unique identifier of the product pricing to retrieve localization data for
     * @param paginationRequest the pagination request containing page size, page number, and other pagination parameters
     * @return a Mono emitting a PaginationResponse containing a list of ProductPricingLocalizationDTO objects
     *         for the specified pricing ID
     */
    public Mono<PaginationResponse<ProductPricingLocalizationDTO>> findByProductPricingId(
            Long pricingId, PaginationRequest paginationRequest) {

        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductPricingId(pricingId, pageable),
                () -> repository.countByProductPricingId(pricingId)
        );

    }

}
