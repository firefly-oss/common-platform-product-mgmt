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


package com.firefly.core.product.core.services.category.v1;

import com.firefly.core.product.core.mappers.category.v1.ProductCategoryMapper;
import com.firefly.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
import com.firefly.core.product.models.entities.category.v1.ProductCategory;
import com.firefly.core.product.models.repositories.category.v1.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Autowired
    private ProductCategoryMapper mapper;

    @Override
    public Mono<ProductCategoryDTO> getById(UUID categoryId) {
        return repository.findById(categoryId)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product category not found for the given ID")));
    }

    @Override
    public Mono<ProductCategoryDTO> create(ProductCategoryDTO categoryDTO) {
        ProductCategory entity = mapper.toEntity(categoryDTO);
        return repository.save(entity)
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to create product category", e)));
    }

    @Override
    public Mono<ProductCategoryDTO> update(UUID categoryId, ProductCategoryDTO categoryDTO) {
        return repository.findById(categoryId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product category not found for the given ID")))
                .flatMap(existingEntity -> {
                    existingEntity.setCategoryName(categoryDTO.getCategoryName());
                    existingEntity.setCategoryDescription(categoryDTO.getCategoryDescription());
                    existingEntity.setParentCategoryId(categoryDTO.getParentCategoryId());
                    return repository.save(existingEntity)
                            .map(mapper::toDto)
                            .onErrorResume(e -> Mono.error(new RuntimeException("Failed to update product category", e)));
                });
    }

    @Override
    public Mono<Void> delete(UUID categoryId) {
        return repository.findById(categoryId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product category not found for the given ID")))
                .flatMap(existingEntity -> repository.delete(existingEntity)
                        .onErrorResume(e -> Mono.error(new RuntimeException("Failed to delete product category", e))));
    }
}