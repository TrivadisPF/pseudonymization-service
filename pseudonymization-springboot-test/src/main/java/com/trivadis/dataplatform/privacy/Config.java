package com.trivadis.dataplatform.privacy;

import com.trivadis.dataplatform.privacy.pseudonymization.service.Pseudonymizer;
import com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.EncryptionPseudonymizer;
import com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.SecureConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Bean
    public Pseudonymizer getPseudonymizer() {
        SecureConfig secureConfig = new SecureConfig("eHkCnEhjfzsAHzNXCTGHaImv514CqfcPpoCgb2c0iuY=", "2hfbSR4JxbE=", 9, 21);
        return new EncryptionPseudonymizer(secureConfig);
    }

}
