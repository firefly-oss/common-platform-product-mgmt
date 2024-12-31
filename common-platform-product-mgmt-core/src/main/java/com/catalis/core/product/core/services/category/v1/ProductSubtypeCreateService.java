package com.catalis.core.product.core.services.category.v1;

import com.catalis.core.product.core.mappers.category.v1.ProductSubtypeMapper;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductSubtypeDTO;
import com.catalis.core.product.models.repositories.category.v1.ProductSubtypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductSubtypeCreateService {

    @Autowired
    private ProductSubtypeRepository repository;
    
    @Autowired
    private ProductSubtypeMapper mapper;

    /**
     * Creates a new product subtype record by saving the provided ProductSubtypeDTO object
     * to the database and returns the saved DTO with its generated identifier.
     *
     * @param request the ProductSubtypeDTO object to be created and saved
     * @return a Mono emitting the saved ProductSubtypeDTO object with its generated identifier
     */
    public Mono<ProductSubtypeDTO> create(ProductSubtypeDTO request) {
        return Mono.justOrEmpty(request)
                .map(mapper::toEntity)
                .flatMap(entity -> repository.save(entity))
                .map(savedEntity -> {
                    ProductSubtypeDTO dto = mapper.toDto(savedEntity);
                    dto.setProductSubtypeId(savedEntity.getProductSubtypeId());
                    return dto;
                });
    }

}
