package edu.byu.core.common.wsAuth.model.hibernate;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SharedSecretCredential extends AbstractCredentialImpl implements Credential, Serializable {

    public static final int SHARED_SECRET_LENGTH = 40;
    public static final int WS_ID_LENGTH = 20;

    public static final String ALGORITHM = "HmacSHA512";
    public static final String CHARACTER_ENCODING = "UTF8";

    public static final int EXPIRATION_LENGTH = 12; //months

    private String wsId;
    private String sharedSecret;
    private Mac mac;

    public static SharedSecretCredential initializeSharedSecretCredential(String personId) {
        if (personId != null) {
            SharedSecretCredential credential = new SharedSecretCredential();
            credential.setPersonId(personId);
            credential.wsId = RandomIdGenerator.generateId(WS_ID_LENGTH);
            credential.sharedSecret = RandomIdGenerator.generateId(SHARED_SECRET_LENGTH);
            initializeMac(credential);
            credential.setExpirationDate(calculateExpirationDate());
            return credential;
        } else
            throw new IllegalArgumentException("person != null");
    }

    public static SharedSecretCredential sharedSecretCredentialFactory(String personId, String wsId, String sharedSecret, Date expirationDate) {
        if (personId != null && wsId.length() == WS_ID_LENGTH && sharedSecret.length() == SHARED_SECRET_LENGTH && expirationDate != null) {
            SharedSecretCredential credential = new SharedSecretCredential();
            credential.setPersonId(personId);
            credential.wsId = wsId;
            credential.sharedSecret = sharedSecret;
            initializeMac(credential);
            credential.setExpirationDate(expirationDate);
            return credential;
        } else
            throw new IllegalArgumentException("personId != null && wsId.length() == WS_ID_LENGTH && sharedSecret.length() == SHARED_SECRET_LENGTH && expirationDate != null");
    }

    public static SharedSecretCredential sharedSecretCredentialFactory(String personId, String wsId, String sharedSecret) {
        if (personId != null && wsId.length() == WS_ID_LENGTH && sharedSecret.length() == SHARED_SECRET_LENGTH) {
            SharedSecretCredential credential = new SharedSecretCredential();
            credential.setPersonId(personId);
            credential.wsId = wsId;
            credential.sharedSecret = sharedSecret;
            initializeMac(credential);
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(new Date()); // sets calendar time/date
            cal.add(Calendar.MONTH, EXPIRATION_LENGTH); // adds in months
            credential.setExpirationDate(cal.getTime());
            return credential;
        } else
            throw new IllegalArgumentException("personId != null && wsId.length() == WS_ID_LENGTH && sharedSecret.length() == SHARED_SECRET_LENGTH && expirationDate != null");
    }

    private static void initializeMac(SharedSecretCredential credential) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(credential.sharedSecret.getBytes(CHARACTER_ENCODING), ALGORITHM);
            credential.mac = Mac.getInstance(ALGORITHM);
            credential.mac.init(signingKey);
        } catch (Exception e) {
            throw new IllegalStateException("Can not create SharedSecretCredential.", e);
        }
    }

    private static Date calculateExpirationDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, EXPIRATION_LENGTH);
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

    public String getWsId() {
        return this.wsId;
    }

    public String getId() {
        return this.wsId;
    }

    public String getSharedSecret() {
        return this.sharedSecret;
    }

    @Override
    public String toString() {
        return "SharedSecretCredential{" +
                "wsId='" + wsId + '\'' +
                ", sharedSecret='" + sharedSecret + '\'' +
                ", mac=" + mac +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SharedSecretCredential)) return false;

        SharedSecretCredential that = (SharedSecretCredential) o;

        if (mac != null ? !mac.equals(that.mac) : that.mac != null) return false;
        if (sharedSecret != null ? !sharedSecret.equals(that.sharedSecret) : that.sharedSecret != null) return false;
        if (wsId != null ? !wsId.equals(that.wsId) : that.wsId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wsId != null ? wsId.hashCode() : 0;
        result = 31 * result + (sharedSecret != null ? sharedSecret.hashCode() : 0);
        result = 31 * result + (mac != null ? mac.hashCode() : 0);
        return result;
    }
}
