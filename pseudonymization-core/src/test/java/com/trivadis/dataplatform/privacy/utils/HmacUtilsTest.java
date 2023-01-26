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
}

