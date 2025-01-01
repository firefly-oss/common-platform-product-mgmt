package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.fee.v1.ProductFeeStructureMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.ProductFeeStructureDTO;
import com.catalis.core.product.models.entities.fee.v1.ProductFeeStructure;
import com.catalis.core.product.models.repositories.fee.v1.ProductFeeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductFeeStructureGetService {

    @Autowired
    private ProductFeeStructureRepository repository;

    @Autowired
    private ProductFeeStructureMapper mapper;

    /**
     * Retrieves a ProductFeeStructure identified by its ID and converts it to a ProductFeeStructureDTO.
     * If the ProductFeeStructure does not exist, an exception is thrown.
     *
     * @param productFeeStructureId the ID of the ProductFeeStructure to be retrieved
     * @return a Mono containing the ProductFeeStructureDTO if the structure exists
     */
    public Mono<ProductFeeStructureDTO> getProductFeeStructure(Long productFeeStructureId) {
        return repository.findById(productFeeStructureId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Fee Structure not found for ID: " + productFeeStructureId)))
                .map(mapper::toDto);
    }

    /**
     * Retrieves a paginated list of ProductFeeStructureDTOs associated with a specified product ID.
     *
     * @param productId the unique identifier of the product whose associated fee structures are to be retrieved
     * @param paginationRequest the pagination request containing pagination details such as page size and page number
     * @return a Mono emitting a PaginationResponse containing a list of ProductFeeStructureDTOs and pagination metadata
     */
    public Mono<PaginationResponse<ProductFeeStructureDTO>> findByProductId(Long productId,
                                                                            PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByProductId(productId, pageable),
                () -> repository.countByProductId(productId)
        );

    }

}
