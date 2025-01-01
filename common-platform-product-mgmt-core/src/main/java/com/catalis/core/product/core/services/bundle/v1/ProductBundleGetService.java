package com.catalis.core.product.core.services.bundle.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.bundle.v1.ProductBundleMapper;
import com.catalis.core.product.interfaces.dtos.bundle.v1.ProductBundleDTO;
import com.catalis.core.product.models.entities.bundle.v1.ProductBundle;
import com.catalis.core.product.models.repositories.bundle.v1.ProductBundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
    public Mono<ProductBundleDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ProductBundle with id " + id + " not found")))
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to retrieve ProductBundle", e)));
    }


    /**
     * Retrieves all product bundles with pagination support and maps them to a {@link ProductBundleDTO}.
     * The method applies the pagination criteria, converts the retrieved entities to DTOs, and calculates the total count.
     *
     * @param paginationRequest the pagination information including page number, page size, and sorting details
     * @return a {@link Mono} emitting a {@link PaginationResponse} containing the paginated list of {@link ProductBundleDTO}
     *         and the total number of product bundles
     */
    public Mono<PaginationResponse<ProductBundleDTO>> getAllProductBundles(PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                repository::findAllBy,
                repository::count
        );
    }

}