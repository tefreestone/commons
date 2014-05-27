package edu.byu.core.common.wsAuth.model.xml;

//import edu.byu.ws.authentication.domain.WsNonce;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "nonce")
@XmlType(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0")
public final class Nonce {
//
//    private String nonce;
//    private Long nonceKey;
//
//    public Nonce() {
//    }
//
//    @XmlElement(name = "nonceValue", required = true)
//    public String getNonce() {
//        return nonce;
//    }
//
//    public void setNonce(String nonce) {
//        this.nonce = nonce;
//    }
//
//    @XmlElement(name = "nonceKey", required = true)
//    public Long getNonceKey() {
//        return nonceKey;
//    }
//
//    public void setNonceKey(Long nonceKey) {
//        this.nonceKey = nonceKey;
//    }
//
//    public WsNonce convertToWsNonce() {
//        WsNonce wsNonce = new WsNonce();
//        wsNonce.setNonceKey(this.nonceKey);
//        wsNonce.setNonceValue(this.nonce);
//        return wsNonce;
//    }
}
