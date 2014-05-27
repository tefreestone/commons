package edu.byu.core.common.wsAuth.model.security;


import edu.byu.core.common.wsAuth.model.hibernate.SharedSecretCredential;


public abstract class HmacCredential extends WsAuthenticationCredential<SharedSecretCredential> {

    private String actor;

    public final String getActor() {
        return actor;
    }


    /**
     * Constructs an HmacCredential using the given SharedSecretCredential when there is no actor.
     *
     * @param sharedSecretCredential SharedSecretCredential used to perform the signature.
     */
    public HmacCredential(final SharedSecretCredential sharedSecretCredential) {
        super(sharedSecretCredential);
        this.actor = null;
    }

    /**
     * Constructs an HmacCredential using the given SharedSecretCredential and actor id.
     *
     * @param sharedSecretCredential SharedSecretCredential used to perform the signature.
     * @param actor                  String id of the actor for the resulting authentication value.
     * @deprecated
     */
    public HmacCredential(final SharedSecretCredential sharedSecretCredential, final String actor) {
        super(sharedSecretCredential);
        this.actor = actor;
    }


    /**
     * Getter for the sharedSecretCredential.
     *
     * @return SharedSecretCredential sharedSecretCredential.
     */
    public final SharedSecretCredential getSharedSecretCredential() {
        return this.getWsAuthCredential();
    }

    @Override
    public final String getId() {
        return this.getSharedSecretCredential().getWsId();
    }

//    @Override
//    public final String getValue() {
//        return this.getSharedSecretCredential().getSharedSecret();
//    }
}
