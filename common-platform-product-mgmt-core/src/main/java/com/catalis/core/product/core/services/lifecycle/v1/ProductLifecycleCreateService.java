package com.catalis.core.product.core.services.lifecycle.v1;


import com.catalis.core.product.core.mappers.lifecycle.v1.ProductLifecycleMapper;
import com.catalis.core.product.interfaces.dtos.lifecycle.v1.ProductLifecycleDTO;
import com.catalis.core.product.models.repositories.lifecycle.v1.ProductLifecycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductLifecycleCreateService {
    
    @Autowired
    private ProductLifecycleRepository repository;
    
    @Autowired
    private ProductLifecycleMapper mapper;

    /**
     * Creates a new product lifecycle record in the system, maps the input DTO to
     * an entity for persistence, and converts the saved entity back to a DTO for response.
     *
     * @param request the ProductLifecycleDTO containing the details of the product lifecycle to be created
     * @return a Mono containing the created ProductLifecycleDTO with updated fields, such as the generated productLifecycleId
     */
    public Mono<ProductLifecycleDTO> createProductLifecycle(ProductLifecycleDTO request) {
        return Mono.just(request)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(savedEntity -> {
                    ProductLifecycleDTO dto = mapper.toDto(savedEntity);
                    dto.setProductLifecycleId(savedEntity.getProductLifecycleId());
                    return dto;
                });
    }

}
