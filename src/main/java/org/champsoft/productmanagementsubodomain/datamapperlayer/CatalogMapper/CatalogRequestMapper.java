package org.champsoft.productmanagementsubodomain.datamapperlayer.CatalogMapper;

import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.Catalog;
import org.champsoft.productmanagementsubodomain.presentationlayer.CatalogPresentationLayer.CatalogRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CatalogRequestMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "catalogIdentifier",ignore = true)
    Catalog requestModelToEntity(CatalogRequestModel catalogRequestModel);
}
