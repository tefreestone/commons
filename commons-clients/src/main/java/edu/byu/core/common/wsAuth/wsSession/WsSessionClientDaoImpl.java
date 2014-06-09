package edu.byu.core.common.wsAuth.wsSession;

import edu.byu.core.common.wsAuth.api.WSSessionClient;
import edu.byu.core.common.wsAuth.dao.WsSessionCredentialDAO;
import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;
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
public class WsSessionClientDaoImpl implements WSSessionClient {

    @Autowired(required = true)
    @Qualifier("wsSessionCredentialDAO")
    private WsSessionCredentialDAO wsSessionCredentialDAO;

    @Override
    public WsSessionCredential getSession(final String personId) {
        if (personId != null) {
            List<WsSessionCredential> sessionCredentials = wsSessionCredentialDAO.getActiveSessions(personId);
            if (sessionCredentials.isEmpty()) {
                return wsSessionCredentialDAO.createSession(personId);
            } else {
                return sessionCredentials.get(0);
            }
        } else
            throw new IllegalArgumentException("personId == null");
    }

    @Override
    public void deleteSession(final WsSessionCredential credential) {
        if (credential != null) {
            wsSessionCredentialDAO.deleteSession(credential);
        } else
            throw new IllegalArgumentException("credential == null");
    }
}
