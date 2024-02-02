package com.sgd.ridc.autoconfigure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Context {

    private String user;
    private String password;
    private String timeZone;

}
