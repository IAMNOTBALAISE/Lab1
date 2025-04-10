package org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Integer> {

Catalog findByCatalogIdentifier_CatalogId(String catalogId);

boolean existsByCatalogIdentifier_CatalogId(String catalogId);

}
