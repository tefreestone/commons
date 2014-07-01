package edu.byu.core.common.wsAuth.nonce;

import edu.byu.core.common.wsAuth.api.NonceClient;
import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by tefreestone on 6/23/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:TestContext.xml",
        "classpath:NonceClientWS-Context.xml"
})
public class NonceClientWSTest {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private static final int MAX_ITERATIONS = 100;
    @Resource(name = "nonceClientWS")
    private NonceClient nonceClient;

    @Resource(name = "testWSId")
    private String wsId;

    @Test
    public void testGetNonce() {
        long start = 0;
        for (int i = 0; i <= MAX_ITERATIONS; i++) {
            if (LOG.isDebugEnabled()) {
                start = System.currentTimeMillis();
            }
            WsNonce nonce = nonceClient.getNonce(wsId);
            if (LOG.isDebugEnabled()) {
                final long end = System.currentTimeMillis() - start;
                LOG.debug("response time : " + end + " ms");
            }
            Assert.assertNotNull(nonce);
            Assert.assertNotNull(nonce.getNonceValue());
            LOG.debug("nonce : " + nonce);
        }
    }
}
