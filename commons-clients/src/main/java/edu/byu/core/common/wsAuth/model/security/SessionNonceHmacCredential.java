package edu.byu.core.common.wsAuth.model.security;


import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;
import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * The SessionNonceHmacCredential is used to perform authentication with a WsSession and WsNonce.
 *
 * @author Tom Freestone
 * @since 1.2.0.0
 */
public final class SessionNonceHmacCredential extends WsSessionHmacCredential implements NonceEncodingCredential {

    /**
     * Serial version uid used for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The authentication type.
     */
    public static final String WS_AUTH_TYPE = "Nonce-Encoded-WsSession-Key";

    /**
     * Log4J logger used for logging.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SessionNonceHmacCredential.class);

    /**
     * Constructs a SessionNonceHmacCredential.
     *
     * @param wsSessionCredential WsSessionCredential used by this Credential.
     */
    public SessionNonceHmacCredential(final WsSessionCredential wsSessionCredential) {
        super(wsSessionCredential);
    }

    /**
     * Generates the Authorization header value.
     *
     * @param nonce WsNonce used to create the header.
     * @return String authorization header value.
     * @throws UnsupportedEncodingException when there is a problem signing the message.
     */
    public String generateHeader(final WsNonce nonce) throws UnsupportedEncodingException {
        String header = null;
        if (nonce != null) {
            String messageDigest = getWsSessionCredential().sign(nonce.getNonceValue());
            header = WS_AUTH_TYPE + " " + nonce.getWsId() + WS_AUTH_DELIMITER + nonce.getNonceKey() + WS_AUTH_DELIMITER + messageDigest;
            if (LOG.isDebugEnabled()) {
                LOG.debug("header : " + header);
            }
            return header;
        } else {
            throw new IllegalArgumentException("credential != null && message != null should be true");
        }
    }

}
