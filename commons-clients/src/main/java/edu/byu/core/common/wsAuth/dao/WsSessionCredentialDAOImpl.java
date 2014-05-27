package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("wsSessionCredentialDAO")
@Transactional(value = "wsAuthTransactionManager", readOnly = true)
public class WsSessionCredentialDAOImpl extends BaseAbstractDAO implements WsSessionCredentialDAO {
    private static final Logger LOG = LoggerFactory.getLogger(WsSessionCredentialDAOImpl.class);

    @Override
    public List<WsSessionCredential> getActiveSessions(String personId) {
        return getSession().getNamedQuery("list.active.WsSessionCredentials.by.personId").setString("personId", personId).list();
    }

    @Override
    public List<WsSessionCredential> getSessions(String personId) {
        return getSession().getNamedQuery("list.WsSessionCredentials.by.personId").setString("personId", personId).list();
    }

    @Override
    public WsSessionCredential getSession(String wsId) {
        return (WsSessionCredential) getSession().getNamedQuery("get.active.WsSessionCredential.by.wsId").setString("wsId", wsId).uniqueResult();
    }

    @Override
    @Transactional(value = "wsAuthTransactionManager", readOnly = false)
    public void deleteSession(WsSessionCredential cred) {
        getSession().delete(cred);
    }


    @Override
    @Transactional(value = "wsAuthTransactionManager", readOnly = false)
    public WsSessionCredential createSession(String personId, int sessionTimeoutLength) {
        WsSessionCredential cred = WsSessionCredential.initializeWsSessionCredential(personId, sessionTimeoutLength);
        getSession().save(cred);
        return cred;
    }

    @Override
    @Transactional(value = "wsAuthTransactionManager", readOnly = false)
    public WsSessionCredential createSession(String personId) {
        return createSession(personId, WsSessionCredential.EXPIRATION_LENGTH);
    }


}
