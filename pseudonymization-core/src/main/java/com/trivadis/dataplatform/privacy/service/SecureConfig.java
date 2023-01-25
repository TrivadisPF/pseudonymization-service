package com.trivadis.dataplatform.privacy.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Base64;

@AllArgsConstructor
@Data
public class SecureConfig {
    private String secretKey;
    private String nonceKey;
    private Integer nonceBeginPos;
    private Integer noceEndPos;

    public byte[] getSecretKeyDecoded() {
        return Base64.getDecoder().decode(this.secretKey);
    }

    public byte[] getNonceKeyDecoded() {
        return Base64.getDecoder().decode(this.nonceKey);
    }
}
