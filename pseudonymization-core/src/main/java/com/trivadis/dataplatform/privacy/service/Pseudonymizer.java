package com.trivadis.dataplatform.privacy.service;

public interface Pseudonymizer {

    public String pseudonymize (String identifier);

    public String[] pseudonymize (String[] identifiers);

}
