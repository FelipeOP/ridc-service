package com.sgd.ridc.service;

import com.sgd.ridc.autoconfigure.RidcProperties;
import com.sgd.ridc.model.ServiceRequest;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.protocol.ServiceResponse;

public class RidcServiceImpl implements RidcService {

    private final IdcClient<?, ?, ?> idcClient;
    private final IdcContext idcContext;

    public RidcServiceImpl(RidcProperties ridcProperties) throws IdcClientException {
        IdcClientManager manager = new IdcClientManager();
        idcClient = manager.createClient(ridcProperties.getClient().getUrl());
        idcClient.getConfig().setProperty("http.library", ridcProperties.getClient().getHttpLibrary());
        idcContext = new IdcContext(
                ridcProperties.getContext().getUser(),
                ridcProperties.getContext().getPassword());
    }

    @Override
    public DataBinder sendRequest(ServiceRequest serviceRequest) throws IdcClientException {
        DataBinder requestBinder = idcClient.createBinder();
        requestBinder.putLocal("IdcService", serviceRequest.getIdcService());
        serviceRequest.getParameters().forEach(requestBinder::putLocal);

        ServiceResponse response = idcClient.sendRequest(idcContext, requestBinder);
        return response.getResponseAsBinder(true);
    }

}
