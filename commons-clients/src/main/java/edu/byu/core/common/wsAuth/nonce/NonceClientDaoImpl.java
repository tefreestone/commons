package edu.byu.core.common.wsAuth.nonce;

import edu.byu.core.common.wsAuth.api.NonceClient;
import edu.byu.core.common.wsAuth.dao.NonceDAO;
import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: tef2
 * Date: 11/8/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */

@Service("nonceClientDaoImpl")
public class NonceClientDaoImpl implements NonceClient {

    private final Logger LOG = LoggerFactory.getLogger(NonceClientDaoImpl.class);

    @Autowired
    @Qualifier("nonceDAO")
    private NonceDAO nonceDAO;

    @Override
    public WsNonce getNonce(final String wsId) {
        if (wsId != null) {
            return nonceDAO.getNewNonce(wsId);
        } else
            throw new IllegalArgumentException("wsId == null");
    }

    @Override
    public WsNonce getNonce(final String wsId, final String actor) {
        if (wsId == null && actor == null) {
            return nonceDAO.getNewNonce(wsId, actor);
        } else
            throw new IllegalArgumentException("wsId == null || actor == null");
    }
}
