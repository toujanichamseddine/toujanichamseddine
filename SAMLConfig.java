package com.example.config;

import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.SAMLBootstrap;
import org.springframework.security.saml.SAMLDiscovery;
import org.springframework.security.saml.metadata.CachingMetadataManager;
import org.springframework.security.saml.processor.*;
import org.springframework.security.saml.websso.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SAMLConfig {

    @Bean
    public SAMLProcessor samlProcessor() {
        List<SAMLBinding> bindings = new ArrayList<>();
        bindings.add(new HTTPRedirectDeflateBinding());
        bindings.add(new HTTPPostBinding());
        bindings.add(new HTTPArtifactBinding(new ArtifactResolutionProfileImpl(new HttpClientFactory().createDefaultHttpClient())));
        bindings.add(new HTTPSOAP11Binding());
        bindings.add(new HTTPPAOS11Binding());

        return new SAMLProcessorImpl(bindings);
    }

    @Bean
    public SAMLBootstrap samlBootstrap() {
        return new SAMLBootstrap();
    }

    @Bean
    public SAMLDiscovery samlDiscovery() {
        return new SAMLDiscovery();
    }

    @Bean
    public CachingMetadataManager metadataManager() throws MetadataProviderException {
        return new CachingMetadataManager(new ArrayList<>());
    }
}
