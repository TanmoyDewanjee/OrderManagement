package io.welldev.util;

import io.welldev.entity.Customer;

import javax.servlet.http.HttpSession;

public class AuthorizationUtility {

    public static String getLoginToken(HttpSession session){
        return (String) session.getAttribute("token");
    }

    public static void setLoginToken(HttpSession session, Customer customer){
        String token = customer.getUsername();
        token = HashingUtility.getHashedString(token);

        session.setAttribute("token", token);
    }

    public static boolean isCustomerAuthorized(String username, HttpSession session){

        String token = HashingUtility.getHashedString(username);

        return token.equals(getLoginToken(session));
    }

    public static boolean isCustomerLoggedIn(HttpSession session){
        return getLoginToken(session) != null;
    }

}
