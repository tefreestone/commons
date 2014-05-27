package edu.byu.core.common.wsAuth.model.security;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * The UrlEncodingCredential interface defines a credential that is used in URL encoded web service authentication.
 *
 * @author Tom Freestone
 * @since 1.2.0.0
 */
public interface UrlEncodingCredential {

    /**
     * Generates the Authorization header from the given information.
     *
     * @param wsId      String id used to identify the principal and associated shared secret to used to sign the message.
     * @param message   String message to be signed.
     * @param timeStamp Date timestamp to use in the signature and resulting header.
     * @param actor     String id of the actor to use in the message signature and the resulting header.
     * @return String value of the Authorization header.
     * @throws UnsupportedEncodingException when if UTF-8 encoding is not supported.
     */
    String generateHeader(String wsId, String message, Date timeStamp, String actor) throws UnsupportedEncodingException;

    /**
     * Generates the Authorization header from the given information.
     *
     * @param wsId      String id used to identify the principal and associated shared secret to used to sign the message.
     * @param message   String message to be signed.
     * @param timeStamp Date timestamp to use in the signature and resulting header.
     * @return String value of the Authorization header.
     * @throws UnsupportedEncodingException when if UTF-8 encoding is not supported.
     */
    String generateHeader(String wsId, String message, Date timeStamp) throws UnsupportedEncodingException;

    /**
     * Generates the Authorization header from the given information.
     *
     * @param wsId    String id used to identify the principal and associated shared secret to used to sign the message.
     * @param message String message to be signed.
     * @return String value of the Authorization header.
     * @throws UnsupportedEncodingException when if UTF-8 encoding is not supported.
     */
    String generateHeader(String wsId, String message) throws UnsupportedEncodingException;

    /**
     * Generates the Authorization header from the given information.
     *
     * @param wsId    String id used to identify the principal and associated shared secret to used to sign the message.
     * @param message String message to be signed.
     * @param actor   String id of the actor to use in the message signature and the resulting header.
     * @return String value of the Authorization header.
     * @throws UnsupportedEncodingException when if UTF-8 encoding is not supported.
     */
    String generateHeader(String wsId, String message, String actor) throws UnsupportedEncodingException;
}
