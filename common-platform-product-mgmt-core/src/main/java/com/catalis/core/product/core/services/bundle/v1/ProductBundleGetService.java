package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.bundle.v1.ProductBundleMapper;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
import com.catalis.core.product.models.repositories.bundle.v1.ProductBundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
public class ProductBundleGetService {

    @Autowired
    private ProductBundleRepository repository;

    @Autowired
    private ProductBundleMapper mapper;

    /**
     * Retrieves a ProductBundle by its ID and maps it to a ProductBundleDTO.
     * If no ProductBundle is found with the specified ID, an error is emitted.
     * Any failures during retrieval will result in an error being emitted.
     *
     * @param id the ID of the ProductBundle to be retrieved
     * @return a Mono emitting the retrieved ProductBundleDTO, or an error if the ProductBundle is not found
     *         or if there is a failure during the retrieval process
     * @throws IllegalArgumentException if the ProductBundle with the given ID does not exist
     * @throws RuntimeException if there is a failure during the retrieval process
     */
    private Mono<ProductBundleDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ProductBundle with id " + id + " not found")))
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve ProductBundle", e)));
    }

    private Mono<PaginationResponse<ProductBundleDTO>> getAll(PaginationRequest paginationRequest) {
        return null;
        // TODO: Pending implementation
    }

}
