package edu.byu.core.common.wsauth.model.security;


import edu.byu.core.common.wsAuth.model.security.SessionUrlHmacCredential;
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
public class SessionUrlHmacCredentialUnitTest extends HMACCredentialAbstractTest {
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
            SessionUrlHmacCredential cred = new SessionUrlHmacCredential(wsSessionCredential);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
        try {
            SessionUrlHmacCredential cred = new SessionUrlHmacCredential(null);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testDateFormat() {
        String result = SessionUrlHmacCredential.formatTimestamp(new Date());
        Assert.assertNotNull(result);

        try {
            result = SessionUrlHmacCredential.formatTimestamp(null);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetHeader() throws Exception {
        SessionUrlHmacCredential cred = new SessionUrlHmacCredential(wsSessionCredential);
        String header = cred.generateHeader(wsID, TEST_MESSAGE, TEST_TIMESTAMP);
        String header2 = cred.generateHeader(wsID, TEST_MESSAGE);
        Assert.assertNotNull(header2);

        String formatTimeStamp = null;
        DateFormat dateFormat = new SimpleDateFormat(UrlHmacCredential.DATE_FORMAT);
        formatTimeStamp = dateFormat.format(TEST_TIMESTAMP);

        String messageDigest = cred.getWsSessionCredential().sign(TEST_MESSAGE + formatTimeStamp);
        String testHeader = SessionUrlHmacCredential.WS_AUTH_TYPE + " " + wsID + SessionUrlHmacCredential.WS_AUTH_DELIMITER + messageDigest + SessionUrlHmacCredential.WS_AUTH_DELIMITER + formatTimeStamp;
        Assert.assertNotNull(header);
        Assert.assertEquals(header, testHeader);
    }

    @Test
    public void testHeaderInput() throws Exception {
        SessionUrlHmacCredential cred = new SessionUrlHmacCredential(wsSessionCredential);
        try {
            cred.generateHeader(null, TEST_MESSAGE, TEST_TIMESTAMP);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
        try {
            cred.generateHeader(wsID, null, TEST_TIMESTAMP);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
            e.printStackTrace();
        }
        try {
            cred.generateHeader(wsID, TEST_MESSAGE, (Date) null);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
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
    }

    @Test
    public void testSuperClass() throws Exception {
        SessionUrlHmacCredential cred = new SessionUrlHmacCredential(wsSessionCredential);
        Assert.assertEquals(PERSON_ID, cred.getPersonId());
    }

}
