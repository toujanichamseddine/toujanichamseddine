package com.example.saml.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.SAMLAuthenticationToken;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLBootstrap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "redirect:/saml/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/saml/logout";
    }

    @GetMapping("/user")
    @ResponseBody
    public String getUserInfo(Principal principal, HttpSession session) {
        if (principal instanceof SAMLAuthenticationToken) {
            SAMLAuthenticationToken samlToken = (SAMLAuthenticationToken) principal;
            SAMLCredential credential = (SAMLCredential) samlToken.getCredentials();

            // Retrieve user details
            String userId = credential.getNameID().getValue();
            String email = credential.getAttributeAsString("email");
            String firstName = credential.getAttributeAsString("firstName");
            String lastName = credential.getAttributeAsString("lastName");

            // Store in session
            session.setAttribute("userId", userId);
            session.setAttribute("email", email);
            session.setAttribute("firstName", firstName);
            session.setAttribute("lastName", lastName);

            return "Authenticated User: " + firstName + " " + lastName + " (" + email + ")";
        }
        return "No user authenticated.";
    }
}
