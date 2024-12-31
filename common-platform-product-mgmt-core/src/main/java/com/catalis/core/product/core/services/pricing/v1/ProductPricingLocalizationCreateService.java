package com.catalis.core.product.core.services.pricing.v1;

import com.catalis.core.product.core.mappers.pricing.v1.ProductPricingLocalizationMapper;
import com.catalis.core.product.interfaces.dtos.pricing.v1.ProductPricingLocalizationDTO;
import com.catalis.core.product.models.repositories.pricing.v1.ProductPricingLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductPricingLocalizationCreateService {

    @Autowired
    private ProductPricingLocalizationRepository repository;
    
    @Autowired
    private ProductPricingLocalizationMapper mapper;

    /**
     * Creates a new product pricing localization entry by converting the provided DTO to an entity,
     * saving it in the repository, and then mapping the saved entity back to a DTO.
     *
     * @param request the data transfer object containing the product pricing localization information to be created
     * @return a Mono emitting the created ProductPricingLocalizationDTO containing the saved product pricing localization details
     */
    public Mono<ProductPricingLocalizationDTO> createProductPricingLocalization(ProductPricingLocalizationDTO request) {
        return Mono.just(request)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(savedEntity -> {
                    ProductPricingLocalizationDTO dto = mapper.toDto(savedEntity);
                    dto.setProductPricingLocalizationId(savedEntity.getProductPricingLocalizationId());
                    return dto;
                });
    }
    
}
