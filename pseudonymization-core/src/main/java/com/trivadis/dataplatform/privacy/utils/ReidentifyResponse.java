package com.trivadis.dataplatform.privacy.utils;

import lombok.Data;
import lombok.NonNull;

@Data
public class ReidentifyResponse {
    @NonNull  public String[] identifiers;
}