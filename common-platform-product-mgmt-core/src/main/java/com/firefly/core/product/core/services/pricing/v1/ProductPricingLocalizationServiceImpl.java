package com.firefly.core.product.core.services.pricing.v1;

import com.firefly.common.core.queries.PaginationRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.common.core.queries.PaginationUtils;
import com.firefly.core.product.core.mappers.pricing.v1.ProductPricingLocalizationMapper;
import com.firefly.core.product.interfaces.dtos.pricing.v1.ProductPricingLocalizationDTO;
import com.firefly.core.product.models.entities.pricing.v1.ProductPricingLocalization;
import com.firefly.core.product.models.repositories.pricing.v1.ProductPricingLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductPricingLocalizationServiceImpl implements ProductPricingLocalizationService {

    @Autowired
    private ProductPricingLocalizationRepository repository;

    @Autowired
    private ProductPricingLocalizationMapper mapper;

    @Override
    public Mono<PaginationResponse<ProductPricingLocalizationDTO>> getAllLocalizations(Long pricingId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductPricingId(pricingId, pageable),
                () -> repository.countByProductPricingId(pricingId)
        ).onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve localizations", e)));
    }

    @Override
    public Mono<ProductPricingLocalizationDTO> createLocalization(Long pricingId, ProductPricingLocalizationDTO request) {
        try {
            request.setProductPricingId(pricingId);
            ProductPricingLocalization entity = mapper.toEntity(request);
            return repository.save(entity)
                    .map(mapper::toDto)
                    .onErrorResume(e -> Mono.error(new RuntimeException("Failed to create localization", e)));
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Unexpected error occurred while creating localization", e));
        }
    }

    @Override
    public Mono<ProductPricingLocalizationDTO> getLocalization(Long pricingId, Long localizationId) {
        try {
            return repository.findById(localizationId)
                    .filter(localization -> localization.getProductPricingId().equals(pricingId))
                    .map(mapper::toDto)
                    .switchIfEmpty(Mono.error(new RuntimeException("Localization not found for the provided pricingId and localizationId.")))
                    .onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve localization", e)));
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Unexpected error occurred while retrieving localization", e));
        }
    }

    @Override
    public Mono<ProductPricingLocalizationDTO> updateLocalization(Long pricingId, Long localizationId, ProductPricingLocalizationDTO request) {
        try {
            return repository.findById(localizationId)
                    .filter(localization -> localization.getProductPricingId().equals(pricingId))
                    .flatMap(existingLocalization -> {
                        request.setProductPricingLocalizationId(localizationId);
                        request.setProductPricingId(pricingId);
                        ProductPricingLocalization updatedEntity = mapper.toEntity(request);
                        return repository.save(updatedEntity);
                    })
                    .map(mapper::toDto)
                    .switchIfEmpty(Mono.error(new RuntimeException("Localization not found for the provided pricingId and localizationId.")))
                    .onErrorResume(e -> Mono.error(new RuntimeException("Failed to update localization", e)));
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Unexpected error occurred while updating localization", e));
        }
    }

    @Override
    public Mono<Void> deleteLocalization(Long pricingId, Long localizationId) {
        try {
            return repository.findById(localizationId)
                    .filter(localization -> localization.getProductPricingId().equals(pricingId))
                    .flatMap(repository::delete)
                    .switchIfEmpty(Mono.error(new RuntimeException("Localization not found for the provided pricingId and localizationId.")))
                    .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete localization", e)));
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Unexpected error occurred while deleting localization", e));
        }
    }
}