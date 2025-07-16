package com.catalis.core.product.models.entities.documentation.v1;

import com.catalis.core.product.interfaces.enums.documentation.v1.ContractingDocTypeEnum;
import com.catalis.core.product.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entity representing a documentation requirement for a product during the contracting/opening phase.
 * This defines which documents are required from customers to complete the contracting process.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("product_documentation_requirement")
public class ProductDocumentationRequirement extends BaseEntity {
    
    @Id
    @Column("product_doc_requirement_id")
    private Long productDocRequirementId;
    
    @Column("product_id")
    private Long productId;
    
    @Column("doc_type")
    private ContractingDocTypeEnum docType;
    
    @Column("is_mandatory")
    private Boolean isMandatory;
    
    @Column("description")
    private String description;
}