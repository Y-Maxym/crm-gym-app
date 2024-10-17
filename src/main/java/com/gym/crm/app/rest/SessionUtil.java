package com.gym.crm.app.rest;

import com.gym.crm.app.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    public static User getSessionUser(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        return (User) session.getAttribute("user");
    }
}
