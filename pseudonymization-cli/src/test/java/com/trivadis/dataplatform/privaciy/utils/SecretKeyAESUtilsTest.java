package com.trivadis.dataplatform.privaciy.utils;

import com.trivadis.dataplatform.privacy.utils.SecretKeyAESUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;

public class SecretKeyAESUtilsTest {

    @Test
    public void testGenerateKeyOfLength64bit() {
        byte[] key = SecretKeyAESUtils.generateKey(64);
        String keyEncoded = Base64.getEncoder().encodeToString(key);
        assertEquals(12, keyEncoded.length());
    }

    @Test
    public void testGenerateKeyOfLength256bit() {
        byte[] key = SecretKeyAESUtils.generateKey(256);
        String keyEncoded = Base64.getEncoder().encodeToString(key);
        assertEquals(44, keyEncoded.length());
    }
}
