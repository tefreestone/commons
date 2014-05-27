package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;

import java.util.List;


public interface NonceDAO {
    public WsNonce getNewNonce(String wsId);

    public WsNonce getNewNonce(String wsId, String actor);

    public WsNonce getNonceByKey(long nonceKey);

    public WsNonce getNonceByValue(String nonceValue);

    public List<WsNonce> getNonceByWSId(String wsId);

    public List<WsNonce> getNonceByWSId(String wsId, String URI);

    public void consumeNonce(long nonceKey);

    public void consumeNonce(String nonceValue);

}
