package edu.byu.core.common.wsauth.model.security;


import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import edu.byu.core.common.wsAuth.model.security.SessionNonceHmacCredential;
import edu.byu.core.common.wsAuth.model.security.UrlHmacCredential;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: tef2
 * Date: 3/2/11
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:Common-Context.xml",
        "classpath:TestContext.xml",
//        "classpath:TestHSQLContext.xml"
        "classpath:CommonDaoHibernate-Context.xml",
        "classpath:TestOracleContext.xml"
})
public class SessionNonceHmacCredentialUnitTest extends HMACCredentialAbstractTest {
    private final Logger LOG = LoggerFactory.getLogger(SessionNonceHmacCredentialUnitTest.class);


    @Before
    public void setUp() {
        super.setUp();

    }

    @After
    public void done() {
        super.done();
    }


    @Test
    public void testConstructor() {
        try {
            SessionNonceHmacCredential cred = new SessionNonceHmacCredential(wsSessionCredential);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
        try {
            SessionNonceHmacCredential cred = new SessionNonceHmacCredential(null);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetHeader() throws Exception {
        SessionNonceHmacCredential cred = new SessionNonceHmacCredential(wsSessionCredential);
        String header = cred.generateHeader(nonce);
        String messageDigest = cred.getWsSessionCredential().sign(nonce.getNonceValue());
        String testHeader = SessionNonceHmacCredential.WS_AUTH_TYPE + " " + wsID + SessionNonceHmacCredential.WS_AUTH_DELIMITER + nonce.getNonceKey() + UrlHmacCredential.WS_AUTH_DELIMITER + messageDigest;
        LOG.debug("testHeader : " + testHeader);
        Assert.assertNotNull(header);
        Assert.assertEquals(header, testHeader);
        try {
            header = cred.generateHeader((WsNonce) null);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testHeaderInput() throws Exception {
        SessionNonceHmacCredential cred = new SessionNonceHmacCredential(wsSessionCredential);
        try {
            cred.generateHeader(null);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
    }

    @Test
    public void testSuperClass() throws Exception {
        SessionNonceHmacCredential cred = new SessionNonceHmacCredential(wsSessionCredential);
        Assert.assertEquals(PERSON_ID, cred.getPersonId());
    }

}
