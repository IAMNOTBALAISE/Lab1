package org.champsoft.serviceplanmanagementsubdomain.businesslogiclayer;

import lombok.extern.slf4j.Slf4j;
import org.champsoft.serviceplanmanagementsubdomain.presentationlayer.ServicePlanRequestModel;
import org.champsoft.serviceplanmanagementsubdomain.presentationlayer.ServicePlanResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicePlanService {
    
    
    List<ServicePlanResponseModel> getServicePlans();

    ServicePlanResponseModel getServicePlansById(String planId);

    ServicePlanResponseModel addServicePlan(ServicePlanRequestModel servicePlanRequestModel);

    ServicePlanResponseModel updateServicePlan(String planId, ServicePlanRequestModel servicePlanRequestModel);

    String deleteServicePlanById(String planId);
}
