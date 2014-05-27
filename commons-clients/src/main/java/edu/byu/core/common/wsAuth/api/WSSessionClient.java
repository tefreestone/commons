package edu.byu.core.common.wsAuth.api;


import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;

/**
 * Created with IntelliJ IDEA.
 * User: tef2
 * Date: 11/8/13
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WSSessionClient {
    public WsSessionCredential getSession(String personId);

    public void deleteSession(WsSessionCredential credential);
}
