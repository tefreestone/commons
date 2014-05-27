package edu.byu.core.common.wsauth.model.security;


import edu.byu.core.common.wsAuth.model.security.HmacCredential;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tef2
 * Date: 3/2/11
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:CommonDao-Context.xml",
        "classpath:TestContext.xml",
//        "classpath:TestHSQLContext.xml"
        "classpath:CommonDaoHibernate-Context.xml",
        "classpath:TestOracleContext.xml"
})
public class UrlHmacCredentialUnitTest extends HMACCredentialAbstractTest {
    private Logger LOG = LoggerFactory.getLogger("UrlHmacCredentialUnitTest");

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
            HmacCredential cred = new UrlHmacCredential(testSharedSecretCredential);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
        try {
            HmacCredential cred = new UrlHmacCredential(null);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testDateFormat() {
        String result = UrlHmacCredential.formatTimestamp(new Date());
        Assert.assertNotNull(result);

        try {
            result = UrlHmacCredential.formatTimestamp(null);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetHeader() throws Exception {
        UrlHmacCredential cred = new UrlHmacCredential(testSharedSecretCredential);
        String header = cred.generateHeader(wsID, TEST_MESSAGE, TEST_TIMESTAMP);
        String header2 = cred.generateHeader(wsID, TEST_MESSAGE);
        Assert.assertNotNull(header2);

        String formatTimeStamp = null;
        DateFormat dateFormat = new SimpleDateFormat(UrlHmacCredential.DATE_FORMAT);
        formatTimeStamp = dateFormat.format(TEST_TIMESTAMP);

        String messageDigest = cred.getSharedSecretCredential().sign(TEST_MESSAGE + formatTimeStamp);
        String testHeader = UrlHmacCredential.WS_AUTH_TYPE + " " + wsID + UrlHmacCredential.WS_AUTH_DELIMITER + messageDigest + UrlHmacCredential.WS_AUTH_DELIMITER + formatTimeStamp;
        Assert.assertNotNull(header);
        Assert.assertEquals(header, testHeader);

        messageDigest = cred.getSharedSecretCredential().sign(TEST_MESSAGE + formatTimeStamp + ACTOR_ID);
        header = cred.generateHeader(wsID, TEST_MESSAGE, TEST_TIMESTAMP, ACTOR_ID);
        header2 = cred.generateHeader(wsID, TEST_MESSAGE, ACTOR_ID);
        Assert.assertNotNull(header2);
        testHeader = UrlHmacCredential.WS_AUTH_TYPE + " " + wsID + UrlHmacCredential.WS_AUTH_DELIMITER + messageDigest + UrlHmacCredential.WS_AUTH_DELIMITER + formatTimeStamp + UrlHmacCredential.WS_AUTH_DELIMITER + ACTOR_ID;
        Assert.assertNotNull(header);
        Assert.assertEquals(testHeader, header);
    }

    @Test
    public void testHeaderInput() throws Exception {
        UrlHmacCredential cred = new UrlHmacCredential(testSharedSecretCredential);
        try {
            cred.generateHeader(null, TEST_MESSAGE, TEST_TIMESTAMP, ACTOR_ID);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
        try {
            cred.generateHeader(wsID, null, TEST_TIMESTAMP, ACTOR_ID);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
        try {
            cred.generateHeader(wsID, TEST_MESSAGE, null, ACTOR_ID);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
        try {
            cred.generateHeader(wsID, TEST_MESSAGE, TEST_TIMESTAMP, null);
            Assert.assertTrue(true);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(false);
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }

        try {
            cred.generateHeader(null, TEST_MESSAGE);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            cred.generateHeader(wsID, TEST_MESSAGE);
            Assert.assertTrue(true);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(false);
        }
        try {
            cred.generateHeader(wsID, TEST_MESSAGE, ACTOR_ID);
            Assert.assertTrue(true);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(false);
        }
        try {
            cred.generateHeader(null, TEST_MESSAGE, ACTOR_ID);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            cred.generateHeader(wsID, null, ACTOR_ID);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            cred.generateHeader(wsID, TEST_MESSAGE, (String) null);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testSuperClass() throws Exception {
        UrlHmacCredential cred = new UrlHmacCredential(testSharedSecretCredential);
        Assert.assertEquals(PERSON_ID, cred.getPersonId());
    }

}
