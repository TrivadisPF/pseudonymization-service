package com.trivadis.dataplatform.privacy;

import com.trivadis.dataplatform.privacy.service.Pseudonymizer;
import com.trivadis.dataplatform.privacy.service.PseudonymizerImpl;
import com.trivadis.dataplatform.privacy.service.SecureConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @Autowired
    private Pseudonymizer pseudonymizer;

    public void doIt(String identifier) {
        System.out.println(pseudonymizer.pseudonymize(identifier));
    }

}
