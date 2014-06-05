package edu.byu.core.common;


import edu.byu.core.common.wsAuth.AbstractSessionNonceWSClient;
import edu.byu.core.common.wsAuth.api.WSSessionClient;
import edu.byu.core.common.wsAuth.nonce.NonceClientDaoImpl;
import edu.byu.core.common.wsAuth.wsSession.WsSessionClientDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:TestContext.xml",
        "classpath:CommonDaoHibernate-Context.xml",
        "classpath:TestOracleContext.xml"
})
public class AbstractNonceWSClientTest extends AbstractSessionNonceWSClient {

    private String personId = "405201982";

    @Resource(name = "testURL")
    private String testUrl;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @Resource(name = "wsSessionClient")
    private WSSessionClient wsSessionClient;

    public AbstractNonceWSClientTest() {
        super("Authentication", new NonceClientDaoImpl(), new RestTemplate(), new WsSessionClientDaoImpl());
    }

    @Test
    public void testCase() {
//        this.makeWSCall(String.class,personId,"https://ws.byu.edu/example/authentication/hmac/services/v1/exampleWS", MediaType.APPLICATION_JSON);
    }
}
