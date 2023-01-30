package com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.service.encryption;

import java.util.Base64;

public class SecureConfig {
    private String secretKey;
    private String nonceKey;
    private Integer nonceBeginPos;
    private Integer nonceEndPos;

    public SecureConfig(String secretKey, String nonceKey, Integer nonceBeginPos, Integer nonceEndPos) {
        this.secretKey = secretKey;
        this.nonceKey = nonceKey;
        this.nonceBeginPos = nonceBeginPos;
        this.nonceEndPos = nonceEndPos;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getNonceKey() {
        return nonceKey;
    }

    public Integer getNonceBeginPos() {
        return nonceBeginPos;
    }

    public Integer getNonceEndPos() {
        return nonceEndPos;
    }

    public byte[] getSecretKeyDecoded() {
        return Base64.getDecoder().decode(this.secretKey);
    }

    public byte[] getNonceKeyDecoded() {
        return Base64.getDecoder().decode(this.nonceKey);
    }
}
