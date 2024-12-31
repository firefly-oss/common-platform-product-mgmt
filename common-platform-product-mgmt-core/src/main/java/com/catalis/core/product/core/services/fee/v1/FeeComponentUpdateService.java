package com.catalis.core.product.core.services.fee.v1;

import com.catalis.core.product.core.mappers.fee.v1.FeeComponentMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeComponentDTO;
import com.catalis.core.product.models.repositories.fee.v1.FeeComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class FeeComponentUpdateService {

    @Autowired
    private FeeComponentRepository repository;

    @Autowired
    private FeeComponentMapper mapper;

    /**
     * Updates an existing FeeComponent identified by the given ID with the data provided in the FeeComponentDTO.
     *
     * If the specified FeeComponent is not found, an error is returned. The method maps the updated entity to a DTO
     * and handles any errors during the update process.
     *
     * @param id the ID of the FeeComponent to be updated
     * @param feeComponentDTO the DTO containing the updated data for the FeeComponent
     * @return a Mono containing the updated FeeComponentDTO after successful update
     *         or an error if the update operation fails
     */
    public Mono<FeeComponentDTO> updateFeeComponent(Long id, FeeComponentDTO feeComponentDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Fee Component not found for ID: " + id)))
                .flatMap(existingEntity -> {
                    existingEntity.setFeeStructureId(feeComponentDTO.getFeeStructureId());
                    existingEntity.setFeeType(feeComponentDTO.getFeeType());
                    existingEntity.setFeeDescription(feeComponentDTO.getFeeDescription());
                    existingEntity.setFeeAmount(feeComponentDTO.getFeeAmount());
                    existingEntity.setFeeUnit(feeComponentDTO.getFeeUnit());
                    existingEntity.setApplicableConditions(feeComponentDTO.getApplicableConditions());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDto)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to update Fee Component", e)));
    }
    
}
