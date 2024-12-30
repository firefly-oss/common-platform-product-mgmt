package com.catalis.core.product.core.mappers.fee.v1;

import com.catalis.core.product.interfaces.dtos.fee.v1.FeeApplicationRuleDTO;
import com.catalis.core.product.models.entities.fee.v1.FeeApplicationRule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeeApplicationRuleMapper {
    FeeApplicationRuleDTO toDto(FeeApplicationRule entity);
    FeeApplicationRule toEntity(FeeApplicationRuleDTO dto);
}