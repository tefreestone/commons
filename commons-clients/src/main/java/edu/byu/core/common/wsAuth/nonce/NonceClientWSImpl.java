package edu.byu.core.common.wsAuth.nonce;

import edu.byu.core.common.wsAuth.AbstractWSClient;
import edu.byu.core.common.wsAuth.api.NonceClient;
import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import edu.byu.core.common.wsAuth.model.xml.Nonce;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: tef2
 * Date: 11/8/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */

@Service("nonceClientWS")
public class NonceClientWSImpl extends AbstractWSClient implements NonceClient {

    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Resource(name = "nonceServiceUrl")
    private String nonceServiceUrl;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getNonceServiceUrl() {
        return nonceServiceUrl;
    }

    public void setNonceServiceUrl(String nonceServiceUrl) {
        this.nonceServiceUrl = nonceServiceUrl;
    }

    @Override
    public WsNonce getNonce(final String wsId) {
        if (wsId != null) {
            final String url = nonceServiceUrl + wsId;
            return makeWSCAll(url);
        } else
            throw new IllegalArgumentException("wsId == null");
    }

    @Override
    public WsNonce getNonce(final String wsId, final String actor) {
        if (wsId == null && actor == null) {
            final String url = nonceServiceUrl + wsId + "/" + actor;
            return makeWSCAll(url);
        } else
            throw new IllegalArgumentException("wsId == null || actor == null");
    }

    private WsNonce makeWSCAll(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<String>("parameters", headers);
        Nonce nonce = restTemplate.postForObject(url, request, Nonce.class);

        return nonce.convertToWsNonce();
    }
}
