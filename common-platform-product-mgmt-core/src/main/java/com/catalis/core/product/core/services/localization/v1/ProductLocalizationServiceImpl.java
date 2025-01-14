package com.catalis.core.product.core.services.localization.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.localization.v1.ProductLocalizationMapper;
import com.catalis.core.product.interfaces.dtos.localization.v1.ProductLocalizationDTO;
import com.catalis.core.product.models.entities.localization.v1.ProductLocalization;
import com.catalis.core.product.models.repositories.localization.v1.ProductLocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductLocalizationServiceImpl implements ProductLocalizationService {

    @Autowired
    private ProductLocalizationRepository repository;

    @Autowired
    private ProductLocalizationMapper mapper;

    @Override
    public Mono<PaginationResponse<ProductLocalizationDTO>> getAllLocalizations(Long productId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findAllByProductId(productId, pageable)
                        .onErrorMap(error -> new RuntimeException("Failed to fetch localizations", error)),
                () -> repository.countByProductId(productId)
                        .onErrorMap(error -> new RuntimeException("Failed to count localizations", error))
        );
    }

    @Override
    public Mono<ProductLocalizationDTO> createLocalization(Long productId, ProductLocalizationDTO localizationDTO) {
        ProductLocalization entity = mapper.toEntity(localizationDTO);
        entity.setProductId(productId);
        return repository.save(entity)
                .map(mapper::toDto)
                .onErrorMap(error -> new RuntimeException("Failed to create localization", error));
    }

    @Override
    public Mono<ProductLocalizationDTO> getLocalizationById(Long productId, Long localizationId) {
        return repository.findById(localizationId)
                .filter(entity -> entity.getProductId().equals(productId))
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Localization not found")))
                .onErrorMap(error -> new RuntimeException("Failed to fetch localization by ID", error));
    }

    @Override
    public Mono<ProductLocalizationDTO> updateLocalization(Long productId, Long localizationId, ProductLocalizationDTO localizationDTO) {
        return repository.findById(localizationId)
                .filter(entity -> entity.getProductId().equals(productId))
                .switchIfEmpty(Mono.error(new RuntimeException("Localization not found for update")))
                .flatMap(existingEntity -> {
                    existingEntity.setLanguageCode(localizationDTO.getLanguageCode());
                    existingEntity.setLocalizedName(localizationDTO.getLocalizedName());
                    existingEntity.setLocalizedDescription(localizationDTO.getLocalizedDescription());
                    return repository.save(existingEntity)
                            .onErrorMap(error -> new RuntimeException("Failed to update localization", error));
                })
                .map(mapper::toDto);
    }

    @Override
    public Mono<Void> deleteLocalization(Long productId, Long localizationId) {
        return repository.findById(localizationId)
                .filter(entity -> entity.getProductId().equals(productId))
                .switchIfEmpty(Mono.error(new RuntimeException("Localization not found for deletion")))
                .flatMap(repository::delete)
                .onErrorMap(error -> new RuntimeException("Failed to delete localization", error));
    }
}