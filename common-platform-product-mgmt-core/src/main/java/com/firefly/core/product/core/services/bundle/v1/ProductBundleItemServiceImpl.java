/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.product.core.services.bundle.v1;

import com.firefly.core.product.core.mappers.bundle.v1.ProductBundleItemMapper;
import com.firefly.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import com.firefly.core.product.models.entities.bundle.v1.ProductBundleItem;
import com.firefly.core.product.models.repositories.bundle.v1.ProductBundleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

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
    public Mono<ProductBundleItemDTO> getItem(UUID bundleId, UUID itemId) {
        return bundleService.getById(bundleId)
                .flatMap(bundle -> repository.findById(itemId))
                .filter(item -> item.getProductBundleId().equals(bundleId))
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product bundle item not found")));
    }

    @Override
    public Mono<ProductBundleItemDTO> createItem(UUID bundleId, ProductBundleItemDTO request) {
        return bundleService.getById(bundleId)
                .flatMap(bundle -> {
                    ProductBundleItem entity = mapper.toEntity(request);
                    entity.setProductBundleId(bundleId);
                    return repository.save(entity);
                })
                .map(mapper::toDto);
    }

    @Override
    public Mono<ProductBundleItemDTO> updateItem(UUID bundleId, UUID itemId, ProductBundleItemDTO request) {
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
    public Mono<Void> deleteItem(UUID bundleId, UUID itemId) {
        return bundleService.getById(bundleId)
                .flatMap(bundle -> repository.findById(itemId))
                .filter(item -> item.getProductBundleId().equals(bundleId))
                .flatMap(repository::delete);
    }
}
