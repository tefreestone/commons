package edu.byu.core.common.wsAuth.model.hibernate;

import org.apache.commons.codec.binary.Base64;

import java.io.Serializable;
import java.security.SecureRandom;

public class RandomIdGenerator implements Serializable {

    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();

    private RandomIdGenerator() {
    }

    public static String generateId(final int length) {
        byte[] generatedIdBytes = new byte[length];
        RANDOM_GENERATOR.nextBytes(generatedIdBytes);
        return Base64.encodeBase64URLSafeString(generatedIdBytes).substring(0, length);
    }

}
