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
     * Creates a new Fee Structure based on the provided FeeStructureDTO.
     * Converts the DTO to a FeeStructure entity, saves it to the database,
     * and retrieves the saved entity as a FeeStructureDTO.
     *
     * @param feeStructure the FeeStructureDTO containing details for the Fee Structure to be created
     * @return a Mono emitting the created FeeStructureDTO after being successfully stored and retrieved
     */
    public Mono<FeeStructureDTO> createFeeStructure(FeeStructureDTO feeStructure) {
        FeeStructure entity = mapper.toEntity(feeStructure);
        return repository.save(entity)
                .flatMap(savedEntity -> repository.findById(savedEntity.getFeeStructureId())
                        .map(mapper::toDto));
    }

}
