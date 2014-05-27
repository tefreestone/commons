package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.SharedSecretCredential;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:CommonDao-Context.xml",
        "classpath:TestContext.xml",
        "classpath:TestHSQLContext.xml"})

public class SharedSecretCredentialIntegrationTest extends AbstractTest {
    private static final Logger LOG = LoggerFactory.getLogger(SharedSecretCredentialIntegrationTest.class);

    private SharedSecretCredentialDAO sharedSecretCredentialDAO;


    @Autowired
    public void setSharedSecretCredentialDAO(SharedSecretCredentialDAO sharedSecretCredentialDAO) {
        this.sharedSecretCredentialDAO = sharedSecretCredentialDAO;
    }

    @Test
    public void testAddNewSharedSecret() throws Exception {
        SharedSecretCredential credential = this.sharedSecretCredentialDAO.getActiveSharedSecretCredentialForIdentity(PERSON_ID);
        if (credential != null) {
            credential.expire();
            sharedSecretCredentialDAO.updateSharedSecretCredential(credential);
            SharedSecretCredential tmpCred = sharedSecretCredentialDAO.getSharedSecretCredentialByWsId(credential.getWsId());
            Assert.assertNotNull(tmpCred);
            Assert.assertEquals(credential, tmpCred);
        }
        credential = SharedSecretCredential.initializeSharedSecretCredential(PERSON_ID);
        String wsId = credential.getWsId();
        sharedSecretCredentialDAO.saveSharedSecretCredential(credential);
        credential = sharedSecretCredentialDAO.getActiveSharedSecretCredentialForIdentity(PERSON_ID);
        SharedSecretCredential credentialByWsId = sharedSecretCredentialDAO.getActiveSharedSecretCredentialByWsId(wsId);
        Assert.assertEquals(credential, credentialByWsId);
    }
}
