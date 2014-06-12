package edu.byu.core.common;


import edu.byu.core.common.TestModel.Message;
import edu.byu.core.common.wsAuth.APIKey.APIKeyClientDaoImpl;
import edu.byu.core.common.wsAuth.AbstractAPIKeyUrlWSClient;
import edu.byu.core.common.wsAuth.api.CredentialClient;
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
public class AbstractAPIKeyUrlWSClientTest extends AbstractAPIKeyUrlWSClient {

    @Resource(name = "apiKeyPersonId")
    private String personId;

    @Resource(name = "personId")
    private String badPersonId;

    @Resource(name = "netId")
    private String actor;

    @Resource(name = "testURL")
    private String testUrl;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @Resource(name = "apiKeyClientDao")
    private CredentialClient credentialClient;

    @Resource(name = "authHeader")
    private String authHeader;


    public AbstractAPIKeyUrlWSClientTest() {
        super("Authorization", new RestTemplate(), new APIKeyClientDaoImpl());
    }

    @Before
    public void setup() {
        super.setAuthHeader(authHeader);
        super.setRestTemplate(restTemplate);
        super.setRestTemplate(restTemplate);
        super.setCredentialClient(credentialClient);
    }

    @Test
    public void testErrors() {
        try {
            Message result = this.makeWSCallSingleton(Message.class, badPersonId, testUrl, MediaType.APPLICATION_XML);
            Assert.assertTrue(false);
        } catch (IllegalStateException e) {
            Assert.assertTrue(true);
        }
    }


    @Test
    public void testCase() {
        Message result = this.makeWSCallSingleton(Message.class, personId, testUrl, MediaType.APPLICATION_XML);
        Assert.assertNotNull(result);
        result = this.makeWSCallSingleton(Message.class, personId, testUrl, MediaType.APPLICATION_JSON);
        Assert.assertNotNull(result);
        result = this.makeWSCallSingleton(Message.class, personId, actor, testUrl, MediaType.APPLICATION_XML);
        Assert.assertNotNull(result);
        result = this.makeWSCallSingleton(Message.class, personId, actor, testUrl, MediaType.APPLICATION_JSON);
        Assert.assertNotNull(result);
    }
}
