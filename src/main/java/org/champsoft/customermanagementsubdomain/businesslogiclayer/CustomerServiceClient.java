package org.champsoft.customermanagementsubdomain.businesslogiclayer;




import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.champsoft.customermanagementsubdomain.presentationlayer.CustomerResponseModel;
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
public class CustomerServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String CLIENT_SERVICE_BASE_URL;


    public CustomerServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper) {

        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        String customerServiceHost = "localhost";
        String customerServicePort = "8080";
        CLIENT_SERVICE_BASE_URL = "http://" + customerServiceHost + ":" + customerServicePort + "/api/v1/customers";

    }

    public CustomerResponseModel getCustomerByCustomerId(String customerId) {

        try {
            String url = CLIENT_SERVICE_BASE_URL + "/" + customerId;
            CustomerResponseModel clientResponseModel = restTemplate.getForObject(url,
                    CustomerResponseModel.class);
            return clientResponseModel;
        }
        catch (HttpClientErrorException ex) {
            //log.debug("5. Received in API-Gateway Client Service Client getAllClients exception: " + ex.getMessage());
            throw handleHttpClientException(ex);
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {

        //include all possible responses from the client
        if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY )) {
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
}
