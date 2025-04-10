package org.champsoft.productmanagementsubodomain.businesslogiclayer;


import org.champsoft.ordermanagementsubdomain.dataaccesslayer.OrderRepository;
import org.champsoft.ordermanagementsubdomain.dataaccesslayer.OrderStatus;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.Catalog;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.CatalogRepository;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.watch.*;
import org.champsoft.productmanagementsubodomain.datamapperlayer.WatchMapper.WatchRequestMapper;
import org.champsoft.productmanagementsubodomain.datamapperlayer.WatchMapper.WatchResponseMapper;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchRequestModel;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchResponseModel;
import org.champsoft.utils.InvalidInputException;
import org.champsoft.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CatalogWatchServiceImpl implements CatalogWatchService {

    private final CatalogRepository catalogRepository;

    private final WatchRepository watchRepository;

    private final WatchResponseMapper watchResponseMapper;

    private final WatchRequestMapper watchRequestMapper;

    public final OrderRepository orderRepository;


    @Autowired
    public CatalogWatchServiceImpl(CatalogRepository catalogRepository, WatchRepository watchRepository, WatchResponseMapper watchResponseMapper, WatchRequestMapper watchRequestMapper, OrderRepository orderRepository) {
        this.catalogRepository = catalogRepository;
        this.watchRepository = watchRepository;
        this.watchResponseMapper = watchResponseMapper;
        this.watchRequestMapper = watchRequestMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<WatchResponseModel> getWatchesInCatalogWithFiltering(String catalogId, Map<String, String> queryParams) {


        String watchStatus = queryParams.get("status");
        String usageType = queryParams.get("usage");

        if (!catalogRepository.existsByCatalogIdentifier_CatalogId(catalogId)) {
            throw new InvalidInputException("Catalog does not exist");
        }

        Map<String, WatchStatus> statusMap = new HashMap<String, WatchStatus>();
        statusMap.put("available", WatchStatus.AVAILABLE);
        statusMap.put("sale_pending", WatchStatus.SALE_PENDING);
        statusMap.put("sold_out", WatchStatus.SOLD_OUT);


        Map<String, UsageType> usageTypeMap = new HashMap<String, UsageType>();
        usageTypeMap.put("new", UsageType.NEW);
        usageTypeMap.put("used", UsageType.USED);

        WatchStatus watchStatusEnum = null;
        if (watchStatus != null) {
            watchStatusEnum = statusMap.get(watchStatus.toLowerCase());
        }

        UsageType usageTypeEnum = null;
        if (usageType != null) {
            usageTypeEnum = usageTypeMap.get(usageType.toLowerCase());
        }

        if (watchStatusEnum != null && usageTypeEnum != null) {
            return watchResponseMapper.entityListToResponseModelList(
                    watchRepository.findAllByCatalogIdentifier_CatalogIdAndWatchStatusEqualsAndUsageTypeEquals(
                            catalogId, watchStatusEnum, usageTypeEnum
                    )
            );
        }

        if (watchStatusEnum != null) {
            return watchResponseMapper.entityListToResponseModelList(
                    watchRepository.findAllByCatalogIdentifier_CatalogIdAndWatchStatusEquals(
                            catalogId, watchStatusEnum
                    )
            );
        }

        if (usageTypeEnum != null) {
            return watchResponseMapper.entityListToResponseModelList(
                    watchRepository.findAllByCatalogIdentifier_CatalogIdAndUsageTypeEquals(
                            catalogId, usageTypeEnum
                    )
            );
        }


        return watchResponseMapper.entityListToResponseModelList(
                watchRepository.findAllByCatalogIdentifier_CatalogId(catalogId));
    }

    @Override
    public List<WatchResponseModel> getWatchesWithFilter(Map<String, String> queryParams) {

        return watchResponseMapper.entityListToResponseModelList(watchRepository.findAll());
    }

    @Override
    public WatchResponseModel getCatalogWatchByID(String watchId) {

        Watch watch = watchRepository.findByWatchIdentifier_WatchId(watchId);

        if (watch == null) {
            throw new InvalidInputException("Watch does not exist");
        }
        return watchResponseMapper.entityToResponseModel(watch);
    }

    @Override
    public WatchResponseModel addWatches(WatchRequestModel watchRequestModel, String catalogId) {

        Catalog existingCatalog = catalogRepository.findByCatalogIdentifier_CatalogId(catalogId);
        if (existingCatalog == null) {
            throw new InvalidInputException("Catalog does not exist");
        }

        String watchId = watchRequestModel.getWatchId();
        if (watchId == null || watchId.isEmpty()) {

            watchId = new WatchIdentifier().generateRandomWatchId();
        }

        if (watchRepository.findByWatchIdentifier_WatchId(watchId) != null) {
            throw new InvalidInputException("Watch already exists");
        }

        WatchIdentifier watchIdentifier = new WatchIdentifier(watchId);

        WatchBrand watchBrand = watchRequestModel.getWatchBrand();

        BigDecimal totalAccessoryCost = BigDecimal.ZERO;
        if (watchRequestModel.getAccessories() != null) {

            totalAccessoryCost = watchRequestModel.getAccessories().stream()
                    .map(Accessory::getAccessoryCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        Price price = new Price(
                watchRequestModel.getPrice().getMsrp(),
                watchRequestModel.getPrice().getCost(),
                totalAccessoryCost
        );

        Watch watch = watchRequestMapper.requestModelToEntity(
                watchRequestModel,
                watchIdentifier,
                existingCatalog.getCatalogIdentifier(),
                price,
                watchBrand

        );

        watchRepository.save(watch);

        return watchResponseMapper.entityToResponseModel(watch);
    }


    @Override
    public WatchResponseModel updateWatchInInventory(String catalogId, String watchId, WatchRequestModel watchRequestModel) {

        Catalog existingCatalog = catalogRepository.findByCatalogIdentifier_CatalogId(catalogId);
        if (existingCatalog == null) {
            throw new InvalidInputException("Catalog does not exist");
        }


        Watch existingWatch = watchRepository.findByWatchIdentifier_WatchId(watchId);
        if (existingWatch == null) {
            throw new NotFoundException("Unknown watch Id provided : " + watchId);
        }


        if (existingWatch.getWatchStatus() == WatchStatus.SOLD_OUT) {
            boolean isOrderCanceled = orderRepository.existsByWatchIdentifier_WatchIdAndOrderStatus(
                    watchId, OrderStatus.PURCHASE_CANCELED);


            if (!isOrderCanceled && watchRequestModel.getWatchStatus() != WatchStatus.SOLD_OUT) {
                throw new InvalidInputException("A watch marked as SOLD_OUT cannot be made AVAILABLE or changed to another status unless the order is canceled.");
            }
        }


        BigDecimal totalAccessoryCost = watchRequestModel.getAccessories().stream()
                .map(Accessory::getAccessoryCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        Price price = new Price(
                watchRequestModel.getPrice().getMsrp(),
                watchRequestModel.getPrice().getCost(),
                totalAccessoryCost
        );

        WatchBrand watchBrand = watchRequestModel.getWatchBrand();


        Watch watch = watchRequestMapper.requestModelToEntity(
                watchRequestModel,
                existingWatch.getWatchIdentifier(),
                existingWatch.getCatalogIdentifier(),
                price,
                watchBrand
        );


        watch.setId(existingWatch.getId());


        return watchResponseMapper.entityToResponseModel(watchRepository.save(watch));

        }





    @Override
    public  String removeWatchInCatalog(String catalogId, String watchId){

        Catalog existingCatalog = catalogRepository.findByCatalogIdentifier_CatalogId(catalogId);
        if(existingCatalog == null){
            throw new InvalidInputException("Catalog does not exist");
        }
        Watch existingWatch = watchRepository.findByWatchIdentifier_WatchId(watchId);
        if(existingWatch == null){
            throw new NotFoundException("Unknown watch Id provided : " + existingWatch);

        }
        watchRepository.delete(existingWatch);
        return "Watch with ID" + watchId + " was successfully removed";
    }

}
