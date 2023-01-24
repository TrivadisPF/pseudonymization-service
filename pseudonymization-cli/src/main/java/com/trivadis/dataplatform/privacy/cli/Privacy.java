package com.trivadis.dataplatform.privacy.cli;

import com.trivadis.dataplatform.privacy.utils.SecretKeyAESUtils;

//import javafx.util.Pair;
import picocli.CommandLine;

import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.trivadis.dataplatform.privacy.utils.SecretKeyAESUtils.generateKey;
import static com.trivadis.dataplatform.privacy.utils.SecretKeyAESUtils.nounceStart;
import static com.trivadis.dataplatform.privacy.utils.Vault.vaultWriter;

@CommandLine.Command(name = "privacy", mixinStandardHelpOptions = true, version = "privacy 1.0",
        description = "xxxxxx")
public class Privacy implements Callable<Integer> {

    //private Pair<Integer,Integer> generateNoncePosition() {
    //    return null;
    //}

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        // translate the key-generator.py python script
        int secretKeyLength = 256;
        int nonceKeyLength = 64;
        int nounceMin = 0;
        int nounceMax = 20;

        Map<String, String> keyPair = new HashMap<>();

        // 1. Create the AES secret key of 256bit
        byte[] secretKey = generateKey(secretKeyLength);
        System.out.println("The secret key is: ");
        String secretKeyEncoded = Base64.getEncoder().encodeToString(secretKey);
        keyPair.put("secretKey",secretKeyEncoded);
        System.out.println(secretKeyEncoded);

        // 2. Create the HMAC_SHA256 secret key (nonce) of 64bit
        byte[] nonceKey = generateKey(nonceKeyLength);
        System.out.println("The nounce is: ");
        String nonceKeyEncoded = Base64.getEncoder().encodeToString(nonceKey);
        keyPair.put("nonceKey",nonceKeyEncoded);
        System.out.println(nonceKeyEncoded);

        // 3. Generate the Nonce position (begin and end)
        int nonceBeginPos = nounceStart(nounceMin,nounceMax);
        int nonceEndPos = nonceBeginPos + 12;
        System.out.println("The nounce start is: ");
        System.out.println(nonceBeginPos);
        keyPair.put("nonceBeginPos",String.valueOf(nonceBeginPos));
        System.out.println("The nounce end is: ");
        keyPair.put("nonceEndPos",String.valueOf(nonceEndPos));
        System.out.println(nonceEndPos);

        int keyToVault = vaultWriter(keyPair);

        // 4. Upload it to Vault
        //      {
        //          "nonceEndPos": 21,
        //          "nonceKey": "2hfbSR4JxbE=",
        //          "nonceBeginPos": 9,
        //          "secretKey": "eHkCnEhjfzsAHzNXCTGHaImv514CqfcPpoCgb2c0iuY="
        //      }
        return 0;
    }
    public static void main(String... args) {
        int exitCode = new CommandLine(new Privacy()).execute(args);
        System.exit(exitCode);
    }
}
