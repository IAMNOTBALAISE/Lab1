package org.champsoft.serviceplanmanagementsubdomain.datamapperlayer;

import org.champsoft.serviceplanmanagementsubdomain.dataaccesslayer.ServicePlan;
import org.champsoft.serviceplanmanagementsubdomain.presentationlayer.ServicePlanResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServicePlanResponseMapper {

    @Mapping(expression = "java(servicePlan.getServicePlanIdentifier().getPlanId())", target = "planId")
    ServicePlanResponseModel entityToResponseModel(ServicePlan servicePlan);

    List<ServicePlanResponseModel> entityListToResponseModelList(List<ServicePlan> servicePlan);
}
