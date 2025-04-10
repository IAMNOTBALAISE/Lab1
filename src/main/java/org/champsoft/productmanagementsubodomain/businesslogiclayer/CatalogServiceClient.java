package org.champsoft.productmanagementsubodomain.businesslogiclayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchRequestModel;
import org.champsoft.productmanagementsubodomain.presentationlayer.WatchPresentationLayer.WatchResponseModel;
import org.champsoft.utils.HttpErrorInfo;
import org.champsoft.utils.InvalidInputException;
import org.champsoft.utils.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@Component
public class CatalogServiceClient {


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String CATALOG_SERVICE_BASE_URL;
    private final String catalogServiceHost;
    private final String catalogServicePort;

    public CatalogServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.catalogServiceHost = "localhost";
        this.catalogServicePort = "8080";
        CATALOG_SERVICE_BASE_URL = "http://" + catalogServiceHost + ":" + catalogServicePort + "/api/v1/catalogs";
    }


    public WatchResponseModel getWatchByWatchId(String catalogId,String watchId) {
        try {
            String url = "http://" + catalogServiceHost + ":" + catalogServicePort + "/api/v1/watches/" + watchId;
            WatchResponseModel watchResponseModel = restTemplate.getForObject(url, WatchResponseModel.class);
            return watchResponseModel;
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    public void updateWatchStatus(WatchRequestModel watchRequestModel,String catalogId ,String watchId) {
        try {

            String url = CATALOG_SERVICE_BASE_URL + "/" + catalogId + "/watches/" + watchId;
            restTemplate.put(url, watchRequestModel);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ioex.getMessage();
        }
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {

        if (ex.getStatusCode().equals(NOT_FOUND)) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode().equals(UNPROCESSABLE_ENTITY)) {
            return new InvalidInputException(getErrorMessage(ex));
        }

        log.warn("Unexpected HTTP error: {}, response: {}", ex.getStatusCode(), ex.getResponseBodyAsString());
        return ex;
    }
}
