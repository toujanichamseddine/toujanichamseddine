package com.example.saml.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(Model model, HttpSession session) {
        // Retrieve user information from session
        String userId = (String) session.getAttribute("userId");
        String email = (String) session.getAttribute("email");
        String firstName = (String) session.getAttribute("firstName");
        String lastName = (String) session.getAttribute("lastName");

        if (userId != null) {
            model.addAttribute("message", "Welcome " + firstName + " " + lastName + " (" + email + ")");
        } else {
            model.addAttribute("message", "Welcome, Guest! Please log in.");
        }

        return "home"; // This should correspond to WEB-INF/views/home.jsp
    }
}
