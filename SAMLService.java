package com.example.saml.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.SAMLAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class SAMLService {

    public Map<String, String> getUserDetails(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof SAMLAuthenticationToken) {
            SAMLAuthenticationToken samlToken = (SAMLAuthenticationToken) authentication;
            SAMLCredential credential = (SAMLCredential) samlToken.getCredentials();

            // Extract user details
            String userId = credential.getNameID().getValue();
            String email = credential.getAttributeAsString("email");
            String firstName = credential.getAttributeAsString("firstName");
            String lastName = credential.getAttributeAsString("lastName");

            // Store in session
            session.setAttribute("userId", userId);
            session.setAttribute("email", email);
            session.setAttribute("firstName", firstName);
            session.setAttribute("lastName", lastName);

            // Prepare response map
            Map<String, String> userDetails = new HashMap<>();
            userDetails.put("userId", userId);
            userDetails.put("email", email);
            userDetails.put("firstName", firstName);
            userDetails.put("lastName", lastName);

            return userDetails;
        }

        return null;
    }
}
