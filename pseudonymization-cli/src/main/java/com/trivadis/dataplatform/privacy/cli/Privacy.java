package com.trivadis.dataplatform.privacy.cli;

import javafx.util.Pair;
import picocli.CommandLine;

import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "privacy", mixinStandardHelpOptions = true, version = "privacy 1.0",
        description = "xxxxxx")
public class Privacy implements Callable<Integer> {




    private Pair<Integer,Integer> generateNoncePosition() {
        return null;
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        // translate the key-generator.py python script

        // 1. Create the AES secret key of 256bit
        // 2. Create the HMAC_SHA256 secret key (nonce) of 64bit
        // 3. Generate the Nonce position (begin and end)
        // 4. Upload it to Vault
        //      {
        //          "nonceEndPos": 21,
        //          "nonceKey": "2hfbSR4JxbE=",
        //          "nonceBeginPos": 9,
        //          "secretKey": "eHkCnEhjfzsAHzNXCTGHaImv514CqfcPpoCgb2c0iuY="
        //      }
        return 0;

    }
}
