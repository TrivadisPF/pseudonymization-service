package com.trivadis.dataplatform.privacy.pseudonymization.service.keyedhash;

import java.util.Base64;

public class SecureConfig {
    private String secretKey;

    public SecureConfig(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public byte[] getSecretKeyDecoded() {
        return Base64.getDecoder().decode(this.secretKey);
    }

}
