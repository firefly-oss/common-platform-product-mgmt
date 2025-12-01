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


package com.firefly.core.product.core.services.impl;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.product.core.mappers.ProductMapper;
import com.firefly.core.product.core.services.ProductService;
import com.firefly.core.product.interfaces.dtos.ProductDTO;
import com.firefly.core.product.models.entities.Product;
import com.firefly.core.product.models.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public Mono<PaginationResponse<ProductDTO>> filterProducts(FilterRequest<ProductDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Product.class,
                        mapper::toDto
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<ProductDTO> createProduct(ProductDTO productDTO) {
        if (productDTO.getTenantId() == null) {
            return Mono.error(new RuntimeException("Tenant ID is required when creating a product"));
        }
        return Mono.just(productDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    @Override
    public Mono<ProductDTO> getProductById(UUID productId) {
        return repository.findById(productId)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found with ID: " + productId)))
                .map(mapper::toDto);
    }

    @Override
    public Flux<ProductDTO> getProductsByTenantId(UUID tenantId) {
        return repository.findByTenantId(tenantId)
                .map(mapper::toDto);
    }

    @Override
    public Mono<ProductDTO> updateProduct(UUID productId, ProductDTO productDTO) {
        return repository.findById(productId)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found with ID: " + productId)))
                .flatMap(existingProduct -> {
                    mapper.updateEntityFromDto(productDTO, existingProduct);
                    return repository.save(existingProduct);
                })
                .map(mapper::toDto);
    }

    @Override
    public Mono<Void> deleteProduct(UUID productId) {
        return repository.findById(productId)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found with ID: " + productId)))
                .flatMap(product -> repository.deleteById(productId));
    }
}
