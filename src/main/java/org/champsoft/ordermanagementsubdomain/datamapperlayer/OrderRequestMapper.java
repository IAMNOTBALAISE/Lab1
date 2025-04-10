package org.champsoft.ordermanagementsubdomain.datamapperlayer;

import org.champsoft.ordermanagementsubdomain.dataaccesslayer.Order;
import org.champsoft.ordermanagementsubdomain.presentationlayer.OrderRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper {

    @Mapping(target = "id", ignore = true) // Auto-generated ID
    @Mapping(target = "orderIdentifier", ignore = true) // Will be set in service
    @Mapping(target = "customerIdentifier", ignore = true) // Retrieved from request
    @Mapping(target = "watchIdentifier", ignore = true) // Retrieved from request
    @Mapping(target = "servicePlanIdentifier", ignore = true) // Retrieved from request
    @Mapping(target = "catalogIdentifier", ignore = true)
  //  @Mapping(target = "orderStatus", expression = "java(orderRequestModel.getOrderStatus())")
    @Mapping(target = "price", ignore = true) // Will be set separately
    @Mapping(target = "currency", ignore = true) // Will be set separately
    @Mapping(target = "orderStatus", ignore = true) // Default status will be assigned in service
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())") // Assign current date-time
    Order requestModelToEntity(OrderRequestModel orderRequestModel);
}
