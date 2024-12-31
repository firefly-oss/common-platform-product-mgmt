package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.core.product.core.mappers.bundle.v1.ProductBundleItemMapper;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleItemDTO;
import com.catalis.core.product.models.repositories.bundle.v1.ProductBundleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductBundleItemCreateService {

    @Autowired
    private ProductBundleItemRepository productBundleItemRepository;

    @Autowired
    private ProductBundleItemMapper productBundleItemMapper;

    /**
     * Creates a new ProductBundleItem using the given {@link ProductBundleItemDTO}.
     * Validates that the provided ProductBundleItemDTO is not null and that it contains
     * non-null productBundleId and productId before proceeding with the creation process.
     * Maps the DTO to an entity, saves it to the repository, and converts it back to a DTO.
     * Ensures that the returned DTO includes the generated ID after the insertion in the database.
     * If any error occurs during validation, mapping, saving, or conversion, an appropriate
     * RuntimeException is emitted.
     *
     * @param request the ProductBundleItemDTO containing the details of the product bundle item to be created;
     *                must have non-null productBundleId and productId
     * @return a Mono emitting the created ProductBundleItemDTO
     * @throws IllegalArgumentException if the provided ProductBundleItemDTO is null or missing
     *                                  productBundleId or productId
     * @throws RuntimeException         if there is a failure during the creation process
     */
    public Mono<ProductBundleItemDTO> create(ProductBundleItemDTO request) {
        return Mono.just(request)
                .filter(dto -> dto != null && dto.getProductBundleId() != null && dto.getProductId() != null)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid ProductBundleItemDTO: productBundleId and productId are required")))
                .map(productBundleItemMapper::toEntity)
                .flatMap(entity -> productBundleItemRepository.save(entity))
                .flatMap(savedEntity -> {
                    ProductBundleItemDTO dto = productBundleItemMapper.toDto(savedEntity);
                    dto.setProductBundleItemId(savedEntity.getProductBundleItemId());
                    return Mono.just(dto);
                })
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to create ProductBundleItem", e)));
    }
    
}