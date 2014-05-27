package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:CommonDao-Context.xml",
        "classpath:TestContext.xml",
//        "classpath:TestHSQLContext.xml"
        "classpath:CommonDaoHibernate-Context.xml",
        "classpath:TestOracleContext.xml"
})


public class WsSessionDAOIntegrationTest extends AbstractTest {
    private static final Logger LOG = LoggerFactory.getLogger(WsSessionDAOIntegrationTest.class);
    @Autowired
    private WsSessionCredentialDAO wsSessionCredentialDAO;

    @Before
    public void setUp() {
        // clean up any old sessions from failed tests
        if (!wsSessionCredentialDAO.getSessions(PERSON_ID).isEmpty()) {
            for (WsSessionCredential cred : wsSessionCredentialDAO.getSessions(PERSON_ID)) {
                wsSessionCredentialDAO.deleteSession(cred);
            }
        }
    }


    @Test
    public void testCrud() {
        WsSessionCredential cred = wsSessionCredentialDAO.createSession(PERSON_ID);
        Assert.assertNotNull(cred);
        Assert.assertEquals(PERSON_ID, cred.getPersonId());
        Assert.assertNotNull(cred.getId());
        Assert.assertNotNull(cred.getSharedSecret());

        List<WsSessionCredential> tmpCreds = wsSessionCredentialDAO.getSessions(PERSON_ID);
        Assert.assertNotNull(tmpCreds);
        Assert.assertEquals(1, tmpCreds.size());


        for (WsSessionCredential sessionCred : tmpCreds) {
            LOG.debug(sessionCred.toString());
        }

        tmpCreds = wsSessionCredentialDAO.getActiveSessions(PERSON_ID);
        LOG.debug("size = " + tmpCreds.size());
        Assert.assertEquals(1, tmpCreds.size());
        for (WsSessionCredential sessionCred : tmpCreds) {
            wsSessionCredentialDAO.deleteSession(sessionCred);
        }
        tmpCreds = wsSessionCredentialDAO.getActiveSessions(PERSON_ID);
        LOG.debug("size = " + tmpCreds.size());
        Assert.assertTrue(tmpCreds.isEmpty());
    }
}
