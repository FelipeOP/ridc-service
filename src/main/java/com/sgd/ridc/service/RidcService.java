package com.sgd.ridc.service;

import com.sgd.ridc.model.ServiceRequest;

import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.model.DataBinder;

public interface RidcService {

    public DataBinder sendRequest(ServiceRequest serviceRequest) throws IdcClientException;

}
