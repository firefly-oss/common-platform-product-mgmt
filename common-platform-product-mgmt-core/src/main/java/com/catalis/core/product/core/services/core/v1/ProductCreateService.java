package com.catalis.core.product.core.services.core.v1;

import com.catalis.core.product.core.mappers.core.v1.ProductMapper;
import com.catalis.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.catalis.core.product.models.repositories.product.v1.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductCreateService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    /**
     * Creates a new product by converting the provided ProductDTO into an entity,
     * saving it in the repository, and then mapping the saved entity back to a DTO.
     *
     * @param request the ProductDTO containing the details of the product to be created
     * @return a Mono emitting the created ProductDTO with its generated productId
     */
    public Mono<ProductDTO> createProduct(ProductDTO request) {
        return Mono.just(request)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(savedEntity -> {
                    ProductDTO productDTO = mapper.toDto(savedEntity);
                    productDTO.setProductId(savedEntity.getProductId());
                    return productDTO;
                });
    }

}
