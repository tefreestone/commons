package edu.byu.core.common.wsAuth.model.security;


import edu.byu.core.common.wsAuth.api.Credential;
import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;

/**
 * The WsSessionHmacCredential is a Credential that wraps a WsSessionCredential.
 *
 * @author Tom Freestone
 * @since 1.2.0.0
 */
public abstract class WsSessionHmacCredential extends WsAuthenticationCredential<WsSessionCredential> implements Credential {

    /**
     * Constructs the WsSessionHmacCredential using the provided WsSessionCredential.
     *
     * @param credential WsSessionCredential that is wrapped in the resulting Credential.
     */
    public WsSessionHmacCredential(final WsSessionCredential credential) {
        super(credential);
    }

    /**
     * Getter for the wsSessionCredential.
     *
     * @return WsSessionCredential the wsSessionCredential.
     */
    public final WsSessionCredential getWsSessionCredential() {
        return getWsAuthCredential();
    }

    /**
     * Setter for the wsSessionCredential.
     *
     * @param wsSessionCredentialParameter WsSessionCredential new value.
     */
    public final void setWsSessionCredential(final WsSessionCredential wsSessionCredentialParameter) {
        throw new RuntimeException();
    }

    @Override
    public final String getId() {
        return this.getWsSessionCredential().getWsId();
    }

//    @Override
//    public final String getValue() {
//        return this.getWsSessionCredential().getSharedSecret();
//    }
}
