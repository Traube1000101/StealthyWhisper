package com.traube.stealthywhisper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

/**
 * Java String Cipher
 * Original Author: Ramesh Fadatare
 */
public class Cipher {
    private static SecretKeySpec secretKey;
    private static final String ALGORITHM = "AES";

    public static void prepareSecreteKey(String myKey) {
        MessageDigest sha;
        try {
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ALGORITHM);
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            prepareSecreteKey(secret);
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ALGORITHM);
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e);
        }
        return null;
    }
}