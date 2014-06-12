package edu.byu.core.common.wsAuth.wsSession;


import edu.byu.core.common.wsAuth.api.Credential;
import edu.byu.core.common.wsAuth.api.CredentialClient;
import edu.byu.core.common.wsAuth.dao.WsSessionCredentialDAO;
import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: tef2
 * Date: 11/8/13
 * Time: 2:57 PM
 */
@Service("wsSessionClientDao")
public class WsSessionClientDaoImpl implements CredentialClient {
    private final Logger LOG = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired(required = true)
    @Qualifier("wsSessionCredentialDAO")
    private WsSessionCredentialDAO wsSessionCredentialDAO;

    @Override
    public WsSessionCredential getCredential(final String personId) {
        if (personId != null) {
            List<WsSessionCredential> sessionCredentials = wsSessionCredentialDAO.getActiveSessions(personId);
            if (LOG.isDebugEnabled()) {
                LOG.debug(personId + " has the following number of sessionCredentials : " + sessionCredentials.size());
            }
            if (sessionCredentials.isEmpty()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Creating new session.");
                }
                return wsSessionCredentialDAO.createSession(personId);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Using session : " + sessionCredentials.get(0));
                }
                return sessionCredentials.get(0);
            }
        } else
            throw new IllegalArgumentException("personId == null");
    }

    @Override
    public void expireCredential(final Credential credential) {
        if (credential != null && credential instanceof WsSessionCredential) {
            wsSessionCredentialDAO.deleteSession((WsSessionCredential) credential);
        } else
            throw new IllegalArgumentException("credential == null || credential is not an instance of WsSessionCredential");
    }
}
