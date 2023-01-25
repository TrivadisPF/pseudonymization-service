package com.trivadis.dataplatform.privacy.utils;

import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.Versioned;

import java.util.Map;
import java.util.Scanner;

public class Vault {

    public static int vaultWriter(Map<String, String> keyPair) {
        VaultEndpoint vaultEndpoint = new VaultEndpoint();

        vaultEndpoint.setHost("172.17.0.1"); //IP from docker
        vaultEndpoint.setPort(8200);
        vaultEndpoint.setScheme("http");

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter Authentification Token: ");
        String token = reader.next();
        VaultTemplate vaultTemplate = new VaultTemplate(
                vaultEndpoint,
                new TokenAuthentication(token));
        System.out.println("Access granted!");
        //System.out.println("Enter path for secret");
        //String secretPath = reader.next();
        String secretPath = "secret";
        System.out.println("Enter name for secret");
        String secretName = reader.next();
        reader.close();


        Versioned.Metadata createResponse = vaultTemplate
                .opsForVersionedKeyValue(secretPath)
                .put(secretName, keyPair);

        System.out.println("Secret written successfully.");
        return 1;
    }
}
