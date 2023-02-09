package com.trivadis.dataplatform.privacy.pseudonymization.service.encryption;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.trivadis.dataplatform.privacy.aesgcmsiv.EncryptionAESGCMSIV;
import com.trivadis.dataplatform.privacy.pseudonymization.service.Pseudonymizer;
import com.trivadis.dataplatform.privacy.pseudonymization.service.Reidentifier;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.logging.Logger;

public class EncryptionPseudonymizer implements Pseudonymizer, Reidentifier {
    private static final Logger log = Logger.getLogger("EncryptionPseudonymizer.class");

    private static final Integer DEFAULT_CACHE_SIZE = 400000;
    private Cache<String, String> cache = null;
    private boolean cacheEnabled = false;

    private EncryptionAESGCMSIV aesgcmsiv;

    public EncryptionPseudonymizer(SecureConfig secureConfig) {
        this(secureConfig, true);
    }

    public EncryptionPseudonymizer(SecureConfig secureConfig, boolean enableCache) {
        aesgcmsiv = new EncryptionAESGCMSIV(secureConfig.getSecretKeyDecoded(), secureConfig.getNonceKeyDecoded(), secureConfig.getNonceBeginPos(), secureConfig.getNonceEndPos());

        if (enableCache) {
            cache = CacheBuilder.newBuilder()
                    .maximumSize(Optional.ofNullable(System.getenv("PSEUDONYMIZATION_CACHE_SIZE")).map(Integer::parseInt).orElse(DEFAULT_CACHE_SIZE))
                    .softValues()
                    .build();
            cacheEnabled = true;
        }
    }

    @Override
    public String pseudonymize(String identifier) {
        return pseudonymize(identifier, true);
    }

    /**
     * Creates a new pseudonym for a plaintext value
     * param identifier the value to create the pseudonym for
     * return the pseudonym
     */
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
                        pseudonym = Base64.getEncoder().encodeToString(aesgcmsiv.encrypt(identifier.getBytes(StandardCharsets.UTF_8), "".getBytes(), true));
                        if (cacheEnabled) {
                            cache.put(identifier, pseudonym);
                        }
                    }
                } else {
                    pseudonym = Base64.getEncoder().encodeToString(aesgcmsiv.encrypt(identifier.getBytes(StandardCharsets.UTF_8), "".getBytes()));
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

    /**
     * Creates a new
     * param request request event
     * param cache the cache
     * param aesgcmsiv encryption class object
     *
     */
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

    /**
     * De-Identify the pseudonym
     * param pseudonym the pseudonym to de-identify
     * return the identification for the pseudony
     */
    @Override
    public String reidentify (String pseudonym) {
        String identifier = null;
        try {
            if (cacheEnabled) {
                identifier = cache.getIfPresent(pseudonym);
            }
            Optional<byte[]> id;
            if (identifier == null) {
                try{
                    id = aesgcmsiv.decrypt(Base64.getDecoder().decode(pseudonym), "".getBytes());
                }catch(IllegalArgumentException e){
                    id = Optional.empty();
                }
                if (id.isPresent()) {
                    identifier = new String(id.get(), StandardCharsets.UTF_8);
                    if (cacheEnabled) {
                        cache.put(pseudonym, identifier);
                    }
                }
            }
        } catch (Exception e) {
            log.severe("Error during Reidentification, setting null as result: " + pseudonym + " Exception = " + e);
        }
        return identifier;
    }

    /**
     * De-Identify the pseudonyms
     * param pseudonyms the pseudonym to de-identify
     * return the identification for the pseudony
     */
    @Override
    public String[] reidentify(String[] pseudonyms) {
        String[] identifiers = new String[pseudonyms.length];

        for (int i = 0; i < pseudonyms.length; i++) {
            String pseudonym = pseudonyms[i];
            String identifier = reidentify(pseudonym);
            identifiers[i] = identifier;
        }
        return identifiers;
    }

}
