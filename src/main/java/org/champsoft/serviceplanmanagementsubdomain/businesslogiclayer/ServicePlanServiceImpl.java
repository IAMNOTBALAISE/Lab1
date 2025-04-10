package org.champsoft.serviceplanmanagementsubdomain.businesslogiclayer;


import org.champsoft.serviceplanmanagementsubdomain.dataaccesslayer.ServicePlan;
import org.champsoft.serviceplanmanagementsubdomain.dataaccesslayer.ServicePlanIdentifier;
import org.champsoft.serviceplanmanagementsubdomain.dataaccesslayer.ServicePlanRepository;
import org.champsoft.serviceplanmanagementsubdomain.datamapperlayer.ServicePlanRequestMapper;
import org.champsoft.serviceplanmanagementsubdomain.datamapperlayer.ServicePlanResponseMapper;
import org.champsoft.serviceplanmanagementsubdomain.presentationlayer.ServicePlanRequestModel;
import org.champsoft.serviceplanmanagementsubdomain.presentationlayer.ServicePlanResponseModel;
import org.champsoft.utils.InvalidInputException;
import org.champsoft.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicePlanServiceImpl implements ServicePlanService {

    private final ServicePlanRepository servicePlanRepository;
    private final ServicePlanResponseMapper servicePlanResponseMapper;
    private final ServicePlanRequestMapper servicePlanRequestMapper;

    @Autowired
    public ServicePlanServiceImpl(ServicePlanRepository servicePlanRepository,
                                  ServicePlanResponseMapper servicePlanResponseMapper,
                                  ServicePlanRequestMapper servicePlanRequestMapper) {
        this.servicePlanRepository = servicePlanRepository;
        this.servicePlanResponseMapper = servicePlanResponseMapper;
        this.servicePlanRequestMapper = servicePlanRequestMapper;
    }

    @Override
    public List<ServicePlanResponseModel> getServicePlans() {
        List<ServicePlan> servicePlans = servicePlanRepository.findAll();
        return servicePlanResponseMapper.entityListToResponseModelList(servicePlans);
    }

    @Override
    public ServicePlanResponseModel getServicePlansById(String planId) {
        ServicePlan servicePlan = this.servicePlanRepository.findByServicePlanIdentifier_PlanId(planId);
        if (servicePlan == null) {
            throw new NotFoundException("Service Plan with ID: " + planId + " not found.");
        }
        return servicePlanResponseMapper.entityToResponseModel(servicePlan);
    }

    @Override
    public ServicePlanResponseModel addServicePlan(ServicePlanRequestModel servicePlanRequestModel) {

        ServicePlan existingPlan = servicePlanRepository.findByServicePlanIdentifier_PlanId(servicePlanRequestModel.getPlanId());
        if (existingPlan != null) {
            throw new InvalidInputException("Service Plan with ID: " + servicePlanRequestModel.getPlanId() + " already exists.");
        }

        // Create a new ServicePlanIdentifier
        ServicePlanIdentifier servicePlanIdentifier = new ServicePlanIdentifier(servicePlanRequestModel.getPlanId());

        // Map request to entity
        ServicePlan servicePlan = servicePlanRequestMapper.requestModelToEntity(servicePlanRequestModel);
        servicePlan.setServicePlanIdentifier(servicePlanIdentifier);

        // Save entity
        ServicePlan savedPlan = servicePlanRepository.save(servicePlan);

        return servicePlanResponseMapper.entityToResponseModel(savedPlan);
    }

    @Override
    public ServicePlanResponseModel updateServicePlan(String planId, ServicePlanRequestModel servicePlanRequestModel) {
        // Find existing plan
        ServicePlan existingPlan = servicePlanRepository.findByServicePlanIdentifier_PlanId(planId);
        if (existingPlan == null) {
            throw new NotFoundException("Service Plan with ID: " + planId + " not found.");
        }


        existingPlan.setCoverageDetails(servicePlanRequestModel.getCoverageDetails());
        existingPlan.setExpirationDate(servicePlanRequestModel.getExpirationDate());


        ServicePlan updatedPlan = servicePlanRepository.save(existingPlan);

        return servicePlanResponseMapper.entityToResponseModel(updatedPlan);
    }

    @Override
    public String deleteServicePlanById(String planId) {
        ServicePlan existingPlan = servicePlanRepository.findByServicePlanIdentifier_PlanId(planId);
        if (existingPlan == null) {
            throw new NotFoundException("Service Plan with ID: " + planId + " not found.");
        }

        servicePlanRepository.delete(existingPlan);
        return "Service Plan with ID " + planId + " deleted successfully.";
    }
}
