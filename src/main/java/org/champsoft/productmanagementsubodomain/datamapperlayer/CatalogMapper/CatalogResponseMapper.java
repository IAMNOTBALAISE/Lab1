package org.champsoft.productmanagementsubodomain.datamapperlayer.CatalogMapper;


import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.Catalog;
import org.champsoft.productmanagementsubodomain.presentationlayer.CatalogPresentationLayer.CatalogResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CatalogResponseMapper {

    @Mapping(expression = "java(catalog.getCatalogIdentifier().getCatalogId())",target = "catalogId")
    CatalogResponseModel entityToResponseModel(Catalog catalog);

    List<CatalogResponseModel> entityListToResponseModelList(List<Catalog> catalogs);
}
