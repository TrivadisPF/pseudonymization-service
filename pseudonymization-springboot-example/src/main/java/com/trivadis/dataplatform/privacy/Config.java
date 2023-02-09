package com.trivadis.dataplatform.privacy;

import com.trivadis.dataplatform.privacy.pseudonymization.service.Pseudonymizer;
import com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.EncryptionPseudonymizer;
import com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.SecureConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Value("${secretKey}")
    String secretKey;

    @Value("${nonceKey}")
    String nonceKey;

    @Value("${nonceBeginPos}")
    Integer nonceBeginPos;

    @Value("${nonceEndPos}")
    Integer nonceEndPos;

    @Bean
    public Pseudonymizer getPseudonymizer() {
        SecureConfig secureConfig = new SecureConfig(secretKey, nonceKey, nonceBeginPos, nonceEndPos);
        return new EncryptionPseudonymizer(secureConfig);
    }

}
