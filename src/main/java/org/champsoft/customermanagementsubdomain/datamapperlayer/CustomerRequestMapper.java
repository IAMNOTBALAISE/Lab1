package org.champsoft.customermanagementsubdomain.datamapperlayer;


import org.champsoft.customermanagementsubdomain.dataaccesslayer.Customer;
import org.champsoft.customermanagementsubdomain.presentationlayer.CustomerRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerIdentifier.customerId", source = "customerId")
    @Mapping(target = "customerAddress.streetAddress", source = "streetAddress")
    @Mapping(target = "customerAddress.postalCode", source = "postalCode")
    @Mapping(target = "customerAddress.city", source = "city")
    @Mapping(target = "customerAddress.province", source = "province")
    @Mapping(target = "phoneNumbers", source = "phoneNumbers")
    Customer requestModelToEntity(CustomerRequestModel customerRequestModel);
    List<Customer> requestModelListToEntityList(List<CustomerRequestModel> customerRequestModel);


}
