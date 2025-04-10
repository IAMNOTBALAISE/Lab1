package org.champsoft.productmanagementsubodomain.datamapperlayer.WatchMapper;

import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.Catalog;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.watch.Watch;
import org.champsoft.productmanagementsubodomain.presentationlayer.CatalogPresentationLayer.CatalogController;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchController;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WatchResponseMapper {

    @Mappings({
            @Mapping(expression = "java(watch.getWatchIdentifier().getWatchId())", target = "watchId"),
            @Mapping(expression = "java(watch.getCatalogIdentifier().getCatalogId())", target = "catalogId"),
            @Mapping(target = "accessories", source = "accessories"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "watchBrand", source = "watchBrand")
    })
    WatchResponseModel entityToResponseModel(Watch watch);

    List<WatchResponseModel> entityListToResponseModelList(List<Watch> watches);

    @AfterMapping
    default void addLinks(@MappingTarget WatchResponseModel watchResponseModel, Watch watch) {
        String catalogId = (watch.getCatalogIdentifier() != null) ? watch.getCatalogIdentifier().getCatalogId() : "unknown";
        String watchId = watch.getWatchIdentifier().getWatchId();


        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WatchController.class)
                        .getWatchInCatalogByID(watchId))
                .withSelfRel();


        Link catalogLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CatalogController.class)
                .getCatalogById(catalogId)) // No catalogId, just general filtering
                .withRel("catalog");


        watchResponseModel.add(selfLink, catalogLink);

    }
}
