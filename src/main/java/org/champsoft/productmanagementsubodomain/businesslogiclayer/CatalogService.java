package org.champsoft.productmanagementsubodomain.businesslogiclayer;


import org.champsoft.productmanagementsubodomain.presentationlayer.CatalogPresentationLayer.CatalogRequestModel;
import org.champsoft.productmanagementsubodomain.presentationlayer.CatalogPresentationLayer.CatalogResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CatalogService {
    
    
    List<CatalogResponseModel> getCatalogs();

    CatalogResponseModel getCatalogById(String catalogId);

    CatalogResponseModel addCatalog(CatalogRequestModel catalogRequestModel);

    CatalogResponseModel updateCatalog(CatalogRequestModel catalogRequestModel, String catalogId);

    String deleteCatalog(String catalogId);
}
