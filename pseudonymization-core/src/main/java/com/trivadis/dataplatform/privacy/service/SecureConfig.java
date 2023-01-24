package com.trivadis.dataplatform.privacy.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Base64;

@AllArgsConstructor
@Data
public class SecureConfig {
    private String secureKey;
    private String nonceKey;
    private Integer nonceBeginPos;
    private Integer noceEndPos;

    public byte[] getSecureKeyDecoded() {
        return Base64.getDecoder().decode(this.secureKey);
    }

    public byte[] getNonceKeyDecoded() {
        return Base64.getDecoder().decode(this.nonceKey);
    }
}
