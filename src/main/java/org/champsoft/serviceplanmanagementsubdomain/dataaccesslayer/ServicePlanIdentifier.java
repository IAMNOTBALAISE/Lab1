package org.champsoft.serviceplanmanagementsubdomain.dataaccesslayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ServicePlanIdentifier {

    @Column(name = "plan_id",nullable = false,unique = true)
    private String planId;

    public ServicePlanIdentifier(String planId) {
        this.planId = (planId != null && !planId.isEmpty()) ? planId : UUID.randomUUID().toString();
    }

}
