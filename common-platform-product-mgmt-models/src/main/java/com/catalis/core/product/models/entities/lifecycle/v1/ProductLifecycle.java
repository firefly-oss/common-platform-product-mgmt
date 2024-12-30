package com.catalis.core.product.models.entities.lifecycle.v1;

import com.catalis.core.product.interfaces.enums.lifecycle.v1.LifecycleStatusEnum;
import com.catalis.core.product.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("product_lifecycle")
public class ProductLifecycle extends BaseEntity {
    @Id
    @Column("product_lifecycle_id")
    private Long productLifecycleId;

    @Column("product_id")
    private Long productId;

    @Column("lifecycle_status")
    private LifecycleStatusEnum lifecycleStatus;

    @Column("status_start_date")
    private LocalDateTime statusStartDate;

    @Column("status_end_date")
    private LocalDateTime statusEndDate;

    @Column("reason")
    private String reason;
}

