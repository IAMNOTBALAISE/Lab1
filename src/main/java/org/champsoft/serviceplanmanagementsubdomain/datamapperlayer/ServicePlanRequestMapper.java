package org.champsoft.serviceplanmanagementsubdomain.datamapperlayer;

import org.champsoft.serviceplanmanagementsubdomain.dataaccesslayer.ServicePlan;
import org.champsoft.serviceplanmanagementsubdomain.presentationlayer.ServicePlanRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServicePlanRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "servicePlanIdentifier", ignore = true)
    ServicePlan requestModelToEntity(ServicePlanRequestModel servicePlanRequestModel);

}
