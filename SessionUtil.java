package com.example.saml.util;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class SessionUtil {

    /**
     * Store user details in session.
     */
    public static void storeUserInSession(HttpSession session, String userId, String email, String firstName, String lastName) {
        session.setAttribute("userId", userId);
        session.setAttribute("email", email);
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);
    }

    /**
     * Retrieve user details from session.
     */
    public static Map<String, String> getUserFromSession(HttpSession session) {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("userId", (String) session.getAttribute("userId"));
        userDetails.put("email", (String) session.getAttribute("email"));
        userDetails.put("firstName", (String) session.getAttribute("firstName"));
        userDetails.put("lastName", (String) session.getAttribute("lastName"));

        return userDetails;
    }

    /**
     * Clear session data on logout.
     */
    public static void clearSession(HttpSession session) {
        session.invalidate();
    }
}
