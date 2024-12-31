package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.core.product.core.mappers.pricing.v1.ProductPricingMapper;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingDTO;
import com.catalis.core.product.models.repositories.pricing.v1.ProductPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductPricingCreateService {

    @Autowired
    private ProductPricingRepository repository;

    @Autowired
    private ProductPricingMapper mapper;

    /**
     * Creates a new product pricing entry by converting the provided DTO to an entity,
     * saving it in the repository, and then mapping the saved entity back to a DTO.
     *
     * @param productPricingDTO the data transfer object containing the product pricing information to be created
     * @return a Mono emitting the created ProductPricingDTO containing the saved product pricing details
     */
    public Mono<ProductPricingDTO> createProductPricing(ProductPricingDTO productPricingDTO) {
        return Mono.just(productPricingDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(savedEntity -> {
                    ProductPricingDTO dto = mapper.toDto(savedEntity);
                    dto.setProductPricingId(savedEntity.getProductPricingId());
                    return dto;
                });
    }

}
