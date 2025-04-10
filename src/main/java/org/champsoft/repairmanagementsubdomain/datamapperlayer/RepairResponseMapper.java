package org.champsoft.repairmanagementsubdomain.datamapperlayer;

import org.champsoft.ordermanagementsubdomain.dataaccesslayer.Order;
import org.champsoft.ordermanagementsubdomain.presentationlayer.OrderController;
import org.champsoft.repairmanagementsubdomain.dataaccesslayer.Repair;
import org.champsoft.repairmanagementsubdomain.presentationlayer.RepairController;
import org.champsoft.repairmanagementsubdomain.presentationlayer.RepairResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RepairResponseMapper {

    @Mappings({
            @Mapping(target = "repairId", expression = "java(repair.getRepairIdentifier().getRepairId())"),
            @Mapping(target = "orderId", expression = "java(repair.getOrderIdentifier().getOrderId())"),
            @Mapping(target = "amount", expression = "java(repair.getRepairCost().getAmount())"), // Extract amount
            @Mapping(target = "currency", expression = "java(repair.getRepairCost().getCurrency())"), // Extract currency
            @Mapping(target = "repairStatus", source = "repairStatus") // Directly map status
    })
    RepairResponseModel entityToResponseModel(Repair repair);

    List<RepairResponseModel> entityListToResponseModelList(List<Repair> repairs);

    @AfterMapping
    default void addLinks(@MappingTarget RepairResponseModel responseModel, Repair repair) {

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RepairController.class)
                .getRepairById(repair.getRepairIdentifier().getRepairId())).withSelfRel();


        Link orderLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                .getOrderById(repair.getOrderIdentifier().getOrderId())).withRel("order");

        responseModel.add(selfLink, orderLink);
    }

}
