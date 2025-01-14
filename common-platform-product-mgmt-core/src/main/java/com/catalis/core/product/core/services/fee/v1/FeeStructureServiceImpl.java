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
public class FeeStructureServiceImpl implements FeeStructureService {

    @Autowired
    private FeeStructureRepository repository;

    @Autowired
    private FeeStructureMapper mapper;

    @Override
    public Mono<FeeStructureDTO> getFeeStructure(Long feeStructureId) {
        return repository.findById(feeStructureId)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Fee structure not found for ID: " + feeStructureId)))
                .onErrorMap(e -> new RuntimeException("Failed to retrieve fee structure with ID: " + feeStructureId, e));
    }

    @Override
    public Mono<FeeStructureDTO> createFeeStructure(FeeStructureDTO feeStructureDTO) {
        FeeStructure entity = mapper.toEntity(feeStructureDTO);
        return repository.save(entity)
                .map(mapper::toDto)
                .onErrorMap(e -> new RuntimeException("Failed to create fee structure", e));
    }

    @Override
    public Mono<FeeStructureDTO> updateFeeStructure(Long feeStructureId, FeeStructureDTO feeStructureDTO) {
        return repository.findById(feeStructureId)
                .switchIfEmpty(Mono.error(new RuntimeException("Fee structure not found for ID: " + feeStructureId)))
                .flatMap(existingEntity -> {
                    FeeStructure updatedEntity = mapper.toEntity(feeStructureDTO);
                    updatedEntity.setFeeStructureId(feeStructureId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDto)
                .onErrorMap(e -> new RuntimeException("Failed to update fee structure with ID: " + feeStructureId, e));
    }

    @Override
    public Mono<Void> deleteFeeStructure(Long feeStructureId) {
        return repository.findById(feeStructureId)
                .switchIfEmpty(Mono.error(new RuntimeException("Fee structure not found for ID: " + feeStructureId)))
                .flatMap(repository::delete)
                .onErrorMap(e -> new RuntimeException("Failed to delete fee structure with ID: " + feeStructureId, e));
    }
}
