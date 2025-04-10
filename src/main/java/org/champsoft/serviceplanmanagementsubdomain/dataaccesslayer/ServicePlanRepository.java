package org.champsoft.serviceplanmanagementsubdomain.dataaccesslayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePlanRepository extends JpaRepository<ServicePlan, Integer> {

    ServicePlan findByServicePlanIdentifier_PlanId(String servicePlanIdentifier);


}
