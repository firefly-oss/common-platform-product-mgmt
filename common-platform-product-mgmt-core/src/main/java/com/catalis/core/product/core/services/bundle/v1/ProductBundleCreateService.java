package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.core.product.core.mappers.bundle.v1.ProductBundleMapper;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
import com.catalis.core.product.models.repositories.bundle.v1.ProductBundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductBundleCreateService {

    @Autowired
    private ProductBundleRepository repository;

    @Autowired
    private ProductBundleMapper mapper;

    /**
     * Creates a new ProductBundle using the given {@link ProductBundleDTO}.
     *
     * @param request the ProductBundleDTO containing the details of the product bundle to be created; must have a non-null bundle name
     * @return a Mono emitting the created ProductBundleDTO
     * @throws IllegalArgumentException if the provided ProductBundleDTO is null or missing a bundle name
     * @throws RuntimeException         if there is a failure during the creation process
     */
    public Mono<ProductBundleDTO> create(ProductBundleDTO request) {
        return Mono.just(request)
                .filter(dto -> dto != null && dto.getBundleName() != null)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid ProductBundleDTO: bundleName is required")))
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(savedEntity -> {
                    ProductBundleDTO dto = mapper.toDto(savedEntity);
                    dto.setProductBundleId(savedEntity.getProductBundleId());
                    return dto;
                })
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to create ProductBundle", e)));
    }

}
