package com.trivadis.dataplatform.privacy.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.trivadis.dataplatform.privacy.aesgcmsiv.EncryptionAESGCMSIV;
import com.trivadis.dataplatform.privacy.pseudonymization.Pseudonymization;

import java.util.Optional;

public class PseudonymizerImpl implements Pseudonymizer {
    private static final Integer DEFAULT_CACHE_SIZE = 400000;
    private Cache<String, String> cache = null;

    private Pseudonymization pseudonymization = null;

    public PseudonymizerImpl(SecureConfig secureConfig) {
        EncryptionAESGCMSIV aesgcmsiv = new EncryptionAESGCMSIV(secureConfig.getSecureKeyDecoded(), secureConfig.getNonceKeyDecoded(), secureConfig.getNonceBeginPos(), secureConfig.getNoceEndPos());
        cache = CacheBuilder.newBuilder()
                .maximumSize(Optional.ofNullable(System.getenv("PSEUDONYMIZATION_CACHE_SIZE")).map(Integer::parseInt).orElse(DEFAULT_CACHE_SIZE))
                .softValues()
                .build();

        pseudonymization = new Pseudonymization();
        pseudonymization.setAesgcmsiv(aesgcmsiv);
        pseudonymization.setCache(cache);
    }

    @Override
    public String pseudonymize(String identifier) {
        return pseudonymization.pseudonymize(identifier, true);
    }

    @Override
    public String[] pseudonymize(String[] identifiers) {
        return pseudonymization.pseudonymize(identifiers, true);
    }
}
