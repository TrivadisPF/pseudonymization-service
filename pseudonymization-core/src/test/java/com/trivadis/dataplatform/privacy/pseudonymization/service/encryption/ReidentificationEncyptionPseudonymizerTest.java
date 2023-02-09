package com.trivadis.dataplatform.privacy.pseudonymization.service.encryption;

import com.trivadis.dataplatform.privacy.pseudonymization.service.Reidentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReidentificationEncyptionPseudonymizerTest {
    private static final String SECRET_KEY = "eHkCnEhjfzsAHzNXCTGHaImv514CqfcPpoCgb2c0iuY=";
    private static final String NONCE_KEY = "2hfbSR4JxbE=";
    private static final Integer NONCE_BEGIN_POS = 9;
    private static final Integer NONCE_END_POS = 21;

    private Reidentifier reidentifier;

    @BeforeEach
    public void setup() {
        SecureConfig secureConfig = new SecureConfig(SECRET_KEY, NONCE_KEY, NONCE_BEGIN_POS, NONCE_END_POS);
        reidentifier = new EncryptionPseudonymizer(secureConfig);
    }

    @Test
    public void reidentifySingleIdentifier() {
        String pseudonym = "ZTRmMjYyOTY3MmZlOfCfTky6D1AJ3cqHoniBnuf9X6NVU2d/ggRkTLzhlOryU+A=";
        String identifier = reidentifier.reidentify(pseudonym);
        assertEquals("Hello Data Privacy!", identifier);
    }

    @Test
    public void reidentifySingleIdentifierNoCaching() {
        SecureConfig secureConfig = new SecureConfig(SECRET_KEY, NONCE_KEY, NONCE_BEGIN_POS, NONCE_END_POS);
        reidentifier = new EncryptionPseudonymizer(secureConfig, false);

        String pseudonym = "ZTRmMjYyOTY3MmZlOfCfTky6D1AJ3cqHoniBnuf9X6NVU2d/ggRkTLzhlOryU+A=";
        String identifier = reidentifier.reidentify(pseudonym);
        assertEquals("Hello Data Privacy!", identifier);
    }



}
