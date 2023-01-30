package com.trivadis.dataplatform.privacy.pseudonymization.service.encryption.service;

public interface Pseudonymizer {

    public String pseudonymize (String identifier);

    public String pseudonymize (String identifier, boolean deterministic);


    public String[] pseudonymize (String[] identifiers);

    public String[] pseudonymize (String[] identifiers, boolean deterministic);
}
