package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;

import java.util.List;


public interface WsSessionCredentialDAO {
    public List<WsSessionCredential> getActiveSessions(String personId);

    public List<WsSessionCredential> getSessions(String personId);

    public WsSessionCredential getSession(String wsId);

    public void deleteSession(WsSessionCredential cred);

    public WsSessionCredential createSession(String personId, int sessionTimeoutLength);

    public WsSessionCredential createSession(String personId);

}
