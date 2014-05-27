package edu.byu.core.common.wsAuth.dao;


import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;


@Repository("nonceDAO")
@Transactional(value = "wsAuthTransactionManager", readOnly = false)
public class NonceDAOImpl extends BaseAbstractDAO implements NonceDAO {
    @Autowired
    private SecureRandom secureRandom;

    @Autowired
    private MessageDigest messageDigest;


    private int nonceThreshold = 2048;

    private static final String CHARACTER_ENCODING = "UTF8";


    private final Logger LOG = LoggerFactory.getLogger(NonceDAOImpl.class);


    public int getNonceThreshold() {
        return nonceThreshold;
    }

    public void setNonceThreshold(int nonceThreshold) {
        this.nonceThreshold = nonceThreshold;
    }

    @Override
    public WsNonce getNewNonce(String wsId) {
        if (overNonceThreshold(wsId)) {
            WsNonce nonce = createWsNonce(wsId, null);
            getSession().save(nonce);

            return nonce;
        } else {
            LOG.error(wsId + " has too many nonces");
            throw new IllegalStateException(wsId + " has too many nonces");
        }
    }

    public WsNonce getNewNonce(String wsId, String actor) {
        if (overNonceThreshold(wsId)) {
            WsNonce nonce;

            nonce = createWsNonce(wsId, actor);
            getSession().save(nonce);
            return nonce;
        } else {
            LOG.error(wsId + " has too many nonces");
            throw new IllegalStateException(wsId + " has too many nonces");
        }
    }


    @Override
    public void consumeNonce(long nonceKey) {
        WsNonce usedNonce = getNonceByKey(nonceKey);
        if (usedNonce != null) {
            getSession().delete(usedNonce);
        } else {
            LOG.error("Nonce : " + nonceKey + " does not exist");
            throw new IllegalStateException("Nonce : " + nonceKey + " does not exist");
        }
    }

    @Override
    public void consumeNonce(String nonceValue) {
        WsNonce usedNonce = getNonceByValue(nonceValue);
        if (usedNonce != null) {
            getSession().delete(usedNonce);
        } else {
            LOG.error("Nonce : " + nonceValue + " does not exist");
            throw new IllegalStateException("Nonce : " + nonceValue + " does not exist");
        }
    }

    @Override
    @Transactional(value = "wsAuthTransactionManager", readOnly = true)
    public WsNonce getNonceByKey(long nonceKey) {
        return (WsNonce) getSession().getNamedQuery("get.nonce.by.nonceKey").setLong("nonceKey", nonceKey).uniqueResult();
    }

    @Override
    @Transactional(value = "wsAuthTransactionManager", readOnly = true)
    public WsNonce getNonceByValue(String nonceValue) {
        return (WsNonce) getSession().getNamedQuery("get.nonce.by.nonce").setString("nonce", nonceValue).uniqueResult();
    }

    @Override
    @Transactional(value = "wsAuthTransactionManager", readOnly = true)
    public List<WsNonce> getNonceByWSId(String wsId) {
        return getSession().getNamedQuery("get.nonce.by.wsId").setString("wsId", wsId).list();
    }

    @Override
    @Transactional(value = "wsAuthTransactionManager", readOnly = true)
    public List<WsNonce> getNonceByWSId(String wsId, String uri) {
        return getSession().getNamedQuery("get.nonce.by.wsId.uri").setString("wsId", wsId).setString("uri", uri).list();
    }

    private WsNonce createWsNonce(String wsId, String actor) {
        WsNonce newNonce = new WsNonce();
        newNonce.setWsId(wsId);
        String seed = wsId;
        if (actor != null) {
            newNonce.setActor(actor);
            seed = seed + " " + actor;
        }

        newNonce.setNonceValue(calculateNonce(seed));
        return newNonce;
    }

    private String calculateNonce(String seed) {
        String result;
        String nonceValue = seed + " " + Float.toString(secureRandom.nextFloat());
        byte[] nonce = new byte[0];
        try {
            nonce = nonceValue.getBytes(CHARACTER_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] signedBytes = messageDigest.digest(nonce);
        return new String(Base64.encodeBase64(signedBytes));
    }

    @Transactional(value = "wsAuthTransactionManager", readOnly = true)
    private boolean overNonceThreshold(String wsId) {
        Long numUnsedNonces = (Long) getSession().getNamedQuery("get.count.by.wsId").setString("wsId", wsId).uniqueResult();
        if (LOG.isDebugEnabled()) {
            LOG.debug(" wsId " + wsId + " has " + numUnsedNonces + " unused nonces");
        }
        if (numUnsedNonces <= nonceThreshold) {
            return true;
        } else
            return false;
    }

}
