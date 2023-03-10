package com.trivadis.dataplatform.privacy.pseudonymization.service.keyedhash;

import com.trivadis.dataplatform.privacy.pseudonymization.service.Pseudonymizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PseudonymizationKeyedHashPseudonymizerTest {
    private static final String SECRET_KEY = "eHkCnEhjfzsAHzNXCTGHaImv514CqfcPpoCgb2c0iuY=";

    private Pseudonymizer pseudonymizer;

    @BeforeEach
    public void setup() {
        SecureConfig secureConfig = new SecureConfig(SECRET_KEY);
        pseudonymizer = new KeyedHashPseudonymizer(secureConfig, true);
    }

    @Test
    public void pseudonymizeSingleIdentifier() {
        String identifier = "Hello Data Privacy!";
        String pseudonym = pseudonymizer.pseudonymize(identifier);
        assertEquals("a337c3b722dc53ee91dcb8ab819828d51fe42b253cef85f299c649fdd77df815", pseudonym);
    }

    @Test
    public void pseudonymizeSingleIdentifierDeterministic() {
        String identifier = "Hello Data Privacy!";
        String pseudonym1 = pseudonymizer.pseudonymize(identifier, true);
        String pseudonym2 = pseudonymizer.pseudonymize(identifier, true);
        assertEquals(pseudonym1, pseudonym2);
    }

    @Test
    public void pseudonymizeSingleIdentifierNotDeterministic() {
        String identifier = "Hello Data Privacy!";
        String pseudonym1 = pseudonymizer.pseudonymize(identifier, false);
        String pseudonym2 = pseudonymizer.pseudonymize(identifier, false);
        assertNotEquals(pseudonym1, pseudonym2);
    }

    @Test
    public void pseudonymizeSingleIdentifierNoCaching() {
        SecureConfig secureConfig = new SecureConfig(SECRET_KEY);
        pseudonymizer = new KeyedHashPseudonymizer(secureConfig, false);

        String identifier = "Hello Data Privacy!";
        String pseudonym = pseudonymizer.pseudonymize(identifier, true);
    }

    @Test
    public void pseudonymizeMultipleIdentifiers() {
        String[] identifiers = new String[] {"First One", "Second One", "Third One"};
        String[] pseudonyms = pseudonymizer.pseudonymize(identifiers);

        assertTrue(pseudonyms.length == 3);
        assertEquals("47bd610a284eb01dec528939b7e781ae5a78a4271283af3d07db6781343a6524", pseudonyms[0]);
        assertEquals("18d54ba3181085659962434462413846b6ba80b49f496faa53893054a02038e6", pseudonyms[1]);
        assertEquals("bbdbe24fb6367bc98fb6f6088b9f3c94fb6389e7c51bc493ab44286295c5c7b1", pseudonyms[2]);
    }

    @Test
    public void pseudonymizeNULLIdentifier() {
        String identifier = null;
        String pseudonym = pseudonymizer.pseudonymize(identifier);
        assertNull(pseudonym);
    }

    @Test
    public void pseudonymizeEmptyListOfIdentifiers() {
        String[] identifiers = new String[] {};
        String[] pseudonyms = pseudonymizer.pseudonymize(identifiers);

        assertTrue(pseudonyms.length == 0);
    }

}
