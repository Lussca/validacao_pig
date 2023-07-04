package util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;

public final class HashPasswordUtils {

    public static String transformationToHash(String password_user) throws NoSuchAlgorithmException {
        String md5Hex = DigestUtils
                .md5Hex(password_user).toUpperCase();

        return md5Hex;
    }

}
