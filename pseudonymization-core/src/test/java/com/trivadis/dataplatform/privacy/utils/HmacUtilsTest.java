package com.trivadis.dataplatform.privacy.utils;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class HmacUtilsTest {

    @Test
    public void testGenerate() {
        HmacUtils hmacUtils = new HmacUtils();

        String value = "192.168.1.172";
        System.out.println(hmacUtils.generateHmac256(value.getBytes(StandardCharsets.UTF_8), "KbPeShVmYq3t6w9z$C&F)J@NcQfTjWnZ".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void testSecureRandom() {
        SecureRandom random = new SecureRandom();
        byte[] nonce = new byte[12];
        random.nextBytes(nonce);

        System.out.println(Base64.getEncoder().encodeToString(nonce));
    }
}

