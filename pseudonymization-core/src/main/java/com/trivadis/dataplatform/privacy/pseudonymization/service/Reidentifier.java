package com.trivadis.dataplatform.privacy.pseudonymization.service;

public interface Reidentifier {
    public String reidentify (String pseudonym);

    public String[] reidentify (String[] pseudonyms);
}
