package org.champsoft.productmanagementsubodomain.datamapperlayer.WatchMapper;


import org.champsoft.productmanagementsubodomain.dataaccesslayer.catalog.CatalogIdentifier;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.watch.Price;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.watch.Watch;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.watch.WatchBrand;
import org.champsoft.productmanagementsubodomain.dataaccesslayer.watch.WatchIdentifier;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WatchRequestMapper {

    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(expression = "java(watchIdentifier)", target = "watchIdentifier"),
            @Mapping(expression = "java(catalogIdentifier)",target = "catalogIdentifier"),
            @Mapping(expression = "java(price)",target = "price"),
            @Mapping(expression = "java(watchBrand)",target = "watchBrand")
    })

    Watch requestModelToEntity(WatchRequestModel watchRequestModel, WatchIdentifier watchIdentifier, CatalogIdentifier catalogIdentifier, Price price, WatchBrand watchBrand);
}
