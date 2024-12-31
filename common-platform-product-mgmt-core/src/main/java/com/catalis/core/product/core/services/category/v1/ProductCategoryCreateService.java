package com.catalis.core.product.core.services.category.v1;


import com.catalis.core.product.core.mappers.category.v1.ProductCategoryMapper;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductCategoryDTO;
import com.catalis.core.product.models.repositories.category.v1.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductCategoryCreateService {

    @Autowired
    private ProductCategoryRepository repository;

    @Autowired
    private ProductCategoryMapper mapper;

    /**
     * Creates a new ProductCategory entity from the given ProductCategoryDTO request,
     * saves it to the repository, and returns the saved ProductCategoryDTO.
     *
     * @param request the ProductCategoryDTO containing the details of the category to be created
     * @return a Mono emitting the saved ProductCategoryDTO with updated identifiers
     */
    public Mono<ProductCategoryDTO> create(ProductCategoryDTO request) {
        return Mono.justOrEmpty(request)
                .map(mapper::toEntity)
                .flatMap(entity -> repository.save(entity))
                .map(savedEntity -> {
                    ProductCategoryDTO dto = mapper.toDto(savedEntity);
                    dto.setProductCategoryId(savedEntity.getProductCategoryId());
                    return dto;
                });
    }

}