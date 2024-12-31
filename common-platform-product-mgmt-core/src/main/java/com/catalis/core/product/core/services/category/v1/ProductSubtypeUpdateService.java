package com.catalis.core.product.core.services.category.v1;

import com.catalis.core.product.core.mappers.category.v1.ProductSubtypeMapper;
import com.catalis.core.product.interfaces.dtos.category.v1.ProductSubtypeDTO;
import com.catalis.core.product.models.repositories.category.v1.ProductSubtypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductSubtypeUpdateService {

    @Autowired
    private ProductSubtypeRepository repository;

    @Autowired
    private ProductSubtypeMapper mapper;

    /**
     * Updates an existing product subtype identified by its unique ID with the details
     * provided in the request. If the product subtype with the specified ID does not
     * exist, an error is returned. Any errors encountered during the process are wrapped
     * and propagated.
     *
     * @param id the unique identifier of the product subtype to be updated
     * @param request the ProductSubtypeDTO containing the updated details of the product subtype
     * @return a Mono emitting the updated ProductSubtypeDTO upon successful update,
     *         or an error if the update fails or the product subtype is not found
     */
    public Mono<ProductSubtypeDTO> update(Long id, ProductSubtypeDTO request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product subtype not found for ID: " + id)))
                .flatMap(existingSubtype -> {
                    existingSubtype.setProductCategoryId(request.getProductCategoryId());
                    existingSubtype.setSubtypeName(request.getSubtypeName());
                    existingSubtype.setSubtypeDescription(request.getSubtypeDescription());
                    return repository.save(existingSubtype);
                })
                .map(mapper::toDto)
                .onErrorMap(ex -> new RuntimeException("An error occurred while updating the product subtype.", ex));
    }

}