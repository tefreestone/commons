package edu.byu.core.common.wsAuth.api;


import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;

/**
 * Created with IntelliJ IDEA.
 * User: tef2
 * Date: 11/8/13
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CredentialClient {
    public WsSessionCredential getCredential(String personId);

    public void expireCredential(Credential credential);
}
