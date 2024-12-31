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
public class ProductFeeStructureCreateService {

    @Autowired
    private ProductFeeStructureRepository repository;

    @Autowired
    private ProductFeeStructureMapper mapper;

    /**
     * Creates a new ProductFeeStructure and returns its corresponding DTO.
     * This method saves the given ProductFeeStructureDTO by converting it into an entity,
     * persists it in the repository, and retrieves the saved entity to convert it back into its DTO representation.
     *
     * @param request the ProductFeeStructureDTO containing the details of the product fee structure to be created
     * @return a Mono containing the ProductFeeStructureDTO of the created entity
     */
    public Mono<ProductFeeStructureDTO> createProductFeeStructure(ProductFeeStructureDTO request) {
        return repository.save(mapper.toEntity(request))
                .flatMap(savedEntity -> repository.findById(savedEntity.getProductFeeStructureId())
                        .map(mapper::toDto));
    }

}
