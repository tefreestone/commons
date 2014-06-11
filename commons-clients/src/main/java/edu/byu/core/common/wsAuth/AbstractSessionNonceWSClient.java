package edu.byu.core.common.wsAuth;


import edu.byu.core.common.wsAuth.api.NonceClient;
import edu.byu.core.common.wsAuth.api.WSSessionClient;
import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;
import edu.byu.core.common.wsAuth.model.security.SessionNonceHmacCredential;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * User: tef2
 * Date: 10/15/12
 * Time: 3:15 PM
 */
public abstract class AbstractSessionNonceWSClient extends BaseAbstractWSClient {

    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private WSSessionClient wsSessionClient;
    private NonceClient nonceClient;


    protected AbstractSessionNonceWSClient() {
        super();
    }

    protected AbstractSessionNonceWSClient(String authHeader, RestTemplate restTemplate) {
        super(authHeader, restTemplate);
    }

    protected AbstractSessionNonceWSClient(String authHeader, NonceClient nonceClient, RestTemplate restTemplate, WSSessionClient wsSessionClient) {
        super(authHeader, restTemplate);
        this.wsSessionClient = wsSessionClient;
        this.nonceClient = nonceClient;
    }

    public NonceClient getNonceClient() {
        return nonceClient;
    }

    public void setNonceClient(NonceClient nonceClient) {
        this.nonceClient = nonceClient;
    }

    public WSSessionClient getWsSessionClient() {
        return wsSessionClient;
    }

    public void setWsSessionClient(WSSessionClient wsSessionClient) {
        this.wsSessionClient = wsSessionClient;
    }

    private HttpEntity<String> generateHeader(final String personId, final MediaType mediaType) {
        WsNonce nonce = null;
        WsSessionCredential sessionCredential = wsSessionClient.getSession(personId);
        SessionNonceHmacCredential sessionNonceHmacCredential = new SessionNonceHmacCredential(sessionCredential);

        nonce = nonceClient.getNonce(sessionNonceHmacCredential.getId());

        String authHeaderValue = null;
        try {
            authHeaderValue = sessionNonceHmacCredential.generateHeader(nonce);
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
            HttpEntity<String> httpEntity = generateHeader(personId, mediaType);
            return makeGenericWSCall(type, url, httpEntity);
        } else
            throw new IllegalArgumentException("type == null || personId == null || url == null");
    }

    protected <E> E makeWSCallSingleton(final Class<E> type, final String personId, final String url, final MediaType mediaType) {
        if (type != null && personId != null && url != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("url : " + url);
            }

            HttpEntity<String> httpEntity = generateHeader(personId, mediaType);
            return makeGenericWSCallSingleton(type, url, httpEntity);
        } else
            throw new IllegalArgumentException("type == null || personId == null || url == null");
    }


}
