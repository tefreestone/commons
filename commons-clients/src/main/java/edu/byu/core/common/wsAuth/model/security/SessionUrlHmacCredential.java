package edu.byu.core.common.wsAuth.model.security;


import edu.byu.core.common.wsAuth.model.hibernate.WsSessionCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The SessionUrlHmacCredential is a WsSessionHmacCredential used for URL encoded ws authentication.
 *
 * @author Tom Freestone
 * @since 1.2.0.0
 */
public final class SessionUrlHmacCredential extends WsSessionHmacCredential implements UrlEncodingCredential {

    /**
     * UID of this class used in serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Authentication type for this credential.
     */
    public static final String WS_AUTH_TYPE = "URL-Encoded-WsSession-Key";

    /**
     * Date format string used for this authentication type.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Log4J logger used by this class.
     * <p/>
     * The logger name is the class name. Enabled DEBUG level logging to get logging.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SessionUrlHmacCredential.class);

    /**
     * Formatter for dates using the #DATE_FORMAT.
     */
    private static final SimpleDateFormat TIME_STAMP_FORMAT = new SimpleDateFormat(DATE_FORMAT);


    /**
     * Constructs a SessionUrlHmacCredential using the given WsSessionCredential.
     *
     * @param wsSessionCredential WsSessionCredential used to perform authentication.
     */
    public SessionUrlHmacCredential(final WsSessionCredential wsSessionCredential) {
        super(wsSessionCredential);
    }

    @Override
    public String generateHeader(final String wsId, final String message, final Date timeStamp, final String actor) throws UnsupportedEncodingException {
        return generateHeader(wsId, message, timeStamp);
    }

    @Override
    public String generateHeader(final String wsId, final String message, final Date timeStamp) throws UnsupportedEncodingException {
        String header = null;
        if (wsId != null && message != null && timeStamp != null) {
            String messageDigest = getWsSessionCredential().sign(generateDigest(message, timeStamp));
            header = WS_AUTH_TYPE + " " + wsId + WS_AUTH_DELIMITER + messageDigest + WS_AUTH_DELIMITER + formatTimestamp(timeStamp);
            if (LOG.isDebugEnabled()) {
                LOG.debug("header : " + header);
            }
            return header;
        } else {
            throw new IllegalArgumentException("wsId != null && message != null && timeStamp != null && actor != null should be true");
        }
    }

    @Override
    public String generateHeader(final String wsId, final String message) throws UnsupportedEncodingException {
        if (wsId != null && message != null) {
            return generateHeader(wsId, message, new Date());
        } else {
            throw new IllegalArgumentException("wsId != null && message != null");
        }
    }

    @Override
    public String generateHeader(final String wsId, final String message, final String actor) throws UnsupportedEncodingException {
        return generateHeader(wsId, message);
    }


    /**
     * Formates the date using the #DATE_FORMAT.
     *
     * @param timestamp Date to format.
     * @return String formatted date.
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
     * Generates the message digest.
     *
     * @param message   String message value.
     * @param timeStamp Date timestamp to append to the message.
     * @return String messageDigest.
     */
    public static String generateDigest(final String message, final Date timeStamp) {
        return message + formatTimestamp(timeStamp);
    }

}
