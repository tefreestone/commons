package edu.byu.core.common.wsAuth.model.security;


import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;

import java.io.UnsupportedEncodingException;

/**
 * The NonceEncodingCredential interface defines a credential that is used in NONCE encoded web service authentication.
 *
 * @author Tom Freestone
 * @since 1.2.0.0
 */
public interface NonceEncodingCredential {

    /**
     * Generates the Authorization header from the Nonce value supplied.
     *
     * @param nonce WsNonce used to create the header.
     * @return String header value used for authentication.
     * @throws UnsupportedEncodingException when UTF-8 encoding is not supported.
     */
    String generateHeader(final WsNonce nonce) throws UnsupportedEncodingException;
}
