package com.sgd.ridc.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "ridc")
public class RidcProperties {

    private Client client;
    private Context context;

}
