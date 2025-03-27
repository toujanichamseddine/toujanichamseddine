package com.example.saml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.metadata.MetadataGeneratorFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SAMLAuthenticationProvider samlAuthenticationProvider() {
        SAMLAuthenticationProvider provider = new SAMLAuthenticationProvider();
        provider.setForcePrincipalAsString(false);
        return provider;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/", "/saml/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll();
    }

    @Bean
    public MetadataGeneratorFilter metadataGeneratorFilter() {
        return new MetadataGeneratorFilter(null);
    }
}
