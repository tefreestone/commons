package edu.byu.core.common;


import edu.byu.core.common.wsAuth.AbstractSessionNonceWSClient;
import edu.byu.core.common.wsAuth.api.NonceClient;
import edu.byu.core.common.wsAuth.api.WSSessionClient;
import edu.byu.core.common.wsAuth.nonce.NonceClientDaoImpl;
import edu.byu.core.common.wsAuth.wsSession.WsSessionClientDaoImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:TestContext.xml",
        "classpath:CommonDaoHibernate-Context.xml",
        "classpath:TestOracleContext.xml"
})
public class AbstractNonceWSClientTest extends AbstractSessionNonceWSClient {

    @Resource(name = "personId")
    private String personId;

    @Resource(name = "testURL")
    private String testUrl;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @Resource(name = "wsSessionClientDao")
    private WSSessionClient wsSessionClient;

    @Resource(name = "nonceClientDao")
    private NonceClient nonceClient;

    @Resource(name = "authHeader")
    private String authHeader;


    public AbstractNonceWSClientTest() {
        super("Authorization", new NonceClientDaoImpl(), new RestTemplate(), new WsSessionClientDaoImpl());
    }

    @Before
    public void setup() {
        super.setAuthHeader(authHeader);
        super.setRestTemplate(restTemplate);
        super.setNonceClient(nonceClient);
        super.setRestTemplate(restTemplate);
        super.setWsSessionClient(wsSessionClient);
    }

    @Test
    public void testCase() {
        List<String> result = this.makeWSCall(String.class, personId, testUrl, MediaType.APPLICATION_JSON);
    }
}
