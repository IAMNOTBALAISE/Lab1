package org.champsoft.serviceplanmanagementsubdomain.businesslogiclayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.champsoft.serviceplanmanagementsubdomain.presentationlayer.ServicePlanResponseModel;
import org.champsoft.utils.HttpErrorInfo;
import org.champsoft.utils.InvalidInputException;
import org.champsoft.utils.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Component
public class ServicePlanServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String SERVICE_PLAN_SERVICE_BASE_URL;

    public ServicePlanServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        String servicePlanServiceHost = "localhost";
        String servicePlanServicePort = "8080";
        SERVICE_PLAN_SERVICE_BASE_URL = "http://" + servicePlanServiceHost + ":" + servicePlanServicePort + "/api/v1/plans";
    }


    public ServicePlanResponseModel getServicePlanById(String planId) {
        try {
            String url = SERVICE_PLAN_SERVICE_BASE_URL + "/" + planId;
            return restTemplate.getForObject(url, ServicePlanResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            return new InvalidInputException(getErrorMessage(ex));
        }

        log.warn("Unexpected HTTP error: {}, response: {}", ex.getStatusCode(), ex.getResponseBodyAsString());
        return ex;
    }


    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ioex.getMessage();
        }
    }

}
