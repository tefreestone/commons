package edu.byu.core.common.wsAuth;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by tefreestone on 2/7/14.
 */
abstract public class AbstractWSClient {

    protected String authHeader;

    protected RestTemplate restTemplate;

    protected AbstractWSClient() {
    }

    protected AbstractWSClient(String authHeader, RestTemplate restTemplate) {
        this.authHeader = authHeader;
        this.restTemplate = restTemplate;
    }

    protected String getAuthHeader() {
        return authHeader;
    }

    protected void setAuthHeader(String authHeader) {
        this.authHeader = authHeader;
    }


    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    protected void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected <E> List<E> makeGenericWSCall(final Class<E> type, final String url, final HttpEntity<String> headers) {
        if (type != null && headers != null && url != null) {
            ResponseEntity<List<E>> response;
            response = restTemplate.exchange(url, HttpMethod.GET, headers, new ParameterizedTypeReference<List<E>>() {
            });
            return response.getBody();
        } else
            throw new IllegalArgumentException("type == null || personId == null || url == null");
    }

    protected <E> E makeGenericWSCallSingleton(final Class<E> type, final String url, final HttpEntity<String> headers) {
        if (type != null && headers != null && url != null) {
            ResponseEntity<E> response;
            response = restTemplate.exchange(url, HttpMethod.GET, headers, type);
            return response.getBody();
        } else
            throw new IllegalArgumentException("type == null || personId == null || url == null");
    }
}
