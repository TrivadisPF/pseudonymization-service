package com.trivadis.dataplatform.privacy.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class SecretKeyAESUtils {

    private static final String CIPHER = "AES";

    /**
     * Method to generate encryption key of certain size
     * @param keySize  the size of the key (32, 64, 128, 256 ...)
     * @return  the encoded key
     */
    public static byte[] generateKey(int keySize) {
        byte[] secureRandomKeyBytes = new byte[keySize / 8];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(secureRandomKeyBytes);
        return new SecretKeySpec(secureRandomKeyBytes, CIPHER).getEncoded();
    }

    public static int nounceStart(int min,int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
    private static byte[] generateKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(CIPHER);
        keyGenerator.init(keySize);
        return keyGenerator.generateKey().getEncoded();
    }*/
}
