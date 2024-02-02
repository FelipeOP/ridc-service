package com.sgd.ridc.config;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.sgd.ridc.autoconfigure.Client;
import com.sgd.ridc.autoconfigure.Context;
import com.sgd.ridc.autoconfigure.RidcProperties;
import com.sgd.ridc.model.ServiceRequest;
import com.sgd.ridc.service.RidcService;
import com.sgd.ridc.service.RidcServiceImpl;

import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.protocol.http.HttpProtocolException;

@ActiveProfiles("dev")
@Disabled
class RidcConfigurationTests {

    private static RidcProperties ridcProperties;

    private static final String DEFAULT_VERSION = "12c";
    private static final String DEFAULT_SERVICE = "PING_SERVER";
    private static final String DEFAULT_URL = "http://100.69.19.57:8200/cs/idcplg";
    private static final String DEFAULT_LIBRARY = "apache4";

    @BeforeAll
    static void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        ridcProperties = new RidcProperties();
        ridcProperties.setClient(new Client());
        ridcProperties.setContext(new Context());
    }

    @BeforeEach
    void setDefaultValues() {
        ridcProperties.getClient().setUrl(DEFAULT_URL);
        ridcProperties.getClient().setHttpLibrary(DEFAULT_LIBRARY);
    }

    @Test
    void givenInvalidUrl_whenCreateRidcService_thenThrowIdcClientException() throws IdcClientException {
        ridcProperties.getClient().setUrl("http:localhost:8080/cs/idcplg");
        assertThatThrownBy(() -> new RidcServiceImpl(ridcProperties))
                .isInstanceOf(IdcClientException.class)
                .hasMessageContaining("Invalid url");
    }

    @Test
    void givenInvalidUrlProtocol_whenCreateRidcService_thenThrowIdcClientException() throws IdcClientException {
        ridcProperties.getClient().setUrl("sftp://localhost:8080/cs/idcplg");
        assertThatThrownBy(() -> new RidcServiceImpl(ridcProperties))
                .isInstanceOf(IdcClientException.class)
                .hasMessageContaining("No provider registered for protocol");
    }

    @Test
    void givenInvalidLibrary_whenSendRequest_thenThrowIdcClientException() throws IdcClientException {
        ridcProperties.getClient().setHttpLibrary("oracle");
        ServiceRequest request = new ServiceRequest(DEFAULT_SERVICE, DEFAULT_VERSION);
        RidcService ridcService = new RidcServiceImpl(ridcProperties);

        assertThatThrownBy(() -> ridcService.sendRequest(request))
                .isInstanceOf(NoClassDefFoundError.class)
                .hasMessageContaining("ProtocolNotSuppException");
    }

    @Test
    void givenInvalidUser_whenSendRequest_thenThrowIdcClientException() throws IdcClientException {
        ridcProperties.getContext().setUser("invalidUser");
        ridcProperties.getContext().setPassword("invalidPassword");
        ServiceRequest request = new ServiceRequest(DEFAULT_SERVICE, DEFAULT_VERSION);
        RidcService ridcService = new RidcServiceImpl(ridcProperties);

        assertThatThrownBy(() -> ridcService.sendRequest(request))
                .isInstanceOf(HttpProtocolException.class)
                .hasMessageContaining("Form validation failed");
    }

    @Test
    void givenHostNotReachable_whenCreateRidcService_thenThrowIdcClientException() throws IdcClientException {
        ridcProperties.getClient().setUrl("http://invalidhost:8080/cs/idcplg");
        ServiceRequest request = new ServiceRequest(DEFAULT_SERVICE, DEFAULT_VERSION);
        RidcService ridcService = new RidcServiceImpl(ridcProperties);

        assertThatThrownBy(() -> ridcService.sendRequest(request))
                .isInstanceOf(IdcClientException.class)
                .hasMessageContaining("java.net.UnknownHostException: invalidhost");
    }

}
