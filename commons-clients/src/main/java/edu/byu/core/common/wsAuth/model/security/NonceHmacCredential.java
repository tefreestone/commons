package edu.byu.core.common.wsAuth.model.security;


import edu.byu.core.common.wsAuth.model.hibernate.SharedSecretCredential;
import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;


public final class NonceHmacCredential extends HmacCredential implements NonceEncodingCredential {

    /**
     * Serial version UID used for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Authentication Type for this kind of authentication.
     */
    public static final String WS_AUTH_TYPE = "Nonce-Encoded-API-Key";

    /**
     * Log4J logger used by this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(NonceHmacCredential.class);


    /**
     * Constructs the NonceHmacCredential from the SharedSecretCredential.
     *
     * @param sharedSecretCredential SharedSecretCredential used to create this Credential.
     */
    public NonceHmacCredential(final SharedSecretCredential sharedSecretCredential) {
        super(sharedSecretCredential);
    }

    /**
     * Constructs the NonceHmacCredential from the SharedSecretCredential and the actor.
     *
     * @param sharedSecretCredential SharedSecretCredential used to create this Credential.
     * @param actor                  String actor in the authentication request.
     */
    public NonceHmacCredential(final SharedSecretCredential sharedSecretCredential, final String actor) {
        super(sharedSecretCredential, actor);
    }


    /**
     * Generates the authorization header value based on this Credential.
     *
     * @param nonce WsNonce used to create the header.
     * @return String header value for the authorization header.
     * @throws java.io.UnsupportedEncodingException when there is a problem signing the message.
     */
    public String generateHeader(final WsNonce nonce) throws UnsupportedEncodingException {
        String header = null;
        if (nonce != null) {
            String messageDigest = getSharedSecretCredential().sign(nonce.getNonceValue());
            header = this.WS_AUTH_TYPE + " " + nonce.getWsId() + WS_AUTH_DELIMITER + nonce.getNonceKey() + WS_AUTH_DELIMITER + messageDigest;
            if (LOG.isDebugEnabled()) {
                LOG.debug("header : " + header);
            }
            return header;
        } else {
            throw new IllegalArgumentException("credential != null && message != null should be true");
        }
    }

}
