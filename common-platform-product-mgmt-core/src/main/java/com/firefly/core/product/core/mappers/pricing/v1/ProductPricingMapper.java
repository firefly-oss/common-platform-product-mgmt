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


package com.firefly.core.product.core.mappers.pricing.v1;

import com.firefly.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.firefly.core.product.interfaces.dtos.pricing.v1.ProductPricingDTO;
import com.firefly.core.product.models.entities.core.v1.Product;
import com.firefly.core.product.models.entities.pricing.v1.ProductPricing;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductPricingMapper {
    ProductPricingDTO toDto(ProductPricing entity);
    ProductPricing toEntity(ProductPricingDTO dto);

    @Mapping(target = "dateCreated", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductPricingDTO dto, @MappingTarget ProductPricing entity);

}
