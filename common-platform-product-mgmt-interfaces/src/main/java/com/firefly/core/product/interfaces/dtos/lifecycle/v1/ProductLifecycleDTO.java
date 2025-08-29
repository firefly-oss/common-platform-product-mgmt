package com.firefly.core.product.interfaces.dtos.lifecycle.v1;

import com.firefly.core.product.interfaces.dtos.BaseDTO;
import com.firefly.core.product.interfaces.enums.lifecycle.v1.LifecycleStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductLifecycleDTO extends BaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long productLifecycleId;

    private Long productId;
    private LifecycleStatusEnum lifecycleStatus;
    private LocalDateTime statusStartDate;
    private LocalDateTime statusEndDate;
    private String reason;
}