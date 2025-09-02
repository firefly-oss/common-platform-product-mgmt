package com.firefly.core.product.models.repositories.fee.v1;

import com.firefly.core.product.models.entities.fee.v1.FeeStructure;
import com.firefly.core.product.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface FeeStructureRepository extends BaseRepository<FeeStructure, UUID> {
}