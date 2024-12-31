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
     * Creates a new FeeComponent and returns the corresponding FeeComponentDTO.
     * This method saves the provided FeeComponent entity in the repository and retrieves the saved entity
     * from the database to convert it into its DTO representation.
     *
     * @param feeComponent the FeeComponent entity to be created
     * @return a Mono containing the FeeComponentDTO of the created entity
     */
    public Mono<FeeComponentDTO> createFeeComponent(FeeComponent feeComponent) {
        return repository.save(feeComponent)
                .flatMap(savedEntity -> repository.findById(savedEntity.getFeeComponentId())
                        .map(mapper::toDto));
    }
    
}
