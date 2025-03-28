package com.example.service;

import com.example.model.SAMLUser;
import com.example.security.SAMLDetailsService;
import com.example.util.SessionUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SAMLService {

    private final SAMLDetailsService samlDetailsService;

    public SAMLService(SAMLDetailsService samlDetailsService) {
        this.samlDetailsService = samlDetailsService;
    }

    public void processSAMLAuthentication(Authentication authentication, HttpSession session) {
        if (authentication instanceof Saml2Authentication) {
            Saml2Authentication samlAuth = (Saml2Authentication) authentication;
            
            // Extract user details
            SAMLUser samlUser = samlDetailsService.loadUserDetails(samlAuth);

            // Save user details to session
            SessionUtil.saveUserToSession(session, samlUser);
        }
    }
}
