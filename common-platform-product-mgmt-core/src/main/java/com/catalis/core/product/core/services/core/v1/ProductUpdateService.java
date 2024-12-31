package com.catalis.core.product.core.services.core.v1;

import com.catalis.core.product.core.mappers.core.v1.ProductMapper;
import com.catalis.core.product.interfaces.dtos.core.v1.ProductDTO;
import com.catalis.core.product.models.entities.core.v1.Product;
import com.catalis.core.product.models.repositories.product.v1.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductUpdateService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    /**
     * Updates an existing product with the given ID using the provided product details.
     * If the product is not found for the given ID, an error is returned.
     * Null values in the request object are ignored, keeping the existing values in the product.
     *
     * @param id The ID of the product to be updated.
     * @param request The ProductDTO object containing the updated product details.
     * @return A Mono emitting the updated product as a ProductDTO, or an error if the product is not found or an update failure occurs.
     */
    public Mono<ProductDTO> updateProduct(Long id, ProductDTO request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found for ID: " + id)))
                .flatMap(existingProduct -> {
                    Product updatedEntity = mapper.toEntity(request);
                    updatedEntity.setProductSubtypeId(request.getProductSubtypeId() != null ? request.getProductSubtypeId() : existingProduct.getProductSubtypeId());
                    updatedEntity.setProductType(request.getProductType() != null ? request.getProductType() : existingProduct.getProductType());
                    updatedEntity.setProductName(request.getProductName() != null ? request.getProductName() : existingProduct.getProductName());
                    updatedEntity.setProductCode(request.getProductCode() != null ? request.getProductCode() : existingProduct.getProductCode());
                    updatedEntity.setProductDescription(request.getProductDescription() != null ? request.getProductDescription() : existingProduct.getProductDescription());
                    updatedEntity.setProductStatus(request.getProductStatus() != null ? request.getProductStatus() : existingProduct.getProductStatus());
                    updatedEntity.setLaunchDate(request.getLaunchDate() != null ? request.getLaunchDate() : existingProduct.getLaunchDate());
                    updatedEntity.setEndDate(request.getEndDate() != null ? request.getEndDate() : existingProduct.getEndDate());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDto)
                .onErrorMap(e -> new IllegalArgumentException("An error occurred while updating the product: " + e.getMessage(), e));
    }

}