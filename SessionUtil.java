package com.example.util;

import com.example.model.SAMLUser;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static final String USER_SESSION_ATTRIBUTE = "user";

    // ✅ Save SAMLUser to session
    public static void saveUserToSession(HttpSession session, SAMLUser samlUser) {
        session.setAttribute(USER_SESSION_ATTRIBUTE, samlUser);
    }

    // ✅ Retrieve SAMLUser from session
    public static SAMLUser getUserFromSession(HttpSession session) {
        Object userObj = session.getAttribute(USER_SESSION_ATTRIBUTE);
        if (userObj instanceof SAMLUser) {
            return (SAMLUser) userObj;
        }
        return null;
    }

    // ✅ Clear session on logout
    public static void clearSession(HttpSession session) {
        session.invalidate();
    }
}
