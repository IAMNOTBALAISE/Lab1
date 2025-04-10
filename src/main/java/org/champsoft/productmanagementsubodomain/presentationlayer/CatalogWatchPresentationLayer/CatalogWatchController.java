package org.champsoft.productmanagementsubodomain.presentationlayer.CatalogWatchPresentationLayer;


import lombok.extern.slf4j.Slf4j;
import org.champsoft.productmanagementsubodomain.businesslogiclayer.CatalogWatchService;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchRequestModel;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchResponseModel;
import org.champsoft.utils.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/v1/catalogs/{catalog_id}/watches")
public class CatalogWatchController {

    private final CatalogWatchService catalogWatchService;

    public CatalogWatchController(CatalogWatchService catalogWatchService) {
        this.catalogWatchService = catalogWatchService;
    }

    @GetMapping()
    public ResponseEntity<List<WatchResponseModel>> getWatchesInCatalogWithFiltering(@PathVariable("catalog_id") String catalogId, @RequestParam Map<String,String> queryParams) {

        return ResponseEntity.ok().body(catalogWatchService.getWatchesInCatalogWithFiltering(catalogId,queryParams));
    }

    @GetMapping("/{watchId}")
    public ResponseEntity<WatchResponseModel> getWatchInCatalogByWatchId(@PathVariable("watch_id") String watchId) {

        return ResponseEntity.ok().body(catalogWatchService.getCatalogWatchByID(watchId));
    }

    @PostMapping
    public ResponseEntity<WatchResponseModel> addWatches(
            @PathVariable("catalog_id") String catalog_id,
            @RequestBody WatchRequestModel watchRequestModel
    ){

        if(watchRequestModel == null ) {
            throw new IllegalArgumentException("Watch Request Body cannot be null");
        } else if (watchRequestModel.getWatchId() != null && watchRequestModel.getWatchId().length() != 17) {

            throw new InvalidInputException("Invalid Watch ID");

        }

        return ResponseEntity.status(HttpStatus.CREATED).body(catalogWatchService.addWatches(watchRequestModel,catalog_id));
    }

    @PutMapping("/{watchId}")
    public ResponseEntity<WatchResponseModel> updateWatchInCatalog(
            @PathVariable("catalog_id") String catalogId, @PathVariable("watchId") String watchId, @RequestBody WatchRequestModel watchRequestModel
    ){
        return ResponseEntity.ok().body(catalogWatchService.updateWatchInInventory(catalogId,watchId,watchRequestModel));
    }

    @DeleteMapping("/{watchId}")
    ResponseEntity<String> removeWatchInCatalog(@PathVariable("catalog_id") String catalogId, @PathVariable("watchId") String watchId) {

        catalogWatchService.removeWatchInCatalog(catalogId,watchId);

        return ResponseEntity.ok("Watch with Id: " + watchId + " deleted successfully.");
    }







}
