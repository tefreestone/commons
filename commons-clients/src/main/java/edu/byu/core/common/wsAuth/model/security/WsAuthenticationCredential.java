package edu.byu.core.common.wsAuth.model.security;


import edu.byu.core.common.wsAuth.model.hibernate.Credential;

import java.util.Date;

/**
 * The WsAuthenticationCredential is a wrapper for Web Service authentication credentials.
 *
 * @param <T> the type of the Credential that is wrapper by this class.
 * @author Tom Freestone
 * @since 1.2.0.0
 */
public abstract class WsAuthenticationCredential<T extends Credential> implements Credential {

    /**
     * The delimiter used in the Authorization header between values.
     */
    public static final String WS_AUTH_DELIMITER = ",";

    /**
     *
     */
    private final T wsAuthCredential;

    /**
     * Constructs a WsAuthenticationCredential to wrap the given Credential.
     *
     * @param credential T credential wrapped by this class.
     */
    protected WsAuthenticationCredential(final T credential) {
        if (credential == null) {
            throw new IllegalArgumentException("The provided credential can not be null.");
        }
        this.wsAuthCredential = credential;
    }

    /**
     * Getter for the wsAuthCredential.
     *
     * @return T wrapped credential.
     */
    protected final T getWsAuthCredential() {
        return this.wsAuthCredential;
    }

    @Override
    public final String getPersonId() {
        return this.wsAuthCredential.getPersonId();
    }

    @Override
    public final boolean isExpired() {
        return this.wsAuthCredential.isExpired();
    }


    @Override
    public final void expire() {
        this.wsAuthCredential.expire();
    }


    @Override
    public final Date getExpirationDate() {
        return this.wsAuthCredential.getExpirationDate();
    }

//    @Override
//    public final boolean isDisabled() {
//        return this.isExpired();
//    }
//
//    @Override
//    public final void disable() {
//        this.expire();
//    }
}
