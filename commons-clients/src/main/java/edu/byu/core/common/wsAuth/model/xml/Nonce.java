package edu.byu.core.common.wsAuth.model.xml;

import edu.byu.core.common.wsAuth.model.hibernate.WsNonce;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;


/**
 * The nonce is used when performing HMAC based authentication to a web service. The nonceKey is passed
 * to the service as part of the authentication along with the signed nonceValue.  The nonceValue is signed
 * using a shared secret.  The nonce can be obtained using the Web Service Authentication Service.
 */
@XmlRootElement(name = "nonce", namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0")
//@XmlType(name = "nonce", propOrder = {"nonceKey", "nonceValue"})
public final class Nonce {
    private String nonceValue;
    private Long nonceKey;

    public Nonce() {
    }

    public Nonce(String nonce, Long nonceKey) {
        this.nonceValue = nonce;
        this.nonceKey = nonceKey;
    }

    /**
     * This identifies the nonce value that will be signed as part of nonce based authentication.
     *
     * @return
     */
    @XmlElement(name = "nonceKey", required = true)
    @XmlSchemaType(name = "unsignedLong")
    public Long getNonceKey() {
        return nonceKey;
    }

    void setNonceKey(Long nonceKey) {
        this.nonceKey = nonceKey;
    }

    /**
     * This is the value that will be signed with a shared secret when using nonce based authentication.
     *
     * @return
     */
    @XmlElement(name = "nonceValue", required = true)
    public String getNonceValue() {
        return nonceValue;
    }

    void setNonceValue(String nonce) {
        this.nonceValue = nonce;
    }

    @Override
    public String toString() {
        return "Nonce{" +
                "nonce='" + nonceValue + '\'' +
                ", nonceKey=" + nonceKey +
                '}';
    }

    public WsNonce convertToWsNonce() {
        WsNonce wsNonce = new WsNonce();
        wsNonce.setNonceKey(this.nonceKey);
        wsNonce.setNonceValue(this.nonceValue);
        return wsNonce;
    }
}
