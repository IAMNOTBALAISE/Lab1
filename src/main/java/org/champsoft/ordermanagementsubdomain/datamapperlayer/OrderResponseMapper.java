package org.champsoft.ordermanagementsubdomain.datamapperlayer;


import org.champsoft.customermanagementsubdomain.presentationlayer.CustomerController;
import org.champsoft.ordermanagementsubdomain.dataaccesslayer.Order;
import org.champsoft.ordermanagementsubdomain.presentationlayer.OrderController;
import org.champsoft.ordermanagementsubdomain.presentationlayer.OrderResponseModel;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchController;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper {

    @Mapping(target = "orderId", expression = "java(order.getOrderIdentifier().getOrderId())")
    @Mapping(target = "customerId", expression = "java(order.getCustomerIdentifier().getCustomerId())")
    @Mapping(target = "watchId", expression = "java(order.getWatchIdentifier().getWatchId())")
    @Mapping(target = "catalogId", expression = "java(order.getCatalogIdentifier().getCatalogId())")
    @Mapping(target = "salePrice", expression = "java(order.getPrice().getAmount() != null ? order.getPrice().getAmount().doubleValue() : null)")
    @Mapping(target = "servicePlanId", expression = "java(order.getServicePlanIdentifier().getPlanId())")
    @Mapping(target = "currency", expression = "java(order.getPrice().getCurrency().toString())")
    @Mapping(target = "paymentCurrency", expression = "java(order.getPrice().getPaymentCurrency().toString())")
    @Mapping(target = "orderDate", expression = "java(order.getOrderDate())")
    OrderResponseModel entityToResponseModel(Order order);

    List<OrderResponseModel> entityToResponseModelList(List<Order> orders);

    @AfterMapping
    default void addLinks(@MappingTarget OrderResponseModel orderResponseModel,Order order) {

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                .getOrderById(order.getOrderIdentifier().getOrderId())).withSelfRel();

        Link customerLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class)
                .getCustomerById(order.getCustomerIdentifier().getCustomerId())).withRel("customer");

        Link watchLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WatchController.class)
        .getWatchInCatalogByID(order.getWatchIdentifier().getWatchId())).withRel("watch");

        orderResponseModel.add(selfLink,customerLink,watchLink);
    }

}
