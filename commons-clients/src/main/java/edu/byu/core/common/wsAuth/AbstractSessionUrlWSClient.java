package edu.byu.core.common;


import edu.byu.core.common.wsAuth.AbstractWSClient;
import edu.byu.core.common.wsAuth.api.CredentialClient;
import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;
import edu.byu.core.common.wsAuth.model.security.SessionUrlHmacCredential;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractSessionUrlWSClient extends AbstractWSClient {

    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private CredentialClient credentialClient;


    protected AbstractSessionUrlWSClient() {
        super();
    }

    protected AbstractSessionUrlWSClient(String authHeader, RestTemplate restTemplate) {
        super(authHeader, restTemplate);
    }

    protected AbstractSessionUrlWSClient(String authHeader, RestTemplate restTemplate, CredentialClient credentialClient) {
        super(authHeader, restTemplate);
        this.credentialClient = credentialClient;
    }


    public CredentialClient getCredentialClient() {
        return credentialClient;
    }

    public void setCredentialClient(CredentialClient credentialClient) {
        this.credentialClient = credentialClient;
    }

    private HttpEntity<String> generateHeader(final String personId, final MediaType mediaType, final String url) {
        WsSessionCredential sessionCredential = (WsSessionCredential) credentialClient.getCredential(personId);
        SessionUrlHmacCredential sessionUrlHmacCredential = new SessionUrlHmacCredential(sessionCredential);

        String authHeaderValue = null;
        try {
            authHeaderValue = sessionUrlHmacCredential.generateHeader(sessionUrlHmacCredential.getId(), url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(authHeader, authHeaderValue);
        headers.setAccept(Arrays.asList(mediaType));

        return new HttpEntity<String>("parameters", headers);
    }

    protected <E> List<E> makeWSCall(final Class<E> type, final String personId, final String url, final MediaType mediaType) {
        if (type != null && personId != null && url != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("url : " + url);
            }
            HttpEntity<String> httpEntity = generateHeader(personId, mediaType, url);
            return makeGenericWSCall(type, url, httpEntity);
        } else
            throw new IllegalArgumentException("type == null || personId == null || url == null");
    }

    protected <E> E makeWSCallSingleton(final Class<E> type, final String personId, final String url, final MediaType mediaType) {
        if (type != null && personId != null && url != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("url : " + url);
            }

            HttpEntity<String> httpEntity = generateHeader(personId, mediaType, url);
            return makeGenericWSCallSingleton(type, url, httpEntity);
        } else
            throw new IllegalArgumentException("type == null || personId == null || url == null");
    }


}
