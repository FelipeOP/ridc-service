package com.sgd.ridc.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sgd.ridc.service.RidcService;
import com.sgd.ridc.service.RidcServiceImpl;
import com.sgd.ridc.service.RidcServiceProxy;

import lombok.AllArgsConstructor;
import oracle.stellent.ridc.IdcClientException;

@Configuration
@ConditionalOnClass(RidcService.class)
@EnableConfigurationProperties(RidcProperties.class)
@AllArgsConstructor
public class RidcAutoConfiguration {

    private final RidcProperties ridcProperties;

    /**
     * @deprecated Eliminar cuando se termine la migración a 12c
     */
    @Deprecated
    @Bean
    @ConfigurationProperties(prefix = "ridc.owcc11g")
    RidcProperties ridcProperties11g() {
        return new RidcProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    RidcService ridcService() throws IdcClientException {
        RidcServiceImpl ridcService = new RidcServiceImpl(ridcProperties);
        RidcServiceImpl ridcService11g = new RidcServiceImpl(ridcProperties11g());
        return new RidcServiceProxy(ridcService, ridcService11g);
    }
}
