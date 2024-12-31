package com.catalis.core.product.core.services.fee.v1;

import com.catalis.core.product.core.mappers.fee.v1.FeeStructureMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeStructureDTO;
import com.catalis.core.product.models.repositories.fee.v1.FeeStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class FeeStructureUpdateService {

    @Autowired
    private FeeStructureRepository repository;

    @Autowired
    private FeeStructureMapper mapper;

    /**
     * Updates an existing FeeStructure's details based on the provided ID and new FeeStructureDTO information.
     * If the FeeStructure with the specified ID does not exist, an exception is thrown.
     *
     * @param id the ID of the FeeStructure to be updated
     * @param feeStructureDTO the FeeStructureDTO containing the updated details
     * @return a Mono containing the updated FeeStructureDTO after the update operation is completed
     */
    public Mono<FeeStructureDTO> updateFeeStructure(Long id, FeeStructureDTO feeStructureDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Fee Structure not found for ID: " + id)))
                .flatMap(existingStructure -> {
                    existingStructure.setFeeStructureName(feeStructureDTO.getFeeStructureName());
                    existingStructure.setFeeStructureDescription(feeStructureDTO.getFeeStructureDescription());
                    existingStructure.setFeeStructureType(feeStructureDTO.getFeeStructureType());
                    return repository.save(existingStructure);
                })
                .map(mapper::toDto);
    }
    
}
