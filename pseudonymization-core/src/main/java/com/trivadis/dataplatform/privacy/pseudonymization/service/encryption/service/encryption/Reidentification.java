package com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.service.encryption;

import com.google.common.cache.Cache;
import com.trivadis.dataplatform.privacy.aesgcmsiv.EncryptionAESGCMSIV;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reidentification {

    private static final Logger log = Logger.getLogger("Reidentification.class");

    private EncryptionAESGCMSIV aesgcmsiv;

    private Cache<String, String> cache;

    public void setAesgcmsiv(EncryptionAESGCMSIV aesgcmsiv) {
        this.aesgcmsiv = aesgcmsiv;
    }

    public void setCache(Cache<String, String> cache) {
        this.cache = cache;
    }

    public Reidentification() {

    }

    /**
     * param cache the cache
     * param aesgcmsiv encryption class object
     */
    public Reidentification(EncryptionAESGCMSIV aesgcmsiv, Cache<String, String> cache) {
        this.aesgcmsiv = aesgcmsiv;
        this.cache = cache;
    }

    public String reidentify (String pseudonym) {
        String identifier = null;
        try {
            identifier = cache.getIfPresent(pseudonym);
            Optional<byte[]> id;
            if (identifier == null) {
                try{
                    id = aesgcmsiv.decrypt(Base64.getDecoder().decode(pseudonym), "".getBytes());
                }catch(IllegalArgumentException e){
                    id = Optional.empty();
                }
                if (id.isPresent()) {
                    identifier = new String(id.get(), StandardCharsets.UTF_8);
                    cache.put(pseudonym, identifier);
                }
            }
        } catch (Exception e) {
            log.log(Level.parse("ERROR"),"Error during Reidentification, setting null as result: " + pseudonym + e);
        }
        return identifier;
    }

    /**
     * Creates a new
     * param request request event
     * param cache the cache
     * param aesgcmsiv encryption class object
     *
     */
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
