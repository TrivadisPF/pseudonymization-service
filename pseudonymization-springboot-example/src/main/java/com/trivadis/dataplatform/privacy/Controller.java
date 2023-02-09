package com.trivadis.dataplatform.privacy;

import com.trivadis.dataplatform.privacy.pseudonymization.service.Pseudonymizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @Autowired
    private Pseudonymizer pseudonymizer;

    public void doIt(String identifier) {
        System.out.println(pseudonymizer.pseudonymize(identifier));
    }

}
