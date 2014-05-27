package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.WsAuthenticationLog;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tef2
 * Date: 2/22/11
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LogDAO {
    public void log(WsAuthenticationLog logEntry);

    public void deleteLog(WsAuthenticationLog logEntry);

    public List<WsAuthenticationLog> getLogEntriesByActor(String actor);

    public List<WsAuthenticationLog> getLogEntriesByPrincipal(String principal);

    public List<WsAuthenticationLog> getLogEntriesByPrincipal(String principal, Date startDate, Date endDate);

    public List<WsAuthenticationLog> getLogEntriesByCredentialValue(String credentialType, String credentialValue);

    public List<WsAuthenticationLog> getLogEntriesByCredentialValue(String credentialType, String credentialValue, Date logDate);

    public List<WsAuthenticationLog> getInvalidAuthentications(String principal);
}
