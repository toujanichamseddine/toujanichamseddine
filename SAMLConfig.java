package com.example.saml.config;

import org.opensaml.saml2.metadata.provider.FilesystemMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.xml.parse.BasicParserPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLBootstrap;
import org.springframework.security.saml.metadata.CachingMetadataManager;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.security.saml.metadata.MetadataGeneratorFilter;
import org.springframework.security.saml.processor.SAMLProcessorImpl;
import org.springframework.security.saml.websso.WebSSOProfileConsumerImpl;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Configuration
public class SAMLConfig {

    @Bean
    public SAMLAuthenticationProvider samlAuthenticationProvider() {
        SAMLAuthenticationProvider provider = new SAMLAuthenticationProvider();
        provider.setForcePrincipalAsString(false);
        return provider;
    }

    @Bean
    public MetadataProvider metadataProvider() throws Exception {
        File idpMetadataFile = new File("src/main/resources/idp-metadata.xml");
        FilesystemMetadataProvider provider = new FilesystemMetadataProvider(idpMetadataFile);
        provider.setParserPool(parserPool());
        return provider;
    }

    @Bean
    public CachingMetadataManager metadataManager() throws Exception {
        List<MetadataProvider> providers = Collections.singletonList(metadataProvider());
        return new CachingMetadataManager(providers);
    }

    @Bean
    public MetadataGenerator metadataGenerator() {
        MetadataGenerator generator = new MetadataGenerator();
        generator.setEntityId("saml-sp");
        generator.setIncludeDiscoveryExtension(false);
        return generator;
    }

    @Bean
    public MetadataGeneratorFilter metadataGeneratorFilter() {
        return new MetadataGeneratorFilter(metadataGenerator());
    }

    @Bean
    public SAMLProcessorImpl samlProcessor() {
        return new SAMLProcessorImpl(null);
    }

    @Bean
    public BasicParserPool parserPool() {
        BasicParserPool pool = new BasicParserPool();
        pool.setMaxPoolSize(10);
        return pool;
    }

    @Bean
    public WebSSOProfileConsumerImpl webSSOProfileConsumer() {
        return new WebSSOProfileConsumerImpl();
    }
}
