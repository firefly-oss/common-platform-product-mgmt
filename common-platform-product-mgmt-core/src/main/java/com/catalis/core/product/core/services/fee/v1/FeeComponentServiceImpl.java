package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.fee.v1.FeeComponentMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeComponentDTO;
import com.catalis.core.product.models.entities.fee.v1.FeeComponent;
import com.catalis.core.product.models.repositories.fee.v1.FeeComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class FeeComponentServiceImpl implements FeeComponentService {

    @Autowired
    private FeeComponentRepository repository;

    @Autowired
    private FeeComponentMapper mapper;

    @Override
    public Mono<PaginationResponse<FeeComponentDTO>> getByFeeStructureId(Long feeStructureId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByFeeStructureId(feeStructureId, pageable),
                () -> repository.countByFeeStructureId(feeStructureId)
        ).onErrorMap(e -> new RuntimeException("Failed to retrieve fee components for fee structure ID: " + feeStructureId, e));
    }

    @Override
    public Mono<FeeComponentDTO> createFeeComponent(Long feeStructureId, FeeComponentDTO feeComponentDTO) {
        FeeComponent entity = mapper.toEntity(feeComponentDTO);
        entity.setFeeStructureId(feeStructureId);
        return repository.save(entity)
                .map(mapper::toDto)
                .onErrorMap(e -> new RuntimeException("Failed to create fee component under fee structure ID: " + feeStructureId, e));
    }

    @Override
    public Mono<FeeComponentDTO> getFeeComponent(Long feeStructureId, Long componentId) {
        return repository.findById(componentId)
                .filter(feeComponent -> feeStructureId.equals(feeComponent.getFeeStructureId()))
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Fee component not found for component ID: " + componentId + " under fee structure ID: " + feeStructureId)))
                .onErrorMap(e -> new RuntimeException("Failed to retrieve fee component with component ID: " + componentId, e));
    }

    @Override
    public Mono<FeeComponentDTO> updateFeeComponent(Long feeStructureId, Long componentId, FeeComponentDTO feeComponentDTO) {
        return repository.findById(componentId)
                .filter(feeComponent -> feeStructureId.equals(feeComponent.getFeeStructureId()))
                .switchIfEmpty(Mono.error(new RuntimeException("Fee component not found for component ID: " + componentId + " under fee structure ID: " + feeStructureId)))
                .flatMap(existingComponent -> {
                    FeeComponent updatedComponent = mapper.toEntity(feeComponentDTO);
                    updatedComponent.setFeeStructureId(feeStructureId);
                    updatedComponent.setFeeComponentId(componentId);
                    return repository.save(updatedComponent);
                })
                .map(mapper::toDto)
                .onErrorMap(e -> new RuntimeException("Failed to update fee component with component ID: " + componentId, e));
    }

    @Override
    public Mono<Void> deleteFeeComponent(Long feeStructureId, Long componentId) {
        return repository.findById(componentId)
                .filter(feeComponent -> feeStructureId.equals(feeComponent.getFeeStructureId()))
                .switchIfEmpty(Mono.error(new RuntimeException("Fee component not found for component ID: " + componentId + " under fee structure ID: " + feeStructureId)))
                .flatMap(repository::delete)
                .onErrorMap(e -> new RuntimeException("Failed to delete fee component with component ID: " + componentId, e));
    }
}
