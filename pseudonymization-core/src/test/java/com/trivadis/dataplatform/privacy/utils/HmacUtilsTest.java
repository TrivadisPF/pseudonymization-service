package com.trivadis.dataplatform.privacy.utils;

import com.trivadis.dataplatform.privacy.utils.HmacUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HmacUtilsTest {

    @Test
    public void testGenerate() {
        HmacUtils hmacUtils = new HmacUtils();

        String value = "Senistive Value to be protected";
        System.out.println(hmacUtils.generateHmac256(value.getBytes(StandardCharsets.UTF_8), "KbPeShVmYq3t6w9z$C&F)J@NcQfTjWnZ".getBytes(StandardCharsets.UTF_8)));
    }

    private static Key getSecureRandomKey(String cipher, int keySize) {
        byte[] secureRandomKeyBytes = new byte[keySize / 8];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(secureRandomKeyBytes);
        return new SecretKeySpec(secureRandomKeyBytes, cipher);
    }

    private static Key getKeyFromKeyGenerator(String cipher, int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(cipher);
        keyGenerator.init(keySize);
        return keyGenerator.generateKey();
    }

    @Test
    public void testGenerateKey() throws NoSuchAlgorithmException {
        final String CIPHER = "AES";
        Key key = getKeyFromKeyGenerator(CIPHER, 128);
        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
    }
}

