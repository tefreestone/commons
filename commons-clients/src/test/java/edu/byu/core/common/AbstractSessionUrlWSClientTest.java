package edu.byu.core.common;


import edu.byu.core.common.TestModel.Message;
import edu.byu.core.common.wsAuth.api.CredentialClient;
import edu.byu.core.common.wsAuth.wsSession.WsSessionClientDaoImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
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
public class AbstractSessionUrlWSClientTest extends edu.byu.core.common.AbstractSessionUrlWSClient {

    @Resource(name = "personId")
    private String personId;

    @Resource(name = "testURL")
    private String testUrl;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @Resource(name = "wsSessionClientDao")
    private CredentialClient credentialClient;

    @Resource(name = "authHeader")
    private String authHeader;


    public AbstractSessionUrlWSClientTest() {
        super("Authorization", new RestTemplate(), new WsSessionClientDaoImpl());
    }

    @Before
    public void setup() {
        super.setAuthHeader(authHeader);
        super.setRestTemplate(restTemplate);
        super.setRestTemplate(restTemplate);
        super.setCredentialClient(credentialClient);
    }

    @Test
    public void testCase() {
        Message result = this.makeWSCallSingleton(Message.class, personId, testUrl, MediaType.APPLICATION_XML);
        Assert.assertNotNull(result);
        result = this.makeWSCallSingleton(Message.class, personId, testUrl, MediaType.APPLICATION_JSON);
        Assert.assertNotNull(result);
    }
}
