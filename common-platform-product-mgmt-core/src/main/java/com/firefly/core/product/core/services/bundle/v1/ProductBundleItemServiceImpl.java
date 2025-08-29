package com.firefly.core.product.core.services.bundle.v1;

import com.firefly.core.product.core.mappers.bundle.v1.ProductBundleItemMapper;
import com.firefly.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import com.firefly.core.product.models.entities.bundle.v1.ProductBundleItem;
import com.firefly.core.product.models.repositories.bundle.v1.ProductBundleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductBundleItemServiceImpl implements ProductBundleItemService {

    @Autowired
    private ProductBundleItemRepository repository;

    @Autowired
    private ProductBundleService bundleService;

    @Autowired
    private ProductBundleItemMapper mapper;

    @Override
    public Mono<ProductBundleItemDTO> getItem(Long bundleId, Long itemId) {
        return bundleService.getById(bundleId)
                .flatMap(bundle -> repository.findById(itemId))
                .filter(item -> item.getProductBundleId().equals(bundleId))
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product bundle item not found")));
    }

    @Override
    public Mono<ProductBundleItemDTO> createItem(Long bundleId, ProductBundleItemDTO request) {
        return bundleService.getById(bundleId)
                .flatMap(bundle -> {
                    ProductBundleItem entity = mapper.toEntity(request);
                    entity.setProductBundleId(bundleId);
                    return repository.save(entity);
                })
                .map(mapper::toDto);
    }

    @Override
    public Mono<ProductBundleItemDTO> updateItem(Long bundleId, Long itemId, ProductBundleItemDTO request) {
        return bundleService.getById(bundleId)
                .flatMap(bundle -> repository.findById(itemId))
                .filter(item -> item.getProductBundleId().equals(bundleId))
                .flatMap(existing -> {
                    existing.setProductId(request.getProductId());
                    existing.setSpecialConditions(request.getSpecialConditions());
                    return repository.save(existing);
                })
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product bundle item not found")));
    }

    @Override
    public Mono<Void> deleteItem(Long bundleId, Long itemId) {
        return bundleService.getById(bundleId)
                .flatMap(bundle -> repository.findById(itemId))
                .filter(item -> item.getProductBundleId().equals(bundleId))
                .flatMap(repository::delete);
    }
}
