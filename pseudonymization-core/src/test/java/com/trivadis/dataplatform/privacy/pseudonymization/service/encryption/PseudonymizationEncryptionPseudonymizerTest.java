package com.trivadis.dataplatform.privacy.pseudonymization.service.encryption;

import com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.service.Pseudonymizer;
import com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.service.encryption.EncryptionPseudonymizer;
import com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.service.encryption.SecureConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PseudonymizationEncryptionPseudonymizerTest {
    private static final String SECRET_KEY = "eHkCnEhjfzsAHzNXCTGHaImv514CqfcPpoCgb2c0iuY=";
    private static final String NONCE_KEY = "2hfbSR4JxbE=";
    private static final Integer NONCE_BEGIN_POS = 9;
    private static final Integer NONCE_END_POS = 21;

    private Pseudonymizer pseudonymizer;

    @BeforeEach
    public void setup() {
        SecureConfig secureConfig = new SecureConfig(SECRET_KEY, NONCE_KEY, NONCE_BEGIN_POS, NONCE_END_POS);
        pseudonymizer = new EncryptionPseudonymizer(secureConfig);
    }

    @Test
    public void pseudonymizeSingleIdentifier() {
        String identifier = "Hello Data Privacy!";
        String pseudonym = pseudonymizer.pseudonymize(identifier);
        assertEquals("ZTRmMjYyOTY3MmZlOfCfTky6D1AJ3cqHoniBnuf9X6NVU2d/ggRkTLzhlOryU+A=", pseudonym);
    }

    @Test
    public void pseudonymizeSingleIdentifierDeterministic() {
        String identifier = "Hello Data Privacy!";
        String pseudonym1 = pseudonymizer.pseudonymize(identifier, true);
        String pseudonym2 = pseudonymizer.pseudonymize(identifier, true);
        assertEquals(pseudonym1, pseudonym2);
    }

    @Test
    public void pseudonymizeSingleIdentifierNotDeterministic() {
        String identifier = "Hello Data Privacy!";
        String pseudonym1 = pseudonymizer.pseudonymize(identifier, false);
        String pseudonym2 = pseudonymizer.pseudonymize(identifier, false);
        assertNotEquals(pseudonym1, pseudonym2);
    }

    @Test
    public void pseudonymizeSingleIdentifierNoCaching() {
        SecureConfig secureConfig = new SecureConfig(SECRET_KEY, NONCE_KEY, NONCE_BEGIN_POS, NONCE_END_POS);
        pseudonymizer = new EncryptionPseudonymizer(secureConfig, false);

        String identifier = "Hello Data Privacy!";
        String pseudonym = pseudonymizer.pseudonymize(identifier, true);
    }

    @Test
    public void pseudonymizeMultipleIdentifiers() {
        String[] identifiers = new String[] {"First One", "Second One", "Third One"};
        String[] pseudonyms = pseudonymizer.pseudonymize(identifiers);

        assertTrue(pseudonyms.length == 3);
        assertEquals("ZGExZGUzNjk0MDFh/cl0508YKkW7Ox/B/5lRlsd1EvVyeIk0Iw==", pseudonyms[0]);
        assertEquals("NTllZTI4YmNjZTk3fi5wtlGWMzcnPTTe2Ag156m63wPJWs0QjRw=", pseudonyms[1]);
        assertEquals("NDYxY2M5MzZiOTQ5NnJ9eTDk5r803tVZQIqxn2ThjcWBhr3N1Q==", pseudonyms[2]);
    }

    @Test
    public void pseudonymizeNULLIdentifier() {
        String identifier = null;
        String pseudonym = pseudonymizer.pseudonymize(identifier);
        assertNull(pseudonym);
    }

    @Test
    public void pseudonymizeEmptyListOfIdentifiers() {
        String[] identifiers = new String[] {};
        String[] pseudonyms = pseudonymizer.pseudonymize(identifiers);

        assertTrue(pseudonyms.length == 0);
    }

}
