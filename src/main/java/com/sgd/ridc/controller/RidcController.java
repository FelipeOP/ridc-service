package com.sgd.ridc.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgd.ridc.model.ServiceRequest;
import com.sgd.ridc.service.RidcService;

import lombok.AllArgsConstructor;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.model.DataBinder;

@AllArgsConstructor
@RestController
@RequestMapping("sgd/api/services/ridc")
public class RidcController {

    private final RidcService ridcService;

    @PostMapping(value = "/sendRequest")
    public ResponseEntity<DataBinder> sendRequest(@Valid @RequestBody ServiceRequest request)
            throws IdcClientException {
        return ResponseEntity.ok(ridcService.sendRequest(request));
    }

}
