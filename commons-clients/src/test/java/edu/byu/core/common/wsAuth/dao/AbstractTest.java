package edu.byu.core.common.wsAuth.dao;

import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by tefreestone on 4/14/14.
 */
public class AbstractTest {

//    @Autowired
//    @Qualifier("testWSId")
//    protected String wsID;
//
//    @Autowired
//    @Qualifier("testSharedSecret")
//    protected String SHARED_SECRET;

    @Autowired
    @Qualifier("testURL")
    protected String URL;

    @Autowired
    @Qualifier("testPersonId")
    protected String PERSON_ID;


    @Autowired
    @Qualifier("testActorId")
    protected String ACTOR_ID;

    @Autowired
    @Qualifier("testWSId")
    protected String wsID;

    protected WsSessionCredential sessionCredential;

    @Autowired
    @Qualifier("wsSessionCredentialDAO")
    private WsSessionCredentialDAO wsSessionCredentialDAO;
    private static final String TEST_PERSON_ID = "123456789";

    public void before() {
        sessionCredential = wsSessionCredentialDAO.createSession(TEST_PERSON_ID);
    }

    public void done() {
        wsSessionCredentialDAO.deleteSession(sessionCredential);
    }
}
