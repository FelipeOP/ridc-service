package com.sgd.ridc.service;

import com.sgd.ridc.model.ServiceRequest;

import lombok.AllArgsConstructor;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.model.DataBinder;

@AllArgsConstructor
public class RidcServiceProxy implements RidcService {

    private final RidcService ridcService;
    private final RidcService ridcService11g;

    @Override
    public DataBinder sendRequest(ServiceRequest serviceRequest) throws IdcClientException {
        return "11g".equals(serviceRequest.getVersion()) ? ridcService11g.sendRequest(serviceRequest)
                : ridcService.sendRequest(serviceRequest);
    }

}
