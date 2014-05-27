package edu.byu.core.common.wsauth.model.security;


import edu.byu.core.common.wsAuth.dao.NonceDAO;
import edu.byu.core.common.wsAuth.dao.WsSessionCredentialDAO;
import edu.byu.core.common.wsAuth.model.hibernate.SharedSecretCredential;
import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;

import javax.annotation.Resource;
import java.util.Date;

public abstract class HMACCredentialAbstractTest {

    @Resource(name = "testPersonId")
    protected String PERSON_ID;

    @Resource(name = "testActorId")
    protected String ACTOR_ID;


    @Resource(name = "testWSId")
    protected String wsID;

    @Resource(name = "testURL")
    protected String TEST_MESSAGE;

    @Resource(name = "testSharedSecret")
    protected String SHARED_SECRET;

    @Resource(name = "testSharedSecretCredential")
    protected SharedSecretCredential testSharedSecretCredential;

    protected Date TEST_TIMESTAMP;

    protected WsNonce nonce;


    //TODO -- Convert to Mocks
    @Resource(name = "nonceDAO")
    private NonceDAO nonceDAO;

    //TODO -- Convert to Mocks
    @Resource(name = "wsSessionCredentialDAO")
    private WsSessionCredentialDAO wsSessionCredentialDAO;

    protected WsSessionCredential wsSessionCredential;


    protected void setUp() {
        nonce = nonceDAO.getNewNonce(wsID);
        wsSessionCredential = wsSessionCredentialDAO.createSession(PERSON_ID);
        TEST_TIMESTAMP = new Date();
    }

    protected void done() {
        nonceDAO.consumeNonce(nonce.getNonceKey());
        wsSessionCredentialDAO.deleteSession(wsSessionCredential);
    }
}
