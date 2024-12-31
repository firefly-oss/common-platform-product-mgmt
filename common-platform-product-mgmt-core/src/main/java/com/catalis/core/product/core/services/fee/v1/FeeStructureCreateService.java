package com.catalis.core.product.core.services.fee.v1;

import com.catalis.core.product.core.mappers.fee.v1.FeeStructureMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeStructureDTO;
import com.catalis.core.product.models.entities.fee.v1.FeeStructure;
import com.catalis.core.product.models.repositories.fee.v1.FeeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class FeeStructureCreateService {

    @Autowired
    private FeeStructureRepository repository;
    
    @Autowired
    private FeeStructureMapper mapper;

    /**
     * Creates a new FeeStructure and returns the corresponding FeeStructureDTO.
     * This method saves the provided FeeStructure entity in the repository and retrieves the saved entity
     * from the database to convert it into its DTO representation.
     *
     * @param feeStructure the FeeStructure entity to be created
     * @return a Mono containing the FeeStructureDTO of the created entity
     */
    public Mono<FeeStructureDTO> createFeeStructure(FeeStructure feeStructure) {
        return repository.save(feeStructure)
                .flatMap(savedEntity -> repository.findById(savedEntity.getFeeStructureId())
                        .map(mapper::toDto));
    }

}
