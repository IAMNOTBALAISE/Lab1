package org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class CatalogIdentifier {


    @Column(name = "catalog_id",nullable = false,unique = true)
    private String catalogId;

    public CatalogIdentifier(String catalogId) {
        this.catalogId = (catalogId != null && !catalogId.isEmpty()) ? catalogId : UUID.randomUUID().toString();
    }


}
