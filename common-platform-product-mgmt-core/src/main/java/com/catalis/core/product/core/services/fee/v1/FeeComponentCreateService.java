package com.catalis.core.product.core.services.fee.v1;

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
public class FeeComponentCreateService {
    
    @Autowired
    private FeeComponentRepository repository;
    
    @Autowired
    private FeeComponentMapper mapper;


    /**
     * Creates a new Fee Component record in the database based on the provided FeeComponentDTO.
     * The method converts the FeeComponentDTO to an entity, saves it, and retrieves the saved record
     * to convert it back to a DTO for returning.
     *
     * @param feeComponent the FeeComponentDTO containing the details of the Fee Component to be created
     * @return a Mono emitting the created FeeComponentDTO after it has been persisted in the database
     */
    public Mono<FeeComponentDTO> createFeeComponent(FeeComponentDTO feeComponent) {
        FeeComponent entity = mapper.toEntity(feeComponent);
        return repository.save(entity)
                .flatMap(savedEntity -> repository.findById(savedEntity.getFeeComponentId())
                        .map(mapper::toDto));
    }
    
}
