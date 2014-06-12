package edu.byu.core.common.wsAuth;


import edu.byu.core.common.wsAuth.api.CredentialClient;
import edu.byu.core.common.wsAuth.api.NonceClient;
import edu.byu.core.common.wsAuth.model.hibernate.SharedSecretCredential;
import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import edu.byu.core.common.wsAuth.model.security.NonceHmacCredential;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractAPIKeyNonceWSClient extends AbstractWSClient {

    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private CredentialClient credentialClient;
    private NonceClient nonceClient;


    protected AbstractAPIKeyNonceWSClient() {
        super();
    }

    protected AbstractAPIKeyNonceWSClient(String authHeader, RestTemplate restTemplate) {
        super(authHeader, restTemplate);
    }

    protected AbstractAPIKeyNonceWSClient(String authHeader, NonceClient nonceClient, RestTemplate restTemplate, CredentialClient credentialClient) {
        super(authHeader, restTemplate);
        this.credentialClient = credentialClient;
        this.nonceClient = nonceClient;
    }

    public NonceClient getNonceClient() {
        return nonceClient;
    }

    public void setNonceClient(NonceClient nonceClient) {
        this.nonceClient = nonceClient;
    }

    public CredentialClient getCredentialClient() {
        return credentialClient;
    }

    public void setCredentialClient(CredentialClient credentialClient) {
        this.credentialClient = credentialClient;
    }


    private HttpEntity<String> generateHeader(final String personId, final MediaType mediaType, final String actor) {
        WsNonce nonce = null;
        SharedSecretCredential sharedSecretCredential = (SharedSecretCredential) credentialClient.getCredential(personId);
        NonceHmacCredential nonceHmacCredential = new NonceHmacCredential(sharedSecretCredential);

        nonce = nonceClient.getNonce(nonceHmacCredential.getId());

        String authHeaderValue = null;
        try {
            authHeaderValue = nonceHmacCredential.generateHeader(nonce);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(authHeader, authHeaderValue);
        headers.setAccept(Arrays.asList(mediaType));

        return new HttpEntity<String>("parameters", headers);
    }

    protected <E> List<E> makeWSCall(final Class<E> type, final String personId, final String url, final MediaType mediaType) {
        return makeWSCall(type, personId, null, url, mediaType);
    }


    protected <E> List<E> makeWSCall(final Class<E> type, final String personId, final String actorNetId, final String url, final MediaType mediaType) {
        if (type != null && personId != null && url != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("url : " + url);
            }
            HttpEntity<String> httpEntity = generateHeader(personId, mediaType, actorNetId);
            return makeGenericWSCall(type, url, httpEntity);
        } else
            throw new IllegalArgumentException("type == null || personId == null || url == null");
    }

    protected <E> E makeWSCallSingleton(final Class<E> type, final String personId, final String url, final MediaType mediaType) {
        return makeWSCallSingleton(type, personId, null, url, mediaType);
    }

    protected <E> E makeWSCallSingleton(final Class<E> type, final String personId, final String actorNetId, final String url, final MediaType mediaType) {
        if (type != null && personId != null && url != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("url : " + url);
            }

            HttpEntity<String> httpEntity = generateHeader(personId, mediaType, actorNetId);
            return makeGenericWSCallSingleton(type, url, httpEntity);
        } else
            throw new IllegalArgumentException("type == null || personId == null || url == null");
    }


}
