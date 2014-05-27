package edu.byu.core.common;


import edu.byu.core.common.wsAuth.AbstractNonceWSClient;
import edu.byu.core.common.wsAuth.nonce.NonceClientDaoImpl;
import edu.byu.core.common.wsAuth.wsSession.WsSessionClientDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: tef2
 * Date: 7/29/13
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:TestContext.xml",
        "classpath:AbstractBasedWSClientDAOContext.xml"
})
public class AbstractNonceWSClientTest extends AbstractNonceWSClient {


    public AbstractNonceWSClientTest() {
        super("Authentication", new NonceClientDaoImpl(), new RestTemplate(), new WsSessionClientDaoImpl());
    }

    @Test
    public void testCase() {
//        this.makeWSCall();
    }
}
