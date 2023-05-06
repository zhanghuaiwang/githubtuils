package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sea on 2018/1/13.
 */

public class Md5Utils {
    public static String md5(String message) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("md5");
            byte m5[] = md.digest(message.getBytes());
            return Base64Encoder.encode(m5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
