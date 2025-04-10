package org.champsoft.productmanagementsubodomain.businesslogiclayer;

import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.Catalog;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.CatalogIdentifier;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.CatalogRepository;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.watch.Watch;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.watch.WatchRepository;
import org.champsoft.productmanagementsubodomain.datamapperlayer.CatalogMapper.CatalogRequestMapper;
import org.champsoft.productmanagementsubodomain.datamapperlayer.CatalogMapper.CatalogResponseMapper;
import org.champsoft.productmanagementsubodomain.presentationlayer.CatalogPresentationLayer.CatalogRequestModel;
import org.champsoft.productmanagementsubodomain.presentationlayer.CatalogPresentationLayer.CatalogResponseModel;
import org.champsoft.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;
    private final CatalogRequestMapper catalogRequestMapper;
    private final CatalogResponseMapper catalogResponseMapper;
    private final WatchRepository watchRepository;

    @Autowired
    public CatalogServiceImpl(CatalogRepository catalogRepository, CatalogRequestMapper catalogRequestMapper, CatalogResponseMapper catalogResponseMapper, WatchRepository watchRepository) {
        this.catalogRepository = catalogRepository;
        this.catalogRequestMapper = catalogRequestMapper;
        this.catalogResponseMapper = catalogResponseMapper;
        this.watchRepository = watchRepository;
    }


    @Override
    public List<CatalogResponseModel> getCatalogs() {

        List<Catalog> catalogs = catalogRepository.findAll();
        return this.catalogResponseMapper.entityListToResponseModelList(catalogs);
    }

    @Override
    public CatalogResponseModel getCatalogById(String catalogId) {

        Catalog foundCatalog = this.catalogRepository.findByCatalogIdentifier_CatalogId(catalogId);
        if(foundCatalog == null) {

            throw new RuntimeException("Catalog not found");
        }else {
            return this.catalogResponseMapper.entityToResponseModel(foundCatalog);
        }
    }


    @Override
    public CatalogResponseModel addCatalog(CatalogRequestModel catalogRequestModel) {

        CatalogIdentifier catalogIdentifier = new CatalogIdentifier(catalogRequestModel.getCatalogId());

        Catalog foundCatalog = this.catalogRepository.findByCatalogIdentifier_CatalogId(catalogIdentifier.getCatalogId());

        if(foundCatalog != null) {
            throw new RuntimeException("Catalog already exists");
        }else {

            Catalog catalog = this.catalogRequestMapper.requestModelToEntity(catalogRequestModel);

            catalog.setCatalogIdentifier(catalogIdentifier);
            catalog.setType(catalogRequestModel.getType());
            catalog.setDescription(catalogRequestModel.getDescription());
            this.catalogRepository.save(catalog);
            return this.catalogResponseMapper.entityToResponseModel(catalog);
        }
    }


    @Override
    public CatalogResponseModel updateCatalog(CatalogRequestModel catalogRequestModel, String catalogId){


        Catalog foundCatalog = this.catalogRepository.findByCatalogIdentifier_CatalogId(catalogId);
        if(foundCatalog == null) {

            throw new RuntimeException("Catalog not found");
        } else if (catalogId == null || catalogId.isEmpty()) {

            throw new IllegalArgumentException("Catalog id cannot be empty in the request bar");
        }




        foundCatalog.setType(catalogRequestModel.getType());
        foundCatalog.setDescription(catalogRequestModel.getDescription());

        Catalog updatedCatalog = this.catalogRepository.save(foundCatalog);

        return this.catalogResponseMapper.entityToResponseModel(updatedCatalog);

    }
    @Override
     public String deleteCatalog(String catalogId){

        Catalog existingCatalog = catalogRepository.findByCatalogIdentifier_CatalogId(catalogId);
        if(existingCatalog == null) {

            throw new NotFoundException("This catalog does not exist");
        }

        List<Watch> watches = watchRepository.findAllByCatalogIdentifier_CatalogId(catalogId);
        watches.forEach(watch -> watchRepository.delete(watch));

        catalogRepository.delete(existingCatalog);

        return "Catalog with id: " + catalogId + " was deleted";


    }

}
