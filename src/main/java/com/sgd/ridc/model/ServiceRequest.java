package com.sgd.ridc.model;

import java.util.Map;
import java.util.TreeMap;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.Data;

@Data
public class ServiceRequest {

    @JsonAlias("IdcService")
    @NotEmpty(message = "IdcService is required")
    private String idcService;

    @JsonAlias({ "v", "useVersion", "version" })
    @Pattern(regexp = "12c|11g", message = "Invalid version, must be 12c or 11g")
    @NotEmpty(message = "Version is required")
    private String version;

    private Map<String, String> parameters;

    public ServiceRequest(String idcService, String version) {
        parameters = new TreeMap<>();
        this.idcService = idcService;
        this.version = version;
    }

    @JsonAnySetter
    public void setParameter(String parameterName, String value) {
        parameters.put(parameterName, value);
    }

}
