package org.champsoft.serviceplanmanagementsubdomain.presentationlayer;

import org.champsoft.serviceplanmanagementsubdomain.businesslogiclayer.ServicePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
public class ServicePlanController {

    private final ServicePlanService servicePlanService;

    @Autowired
    public ServicePlanController(ServicePlanService servicePlanService) {
        this.servicePlanService = servicePlanService;
    }

    @GetMapping()
    public ResponseEntity<List<ServicePlanResponseModel>> getServicePlans() {

        return ResponseEntity.ok(this.servicePlanService.getServicePlans());
    }

    @GetMapping("/{plan_id}")
    public ResponseEntity<ServicePlanResponseModel> getServicePlanById(@PathVariable String plan_id) {

        return ResponseEntity.ok(this.servicePlanService.getServicePlansById(plan_id));

    }

    @PostMapping()
    public ResponseEntity<ServicePlanResponseModel> addServicePlan(@RequestBody ServicePlanRequestModel servicePlanRequestModel) {

        return ResponseEntity.ok(this.servicePlanService.addServicePlan(servicePlanRequestModel));
    }

    @PutMapping("/{plan_id}")
    public ResponseEntity<ServicePlanResponseModel> updateServicePlan(@PathVariable String plan_id, @RequestBody ServicePlanRequestModel servicePlanRequestModel) {

        return ResponseEntity.ok(this.servicePlanService.updateServicePlan(plan_id,servicePlanRequestModel));

    }

    @DeleteMapping("/{plan_id}")
    public ResponseEntity<String> deleteServicePlanById(@PathVariable String plan_id) {

        return ResponseEntity.ok(this.servicePlanService.deleteServicePlanById(plan_id));
    }
}
