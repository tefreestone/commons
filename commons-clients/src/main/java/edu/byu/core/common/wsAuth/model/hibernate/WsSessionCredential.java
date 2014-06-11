package edu.byu.core.common.wsAuth.model.hibernate;


import edu.byu.core.common.wsAuth.api.Credential;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class WsSessionCredential extends AbstractCredentialImpl implements Credential, Serializable {

    public static final int SHARED_SECRET_LENGTH = 40;
    public static final int WS_ID_LENGTH = 20;
    public static final int EXPIRATION_LENGTH = 10; //minutes

    public static final String ALGORITHM = "HmacSHA512";
    public static final String CHARACTER_ENCODING = "UTF8";

    //    private long credentialId;
    private String wsId;
    private String sharedSecret;
    private Mac mac;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static WsSessionCredential initializeWsSessionCredential(String personId) {
        if (personId != null) {
            return initializeWsSessionCredential(personId, EXPIRATION_LENGTH);
        } else
            throw new IllegalArgumentException("person != null");
    }

    public static WsSessionCredential initializeWsSessionCredential(String personId, int expireTimeout) {
        if (personId != null && expireTimeout >= 1) {
            WsSessionCredential credential = new WsSessionCredential();
            credential.setPersonId(personId);
            credential.wsId = RandomIdGenerator.generateId(WS_ID_LENGTH);
            credential.sharedSecret = RandomIdGenerator.generateId(SHARED_SECRET_LENGTH);
            initializeMac(credential);
            credential.setExpirationDate(calculateExpirationDate(expireTimeout));

            return credential;
        } else
            throw new IllegalArgumentException("person != null");
    }

    public static WsSessionCredential wsSessionCredentialFactory(String personId, String wsId, String sharedSecret, Date expirationDate) {
        if (personId != null && wsId.length() == WS_ID_LENGTH && sharedSecret.length() == SHARED_SECRET_LENGTH && expirationDate != null) {
            WsSessionCredential credential = new WsSessionCredential();
            credential.setPersonId(personId);
            credential.wsId = wsId;
            credential.sharedSecret = sharedSecret;
            initializeMac(credential);
            credential.setExpirationDate(expirationDate);
            return credential;
        } else
            throw new IllegalArgumentException("personId != null && wsId.length() == WS_ID_LENGTH && sharedSecret.length() == SHARED_SECRET_LENGTH && expirationDate != null && expirationLength >= 1");
    }

    private static void initializeMac(WsSessionCredential credential) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(credential.sharedSecret.getBytes(CHARACTER_ENCODING), ALGORITHM);
            credential.mac = Mac.getInstance(ALGORITHM);
            credential.mac.init(signingKey);
        } catch (Exception e) {
            throw new IllegalStateException("Can not create SharedSecretCredential.", e);
        }
    }

    private static Date calculateExpirationDate(int expireTimeout) {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MINUTE, expireTimeout);
        return calendar.getTime();
    }

    public String sign(String valueToSign) throws UnsupportedEncodingException {
        byte[] valueToSignBytes = valueToSign.getBytes(CHARACTER_ENCODING);
        if (mac == null) {
            initializeMac(this);
        }
        byte[] signedBytes = mac.doFinal(valueToSignBytes);
        return new String(Base64.encodeBase64(signedBytes));
    }


    public String getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }


    public String getWsId() {
        return wsId;
    }

    public void setWsId(String wsId) {
        this.wsId = wsId;
    }

    @Override
    public String getId() {
        return getWsId();
    }

    @Override
    public String toString() {
        return "WsSessionCredential{" +
                "wsId='" + wsId + '\'' +
                ", sharedSecret='" + sharedSecret + '\'' +
                ", mac=" + mac +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WsSessionCredential)) return false;

        WsSessionCredential that = (WsSessionCredential) o;

        if (LOG != null ? !LOG.equals(that.LOG) : that.LOG != null) return false;
        if (sharedSecret != null ? !sharedSecret.equals(that.sharedSecret) : that.sharedSecret != null) return false;
        if (wsId != null ? !wsId.equals(that.wsId) : that.wsId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wsId != null ? wsId.hashCode() : 0;
        result = 31 * result + (sharedSecret != null ? sharedSecret.hashCode() : 0);
        result = 31 * result + (LOG != null ? LOG.hashCode() : 0);
        return result;
    }
}
