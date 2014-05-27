package edu.byu.core.common.wsAuth.api;


import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;

public interface NonceClient {
    public WsNonce getNonce(String wsId);

    public WsNonce getNonce(String wsId, String actor);
}
