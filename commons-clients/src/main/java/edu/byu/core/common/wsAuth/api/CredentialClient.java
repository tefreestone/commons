package edu.byu.core.common.wsAuth.api;


/**
 * Created with IntelliJ IDEA.
 * User: tef2
 * Date: 11/8/13
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CredentialClient {
    public Credential getCredential(String personId);

    public void expireCredential(Credential credential);
}
