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
public class ProductLimitCreateService {

    @Autowired
    private ProductLimitRepository repository;
    
    @Autowired
    private ProductLimitMapper mapper;

    /**
     * Creates a new product limit record by mapping the provided DTO to an entity,
     * saving it in the repository, and then mapping the saved entity back to a DTO
     * with updated fields such as the generated productLimitId.
     *
     * @param productLimit the ProductLimitDTO containing the details of the product limit to be created
     * @return a Mono containing the created ProductLimitDTO with updated fields, including the generated productLimitId
     */
    public Mono<ProductLimitDTO> createProductLimit(ProductLimitDTO productLimit) {
        return Mono.just(productLimit)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(savedEntity -> {
                    ProductLimitDTO dto = mapper.toDto(savedEntity);
                    dto.setProductLimitId(savedEntity.getProductLimitId());
                    return dto;
                });
    }

}
