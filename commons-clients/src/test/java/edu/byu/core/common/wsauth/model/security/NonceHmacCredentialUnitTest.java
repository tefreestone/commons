package edu.byu.core.common.wsauth.model.security;


import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import edu.byu.core.common.wsAuth.model.security.HmacCredential;
import edu.byu.core.common.wsAuth.model.security.NonceHmacCredential;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:Common-Context.xml",
        "classpath:CommonDaoHibernate-Context.xml",
        "classpath:TestOracleContext.xml",
//          "classpath:TestHSQLContext.xml",
        "classpath:TestContext.xml"
})
public class NonceHmacCredentialUnitTest extends HMACCredentialAbstractTest {
    private final Logger LOG = LoggerFactory.getLogger(NonceHmacCredentialUnitTest.class);


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
            HmacCredential cred = new NonceHmacCredential(testSharedSecretCredential);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
        try {
            HmacCredential cred = new NonceHmacCredential(null);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetHeader() throws Exception {
        NonceHmacCredential cred = new NonceHmacCredential(testSharedSecretCredential);
        String header = cred.generateHeader(nonce);
        String messageDigest = cred.getSharedSecretCredential().sign(nonce.getNonceValue());
        String testHeader = NonceHmacCredential.WS_AUTH_TYPE + " " + wsID + NonceHmacCredential.WS_AUTH_DELIMITER + nonce.getNonceKey() + UrlHmacCredential.WS_AUTH_DELIMITER + messageDigest;
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
        NonceHmacCredential cred = new NonceHmacCredential(testSharedSecretCredential);
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
        NonceHmacCredential cred = new NonceHmacCredential(testSharedSecretCredential);
        Assert.assertEquals(PERSON_ID, cred.getPersonId());
    }

}
