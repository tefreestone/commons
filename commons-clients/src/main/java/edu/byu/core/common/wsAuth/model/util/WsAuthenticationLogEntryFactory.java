package edu.byu.core.common.wsAuth.model.util;


import edu.byu.core.common.wsAuth.model.hibernate.WsAuthenticationLog;

/**
 * Created by IntelliJ IDEA.
 * User: tef2
 * Date: 2/24/11
 * Time: 5:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class WsAuthenticationLogEntryFactory {

    public static WsAuthenticationLog createValidAuthenticationLogEntry(String actor, String principal, String credentialType, String credentialValue, String uri, String userAgent) {
        WsAuthenticationLog result = createLogEntry(principal, credentialType, credentialValue, uri);
        result.setActor(actor);
        result.setUserAgent(userAgent);
        result.setValidAuthentication(WsAuthenticationLog.VALID_AUTHENTICATION);
        return result;
    }

    public static WsAuthenticationLog createInvalidAuthenticationLogEntry(String actor, String principal, String credentialType, String credentialValue, String uri, String userAgent) {
        WsAuthenticationLog result = createLogEntry(principal, credentialType, credentialValue, uri);
        result.setActor(actor);
        result.setUserAgent(userAgent);
        result.setValidAuthentication(WsAuthenticationLog.INVALID_AUTHENTICATION);
        return result;
    }

    public static WsAuthenticationLog createValidAuthenticationLogEntry(String actor, String principal, String credentialType, String credentialValue, String uri) {
        WsAuthenticationLog result = createLogEntry(principal, credentialType, credentialValue, uri);
        result.setActor(actor);
        result.setValidAuthentication(WsAuthenticationLog.VALID_AUTHENTICATION);
        return result;
    }

    public static WsAuthenticationLog createInvalidAuthenticationLogEntry(String actor, String principal, String credentialType, String credentialValue, String uri) {
        WsAuthenticationLog result = createLogEntry(principal, credentialType, credentialValue, uri);
        result.setActor(actor);
        result.setValidAuthentication(WsAuthenticationLog.INVALID_AUTHENTICATION);
        return result;
    }

    public static WsAuthenticationLog createValidAuthenticationLogEntry(String principal, String credentialType, String credentialValue, String uri) {
        WsAuthenticationLog result = createLogEntry(principal, credentialType, credentialValue, uri);
        result.setValidAuthentication(WsAuthenticationLog.VALID_AUTHENTICATION);
        return result;
    }

    public static WsAuthenticationLog createInvalidAuthenticationLogEntry(String principal, String credentialType, String credentialValue, String uri) {
        WsAuthenticationLog result = createLogEntry(principal, credentialType, credentialValue, uri);
        result.setValidAuthentication(WsAuthenticationLog.INVALID_AUTHENTICATION);
        return result;
    }


    private static WsAuthenticationLog createLogEntry(String principal, String credentialType, String credentialValue, String uri) {
        WsAuthenticationLog result = new WsAuthenticationLog();
        result.setPrincipal(principal);
        result.setUri(uri);
        result.setCredentialValue(credentialValue);
        result.setCredentialType(credentialType);
        return result;
    }

}
