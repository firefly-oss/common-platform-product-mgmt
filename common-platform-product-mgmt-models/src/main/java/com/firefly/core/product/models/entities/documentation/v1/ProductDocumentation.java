package com.firefly.core.product.models.entities.documentation.v1;

import com.firefly.core.product.interfaces.enums.documentation.v1.DocTypeEnum;
import com.firefly.core.product.models.entities.BaseEntity;
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
@Table("product_documentation")
public class ProductDocumentation extends BaseEntity {
    @Id
    @Column("product_documentation_id")
    private Long productDocumentationId;

    @Column("product_id")
    private Long productId;

    @Column("doc_type")
    private DocTypeEnum docType;

    @Column("document_manager_ref")
    private Long documentManagerRef;

    @Column("date_added")
    private LocalDateTime dateAdded;
}
