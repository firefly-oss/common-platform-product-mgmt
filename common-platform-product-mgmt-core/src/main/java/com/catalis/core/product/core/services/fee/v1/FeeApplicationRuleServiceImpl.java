package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.common.core.queries.PaginationUtils;
import com.catalis.core.product.core.mappers.fee.v1.FeeApplicationRuleMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeApplicationRuleDTO;
import com.catalis.core.product.models.entities.fee.v1.FeeApplicationRule;
import com.catalis.core.product.models.repositories.fee.v1.FeeApplicationRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class FeeApplicationRuleServiceImpl implements FeeApplicationRuleService {

    @Autowired
    private FeeApplicationRuleRepository repository;

    @Autowired
    private FeeApplicationRuleMapper mapper;

    @Override
    public Mono<PaginationResponse<FeeApplicationRuleDTO>> getRulesByComponentId(Long feeStructureId, Long componentId, PaginationRequest paginationRequest) {
        return PaginationUtils.paginateQuery(
                paginationRequest,
                mapper::toDto,
                pageable -> repository.findByFeeComponentId(componentId, pageable),
                () -> repository.countByFeeComponentId(componentId)
        ).onErrorMap(e -> new RuntimeException("Failed to retrieve rules by component ID", e));
    }

    @Override
    public Mono<FeeApplicationRuleDTO> createRule(Long feeStructureId, Long componentId, FeeApplicationRuleDTO ruleDTO) {
        FeeApplicationRule entity = mapper.toEntity(ruleDTO);
        entity.setFeeComponentId(componentId);
        return repository.save(entity)
                .map(mapper::toDto)
                .onErrorMap(e -> new RuntimeException("Failed to create rule", e));
    }

    @Override
    public Mono<FeeApplicationRuleDTO> getRule(Long feeStructureId, Long componentId, Long ruleId) {
        return repository.findById(ruleId)
                .filter(rule -> rule.getFeeComponentId().equals(componentId))
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Rule not found or does not belong to the component")))
                .onErrorMap(e -> new RuntimeException("Failed to retrieve rule", e));
    }

    @Override
    public Mono<FeeApplicationRuleDTO> updateRule(Long feeStructureId, Long componentId, Long ruleId, FeeApplicationRuleDTO ruleDTO) {
        return repository.findById(ruleId)
                .filter(rule -> rule.getFeeComponentId().equals(componentId))
                .switchIfEmpty(Mono.error(new RuntimeException("Rule not found or does not belong to the component")))
                .flatMap(existingRule -> {
                    FeeApplicationRule updatedEntity = mapper.toEntity(ruleDTO);
                    updatedEntity.setFeeApplicationRuleId(ruleId);
                    updatedEntity.setFeeComponentId(componentId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDto)
                .onErrorMap(e -> new RuntimeException("Failed to update rule", e));
    }

    @Override
    public Mono<Void> deleteRule(Long feeStructureId, Long componentId, Long ruleId) {
        return repository.findById(ruleId)
                .filter(rule -> rule.getFeeComponentId().equals(componentId))
                .switchIfEmpty(Mono.error(new RuntimeException("Rule not found or does not belong to the component")))
                .flatMap(repository::delete)
                .onErrorMap(e -> new RuntimeException("Failed to delete rule", e));
    }
}