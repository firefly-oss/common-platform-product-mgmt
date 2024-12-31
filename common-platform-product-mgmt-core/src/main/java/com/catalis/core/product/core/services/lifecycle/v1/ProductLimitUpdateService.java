package com.catalis.core.product.core.services.lifecycle.v1;

import com.catalis.core.product.core.mappers.lifecycle.v1.ProductLimitMapper;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLimitDTO;
import com.catalis.core.product.models.repositories.lifecycle.v1.ProductLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductLimitUpdateService {

    @Autowired
    private ProductLimitRepository repository;

    @Autowired
    private ProductLimitMapper mapper;

    /**
     * Updates an existing product limit record in the repository with the details provided in the ProductLimitDTO.
     * If the provided ID does not correspond to an existing record, an error is emitted.
     * The updated entity is then mapped to a ProductLimitDTO and returned.
     *
     * @param id the unique identifier of the product limit to update
     * @param productLimit the ProductLimitDTO containing the updated details of the product limit
     * @return a Mono containing the updated ProductLimitDTO, or an error if the record with the given ID does not exist
     */
    public Mono<ProductLimitDTO> updateProductLimit(Long id, ProductLimitDTO productLimit) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Limit not found for ID: " + id)))
                .flatMap(existingEntity -> {
                    existingEntity.setProductId(productLimit.getProductId());
                    existingEntity.setLimitType(productLimit.getLimitType());
                    existingEntity.setLimitValue(productLimit.getLimitValue());
                    existingEntity.setLimitUnit(productLimit.getLimitUnit());
                    existingEntity.setTimePeriod(productLimit.getTimePeriod());
                    existingEntity.setEffectiveDate(productLimit.getEffectiveDate());
                    existingEntity.setExpiryDate(productLimit.getExpiryDate());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDto);
    }

}
