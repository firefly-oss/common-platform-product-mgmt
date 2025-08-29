package com.firefly.core.product.core.mappers.fee.v1;

import com.firefly.core.product.interfaces.dtos.fee.v1.FeeApplicationRuleDTO;
import com.firefly.core.product.models.entities.fee.v1.FeeApplicationRule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeeApplicationRuleMapper {
    FeeApplicationRuleDTO toDto(FeeApplicationRule entity);
    FeeApplicationRule toEntity(FeeApplicationRuleDTO dto);
}