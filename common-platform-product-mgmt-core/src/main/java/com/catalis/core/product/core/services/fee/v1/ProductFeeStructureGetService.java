package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.fee.v1.ProductFeeStructureMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.ProductFeeStructureDTO;
import com.catalis.core.product.models.repositories.fee.v1.ProductFeeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

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
    
    public Mono<PaginationResponse<ProductFeeStructureDTO>> findByProductId(Long productId, PaginationRequest pagination){
        return null;
        //TODO: Pending implementation
    }

}
