package com.example.saml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.metadata.MetadataGeneratorFilter;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.security.saml.websso.SAMLProcessingFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Configuration
@EnableWebSecurity
@ImportResource("classpath:securityContext.xml") // Load additional XML configurations if needed
public class SecurityConfig {

    @Bean
    public SAMLAuthenticationProvider samlAuthenticationProvider() {
        SAMLAuthenticationProvider provider = new SAMLAuthenticationProvider();
        provider.setForcePrincipalAsString(false); // Allows retrieving detailed user attributes
        return provider;
    }

    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher() {
            @Override
            public void sessionCreated(HttpSessionEvent event) {
                super.sessionCreated(event);
                System.out.println("Session Created: " + event.getSession().getId());
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent event) {
                super.sessionDestroyed(event);
                System.out.println("Session Destroyed: " + event.getSession().getId());
            }
        };
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/", "/saml/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
            .sessionManagement()
                .maximumSessions(1).expiredUrl("/login?expired")
                .and()
                .invalidSessionUrl("/login?invalid")
                .sessionFixation().migrateSession()
                .and()
            .addFilterBefore(metadataGeneratorFilter(), SecurityContextPersistenceFilter.class)
            .addFilterAfter(samlProcessingFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(logoutFilter(), LogoutFilter.class)
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    @Bean
    public MetadataGeneratorFilter metadataGeneratorFilter() {
        return new MetadataGeneratorFilter(metadataManager());
    }

    @Bean
    public MetadataManager metadataManager() {
        // Load metadata from XML file
        return new MetadataManager();
    }

    @Bean
    public SAMLProcessingFilter samlProcessingFilter() throws Exception {
        SAMLProcessingFilter filter = new SAMLProcessingFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    public LogoutFilter logoutFilter() {
        return new LogoutFilter("/login?logout", securityContextLogoutHandler());
    }

    @Bean
    public AuthenticationManagerBuilder authenticationManager() {
        return new AuthenticationManagerBuilder(null);
    }
}
