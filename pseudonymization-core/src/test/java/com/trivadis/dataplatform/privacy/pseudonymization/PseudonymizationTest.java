package com.trivadis.dataplatform.privacy.pseudonymization;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.trivadis.dataplatform.privacy.aesgcmsiv.EncryptionAESGCMSIV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PseudonymizationTest {

    private static final String SECRET_KEY = "eHkCnEhjfzsAHzNXCTGHaImv514CqfcPpoCgb2c0iuY=";
    private static final byte[] DECODED_SECRET_KEY = Base64.getDecoder().decode(SECRET_KEY);
    private static final String NONCE_KEY = "2hfbSR4JxbE=";
    private static final byte[] DECODED_NONCE_KEY = Base64.getDecoder().decode(NONCE_KEY);
    private static final Integer NONCE_BEGIN_POS = 9;
    private static final Integer NONCE_END_POS = 21;

    private static final Integer DEFAULT_CACHE_SIZE=10;

    private Pseudonymization pseudonymization;

    @BeforeEach
    public void setup() {
        EncryptionAESGCMSIV aesgcmsiv = new EncryptionAESGCMSIV(DECODED_SECRET_KEY, DECODED_NONCE_KEY, NONCE_BEGIN_POS, NONCE_END_POS);
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(Optional.ofNullable(System.getenv("PSEUDONYMIZATION_CACHE_SIZE")).map(Integer::parseInt).orElse(DEFAULT_CACHE_SIZE))
                .softValues()
                .build();
        pseudonymization = new Pseudonymization(aesgcmsiv, cache);
    }


    @Test
    public void pseudonymizeSingleIdentifier() {
        String identifier = "Hello Data Privacy!";
        String pseudonym = pseudonymization.pseudonymize(identifier, true);
        assertEquals("ZTRmMjYyOTY3MmZlOfCfTky6D1AJ3cqHoniBnuf9X6NVU2d/ggRkTLzhlOryU+A=", pseudonym);
    }

    @Test
    public void pseudonymizeMultipleIdentifiers() {
        String[] identifiers = new String[] {"First One", "Second One", "Third One"};
        String[] pseudonyms = pseudonymization.pseudonymize(identifiers, true);

        assertTrue(pseudonyms.length == 3);
        assertEquals("ZGExZGUzNjk0MDFh/cl0508YKkW7Ox/B/5lRlsd1EvVyeIk0Iw==", pseudonyms[0]);
        assertEquals("NTllZTI4YmNjZTk3fi5wtlGWMzcnPTTe2Ag156m63wPJWs0QjRw=", pseudonyms[1]);
        assertEquals("NDYxY2M5MzZiOTQ5NnJ9eTDk5r803tVZQIqxn2ThjcWBhr3N1Q==", pseudonyms[2]);
    }

    @Test
    public void pseudonymizeNULLIdentifier() {
        String identifier = null;
        String pseudonym = pseudonymization.pseudonymize(identifier, true);
        assertNull(pseudonym);
    }

    @Test
    public void pseudonymizeEmptyListOfIdentifiers() {
        String[] identifiers = new String[] {};
        String[] pseudonyms = pseudonymization.pseudonymize(identifiers, true);

        assertTrue(pseudonyms.length == 0);
    }
}
