package com.sgd.ridc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.sgd.ridc.model.ServiceRequest;

import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.model.DataBinder;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { RidcService.class })
@ActiveProfiles("dev")
@Disabled
class RidcServiceTests {

    @Autowired
    private RidcService ridcService;

    private static final String VERSION_12C = "12c";

    @Test
    void givenValidPingServerParams_whenSendRequest_thenSuccess() throws IdcClientException {
        ServiceRequest request = new ServiceRequest("PING_SERVER", VERSION_12C);
        DataBinder binder = ridcService.sendRequest(request);

        String status = binder.getLocalData().get("StatusCode");
        assertThat(status).isNullOrEmpty();
    }

    @Test
    void givenInvalidService_whenSendRequest_thenThrowIdcClientException() throws IdcClientException {
        ServiceRequest request = new ServiceRequest("INVALID_SERVICE", VERSION_12C);
        assertThatThrownBy(() -> ridcService.sendRequest(request))
                .isInstanceOf(IdcClientException.class)
                .hasMessageContaining("No service defined for INVALID_SERVICE");
    }

    @Test
    void givenValidServiceWithoutParameters_whenSendRequest_thenThrowIdcClientException() throws IdcClientException {
        ServiceRequest request = new ServiceRequest("GET_SEARCH_RESULTS", VERSION_12C);
        assertThatThrownBy(() -> ridcService.sendRequest(request))
                .isInstanceOf(IdcClientException.class)
                .hasMessageContaining("Unable to execute service GET_SEARCH_RESULTS");
    }

}
