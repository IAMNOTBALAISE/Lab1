package org.champsoft.serviceplanmanagementsubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServicePlanRequestModel {

    private String planId;
    private String coverageDetails;
    private LocalDate expirationDate;
}
