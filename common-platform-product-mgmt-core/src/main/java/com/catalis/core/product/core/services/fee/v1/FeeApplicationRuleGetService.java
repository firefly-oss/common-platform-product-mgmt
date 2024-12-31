package com.catalis.core.product.core.services.fee.v1;

import com.catalis.common.core.queries.PaginationRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.product.core.mappers.fee.v1.FeeApplicationRuleMapper;
import com.catalis.core.product.interfaces.dtos.fee.v1.FeeApplicationRuleDTO;
import com.catalis.core.product.models.entities.fee.v1.FeeApplicationRule;
import com.catalis.core.product.models.repositories.fee.v1.FeeApplicationRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FeeApplicationRuleGetService {

    @Autowired
    private FeeApplicationRuleRepository repository;

    @Autowired
    private FeeApplicationRuleMapper mapper;

    /**
     * Retrieves a FeeApplicationRule identified by its ID and converts it to a FeeApplicationRuleDTO.
     * If the FeeApplicationRule does not exist, an exception is thrown.
     *
     * @param feeApplicationRuleId the ID of the FeeApplicationRule to be retrieved
     * @return a Mono containing the FeeApplicationRuleDTO if the rule exists
     */
    public Mono<FeeApplicationRuleDTO> getFeeApplicationRule(Long feeApplicationRuleId) {
        return repository.findById(feeApplicationRuleId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Fee Application Rule not found for ID: " + feeApplicationRuleId)))
                .map(mapper::toDto);
    }

    /**
     * Retrieves a paginated list of FeeApplicationRuleDTO objects associated with a specific FeeComponent ID.
     * The method applies the pagination settings provided in the PaginationRequest.
     *
     * @param feeComponentId the ID of the FeeComponent used to filter FeeApplicationRule records
     * @param paginationRequest the pagination settings, including page number and page size
     * @return a Mono containing a PaginationResponse with a list of FeeApplicationRuleDTOs,
     *         total count of records, total number of pages, and current page information
     */
    public Mono<PaginationResponse<FeeApplicationRuleDTO>> findByFeeComponentId(Long feeComponentId,
                                                                                PaginationRequest paginationRequest) {

        // Convert PaginationRequest to Pageable for pagination settings
        Pageable pageable = paginationRequest.toPageable();

        // Fetch the paginated list of FeeApplicationRule entities from the repository
        Flux<FeeApplicationRule> rules = repository.findByFeeComponentId(feeComponentId, pageable);

        // Fetch the total count of FeeApplicationRule entities for the given feeComponentId
        Mono<Long> count = repository.countByFeeComponentId(feeComponentId);

        // Transform entities into DTOs, combine with the count, and return a paginated response
        return rules
                // Map each FeeApplicationRule entity to a FeeApplicationRuleDTO using the mapper
                .map(mapper::toDto)

                // Collect all mapped DTOs into a List
                .collectList()

                // Combine the collected list of DTOs with the total count
                .zipWith(count)

                // Generate and return the paginated response object
                .map(tuple -> {
                    List<FeeApplicationRuleDTO> feeApplicationRuleDTOS = tuple.getT1(); // Extract the list of DTOs
                    long total = tuple.getT2(); // Extract the total count

                    // Create and return a PaginationResponse with the list, total count, total pages, and current page
                    return new PaginationResponse<>(
                            feeApplicationRuleDTOS,
                            total,
                            (int) Math.ceil((double) total / pageable.getPageSize()), // Calculate and set total pages
                            pageable.getPageNumber() // Set the current page number
                    );
                });

    }


}
