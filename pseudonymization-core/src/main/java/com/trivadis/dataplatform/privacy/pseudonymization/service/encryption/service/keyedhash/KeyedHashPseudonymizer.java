package com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.service.keyedhash;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.service.Pseudonymizer;
import com.trivadis.dataplatform.privacy.utils.HmacUtils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.logging.Logger;

public class KeyedHashPseudonymizer implements Pseudonymizer {
    private static final Logger log = Logger.getLogger("Pseudonymizer.class");

    private static final Integer DEFAULT_CACHE_SIZE = 400000;

    private static final int NONCE_SIZE = 12;
    private Cache<String, String> cache = null;
    private boolean cacheEnabled = false;

    private final SecureRandom random = new SecureRandom();

    private SecureConfig secureConfig;

    public KeyedHashPseudonymizer(SecureConfig secureConfig) {
        this(secureConfig, true);
    }

    public KeyedHashPseudonymizer(SecureConfig secureConfig, boolean enableCache) {
        if (enableCache) {
            this.cache = CacheBuilder.newBuilder()
                .maximumSize(Optional.ofNullable(System.getenv("PSEUDONYMIZATION_CACHE_SIZE")).map(Integer::parseInt).orElse(DEFAULT_CACHE_SIZE))
                .softValues()
                .build();
            this.cacheEnabled = true;
        }
        this.secureConfig = secureConfig;
    }

    @Override
    public String pseudonymize(String identifier) {
        return pseudonymize(identifier, true);
    }

    @Override
    public String pseudonymize(String identifier, boolean deterministic) {
        String pseudonym = null;
        if (identifier != null) {
            try {
                if (deterministic) {
                    if (cacheEnabled) {
                        pseudonym = cache.getIfPresent(identifier);
                    }
                    if (pseudonym == null) {
                        HmacUtils hm = new HmacUtils();
                        pseudonym = hm.generateHmac256(identifier.getBytes(StandardCharsets.UTF_8), secureConfig.getSecretKeyDecoded());
                        if (cacheEnabled) {
                            cache.put(identifier, pseudonym);
                        }
                    }
                } else {
                    HmacUtils hm = new HmacUtils();
                    byte[] nonce = new byte[NONCE_SIZE];
                    random.nextBytes(nonce);

                    byte[] identifierEncoded =  identifier.getBytes(StandardCharsets.UTF_8);
                    final byte[] nonceAndIdentifierEncoded = new byte[nonce.length + identifierEncoded.length];

                    System.arraycopy(nonce, 0, nonceAndIdentifierEncoded, 0, nonce.length);
                    System.arraycopy(identifierEncoded, 0, nonceAndIdentifierEncoded, nonce.length, identifierEncoded.length);

                    pseudonym = hm.generateHmac256(nonceAndIdentifierEncoded, secureConfig.getSecretKeyDecoded());
                }
            } catch (Exception e) {
                log.severe("Error during pseudonymisation, setting null as result: " + identifier + " Exception = " + e);
            }
        }
        return pseudonym;
    }

    @Override
    public String[] pseudonymize(String[] identifiers) {
        return pseudonymize(identifiers, true);
    }

    @Override
    public String[] pseudonymize(String[] identifiers, boolean deterministic) {
        String[] pseudonyms = new String[identifiers.length];
        for (int i = 0; i < identifiers.length; i++) {
            final String identifier = identifiers[i];
            String pseudonym = pseudonymize(identifier, deterministic);
            pseudonyms[i] = pseudonym;
        }
        return pseudonyms;
    }
}
