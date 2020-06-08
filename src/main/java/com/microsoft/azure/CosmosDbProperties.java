package com.microsoft.azure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CosmosDbProperties {

    private String uri;

    private String key;

    private String secondaryKey;

    private String database;

    private boolean populateQueryMetrics;

}
