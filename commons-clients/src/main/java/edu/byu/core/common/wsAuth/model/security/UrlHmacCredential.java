package edu.byu.core.common.wsAuth.model.security;

import edu.byu.core.common.wsAuth.model.hibernate.SharedSecretCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The UrlHmacCredential provides support for performing URL HMAC authentication.
 *
 * @author Tom Freestone
 * @since 1.2.0.0
 */
public final class UrlHmacCredential extends HmacCredential implements UrlEncodingCredential {

    /**
     * Serial version UID used in Serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Authentication type.
     */
    public static final String WS_AUTH_TYPE = "URL-Encoded-API-Key";

    /**
     * String time stamp format.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Log4J logger used by this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(UrlHmacCredential.class);

    /**
     * Formatter used to format the time stamp.
     */
    private static final SimpleDateFormat TIME_STAMP_FORMAT = new SimpleDateFormat(DATE_FORMAT);


    /**
     * Constructs an UrlHmacCredential from the given information.
     *
     * @param sharedSecretCredential SharedSecretCredential used by this credential.
     */
    public UrlHmacCredential(final SharedSecretCredential sharedSecretCredential) {
        super(sharedSecretCredential);
    }

    /**
     * Constructs an UrlHmacCredential from the given information.
     *
     * @param sharedSecretCredential SharedSecretCredential used by this credential.
     * @param actor                  String actor id.
     */
    public UrlHmacCredential(final SharedSecretCredential sharedSecretCredential, final String actor) {
        super(sharedSecretCredential, actor);
    }

    /**
     * Generates the Authorization header.
     *
     * @param wsId      String id used to identify the principal and associated shared secret to used to sign the message.
     * @param message   String message to be signed.
     * @param timeStamp Date timestamp to use in the signature and resulting header.
     * @return String authorization header value.
     * @throws UnsupportedEncodingException when there is a problem signing the digest.
     */
    public String generateHeader(final String wsId, final String message, final Date timeStamp) throws UnsupportedEncodingException {
        String header = null;

        header = generateHeader(wsId, message, timeStamp, null);
        if (LOG.isDebugEnabled()) {
            LOG.debug("header : " + header);
        }
        return header;
    }

    /**
     * Generates the Authorization header.
     *
     * @param wsId      String id used to identify the principal and associated shared secret to used to sign the message.
     * @param message   String message to be signed.
     * @param timeStamp Date timestamp to use in the signature and resulting header.
     * @param actor     String id of the actor to use in the message signature and the resulting header.
     * @return String authorization header value.
     * @throws UnsupportedEncodingException when there is a problem signing the digest.
     */
    public String generateHeader(final String wsId, final String message, final Date timeStamp, final String actor) throws UnsupportedEncodingException {
        String header = null;
        if (wsId != null && message != null && timeStamp != null) {
            String messageDigest = null;
            if (actor == null) {
                messageDigest = getSharedSecretCredential().sign(generateDigest(message, timeStamp));
                header = WS_AUTH_TYPE + " " + wsId + WS_AUTH_DELIMITER + messageDigest + WS_AUTH_DELIMITER + formatTimestamp(timeStamp);
            } else {
                messageDigest = getSharedSecretCredential().sign(generateDigest(message, timeStamp, actor));
                header = WS_AUTH_TYPE + " " + wsId + WS_AUTH_DELIMITER + messageDigest + WS_AUTH_DELIMITER + formatTimestamp(timeStamp) + WS_AUTH_DELIMITER + actor;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("header : " + header);
            }
            return header;
        } else {
            throw new IllegalArgumentException("wsId != null && message != null && timeStamp != null && actor != null should be true");
        }
    }

    /**
     * Generates the Authorization header.
     *
     * @param wsId    String id used to identify the principal and associated shared secret to used to sign the message.
     * @param message String message to be signed.
     * @return String authorization header value.
     * @throws UnsupportedEncodingException when there is a problem signing the digest.
     */
    public String generateHeader(final String wsId, final String message) throws UnsupportedEncodingException {
        if (wsId != null && message != null) {
            return generateHeader(wsId, message, new Date());
        } else {
            throw new IllegalArgumentException("wsId != null && message != null");
        }
    }

    /**
     * Generates the Authorization header.
     *
     * @param wsId    String id used to identify the principal and associated shared secret to used to sign the message.
     * @param message String message to be signed.
     * @param actor   String id of the actor to use in the message signature and the resulting header.
     * @return String authorization header value.
     * @throws UnsupportedEncodingException when there is a problem signing the digest.
     */
    public String generateHeader(final String wsId, final String message, final String actor) throws UnsupportedEncodingException {
        if (wsId != null && message != null && actor != null) {
            return generateHeader(wsId, message, new Date(), actor);
        } else {
            throw new IllegalArgumentException("wsId != null && message != null");
        }
    }

    /**
     * Formats the Time Stamp.
     *
     * @param timestamp Date to format.
     * @return String formatted time stamp.
     */
    public static String formatTimestamp(final Date timestamp) {
        if (timestamp != null) {
            DateFormat dateFormat = TIME_STAMP_FORMAT;
            return dateFormat.format(timestamp);
        } else {
            throw new IllegalArgumentException("timestamp != null");
        }
    }

    /**
     * Generates the digest to sign.
     *
     * @param message   String message to be signed.
     * @param timeStamp Date time stamp.
     * @return String formatted digest ready for signing.
     */
    public static String generateDigest(final String message, final Date timeStamp) {
        return message + formatTimestamp(timeStamp);
    }

    /**
     * Generates the digest to sign.
     *
     * @param message   String message to be signed.
     * @param timeStamp Date time stamp.
     * @param actor     String actor id.
     * @return String formatted digest ready for signing.
     */
    public static String generateDigest(final String message, final Date timeStamp, final String actor) {
        return message + formatTimestamp(timeStamp) + actor;
    }
}
