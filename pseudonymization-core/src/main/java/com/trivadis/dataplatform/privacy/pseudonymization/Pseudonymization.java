package com.trivadis.dataplatform.privacy.pseudonymization;

import com.google.common.cache.Cache;
import com.trivadis.dataplatform.privacy.aesgcmsiv.EncryptionAESGCMSIV;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pseudonymization {
    private static final Logger log = Logger.getLogger("Pseudonymization.class");

    private EncryptionAESGCMSIV aesgcmsiv;

    private Cache<String, String> cache;

    public void setAesgcmsiv(EncryptionAESGCMSIV aesgcmsiv) {
        this.aesgcmsiv = aesgcmsiv;
    }

    public void setCache(Cache<String, String> cache) {
        this.cache = cache;
    }

    public Pseudonymization() {

    }

    /**
     * param cache the cache
     * param aesgcmsiv encryption class object
    */
    public Pseudonymization(EncryptionAESGCMSIV aesgcmsiv, Cache<String, String> cache) {
        this.aesgcmsiv = aesgcmsiv;
        this.cache = cache;
    }

    /**
     * Creates a new pseudonym for a plaintext value
     * param identifier the value to create the pseudonym for
     * return the pseudonym
     */
    public String pseudonymize(String identifier, Boolean deterministic) {
        String pseudonym = null;
        try {
            if (deterministic) {
                pseudonym = cache.getIfPresent(identifier);
                if (pseudonym == null) {
                    pseudonym = Base64.getEncoder().encodeToString(aesgcmsiv.encrypt(identifier.getBytes(StandardCharsets.UTF_8), "".getBytes(), true));
                    cache.put(identifier, pseudonym);
                }
            } else {
                pseudonym = Base64.getEncoder().encodeToString(aesgcmsiv.encrypt(identifier.getBytes(StandardCharsets.UTF_8), "".getBytes()));
            }
        } catch (Exception e) {
            log.log(Level.parse("SEVERE"),"Error during pseudonymisation, setting null as result: " + identifier + e);
        }
        return pseudonym;
    }

    /**
     * Creates a new
     * param request request event
     * param cache the cache
     * param aesgcmsiv encryption class object
     *
     */
    public String[] pseudonymize(String[] identifiers, Boolean deterministic) {
        String[] pseudonyms = new String[identifiers.length];
        for (int i = 0; i < identifiers.length; i++) {
            final String identifier = identifiers[i];
            String pseudonym = pseudonymize(identifier, deterministic);
            pseudonyms[i] = pseudonym;
        }
        return pseudonyms;
    }
}


