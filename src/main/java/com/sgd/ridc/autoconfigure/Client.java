package com.sgd.ridc.autoconfigure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private String url;
    private String httpLibrary;

}
