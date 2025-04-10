package org.champsoft.productmanagementsubodomain.businesslogiclayer;

import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchRequestModel;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CatalogWatchService {
    
    
    List<WatchResponseModel> getWatchesWithFilter(Map<String, String> queryParams);

    WatchResponseModel getCatalogWatchByID(String watchId);

    List<WatchResponseModel> getWatchesInCatalogWithFiltering(String catalogId, Map<String, String> queryParams);

    WatchResponseModel addWatches(WatchRequestModel watchRequestModel, String catalogId);

    WatchResponseModel updateWatchInInventory(String catalogId, String watchId, WatchRequestModel watchRequestModel);

    String removeWatchInCatalog(String catalogId, String watchId);
}
