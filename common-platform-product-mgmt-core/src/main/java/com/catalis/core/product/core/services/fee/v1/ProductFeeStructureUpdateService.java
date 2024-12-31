package com.catalis.core.product.core.services.fee.v1;

import com.catalis.core.product.core.mappers.fee.v1.ProductFeeStructureMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.ProductFeeStructureDTO;
import com.catalis.core.product.models.repositories.fee.v1.ProductFeeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductFeeStructureUpdateService {

    @Autowired
    private ProductFeeStructureRepository repository;

    @Autowired
    private ProductFeeStructureMapper mapper;

    /**
     * Updates an existing Product Fee Structure based on the provided ID and request data.
     * If the Product Fee Structure is not found, an error is returned.
     *
     * @param id the ID of the Product Fee Structure to be updated
     * @param request the data to update the Product Fee Structure with
     *                containing the new productId, feeStructureId, and priority
     * @return a Mono containing the updated ProductFeeStructureDTO object
     *         or an error if the update fails
     */
    public Mono<ProductFeeStructureDTO> updateProductFeeStructure(Long id, ProductFeeStructureDTO request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product Fee Structure not found for ID: " + id)))
                .flatMap(existingEntity -> {
                    existingEntity.setProductId(request.getProductId());
                    existingEntity.setFeeStructureId(request.getFeeStructureId());
                    existingEntity.setPriority(request.getPriority());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to update Product Fee Structure", e)));
    }
    
}
