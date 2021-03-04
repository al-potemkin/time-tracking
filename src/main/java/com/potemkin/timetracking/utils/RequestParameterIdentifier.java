package com.potemkin.timetracking.utils;

import com.potemkin.timetracking.commands.factory.CommandType;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.entities.User;
import com.potemkin.timetracking.entities.UserType;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class identifies request parameters.
 * <p>
 */
public class RequestParameterIdentifier {

    /**
     * This method identifies a type command from request.
     *
     * @param request - object of request.
     * @return - type of command that will be executed.
     */
    public static CommandType getCommandFromRequest(HttpServletRequest request) {
        CommandType commandType = CommandType.DEFAULT;
        if (request.getParameter(Parameters.COMMAND) != null
                && !request.getParameter(Parameters.COMMAND).isEmpty()) {
            commandType = CommandType.valueOf(request.getParameter(Parameters.COMMAND).toUpperCase());
        }
        return commandType;
    }

    /**
     * This method receives users login and password from an object of request
     * and constructs an user with the corresponding fields.
     *
     * @param request - object of request.
     * @return - an user with the following fields: login and password.
     */
    public static User getUserLoginPasswordFromRequest(HttpServletRequest request) {
        User user = new User();
        String login = request.getParameter(Parameters.LOGIN);
        String password = request.getParameter(Parameters.PASSWORD);
        if (login != null && !login.isEmpty()) {
            user.setLogin(login);
        }
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }
        return user;
    }

    /**
     * This method creates a new user of application. This method is used during registration operation.
     * This method receives first name, surname, login and password from request
     * and sets this values to the corresponding fields of the user.
     *
     * @param request - an object of request with necessary parameters.
     * @return - user object with updated fields.
     */
    public static User getUserFromRequest(HttpServletRequest request) {
        User user = new User();
        String firstName = request.getParameter(Parameters.FIRST_NAME);
        String surName = request.getParameter(Parameters.SURNAME);
        String login = request.getParameter(Parameters.LOGIN);
        String password = request.getParameter(Parameters.PASSWORD);
        if ((firstName != null && !firstName.isEmpty())
                & (surName != null && !surName.isEmpty())
                & (login != null && !login.isEmpty())
                & (password != null && !password.isEmpty())) {
            user.setFirstName(firstName);
            user.setSurName(surName);
            user.setLogin(login);
            user.setPassword(password);
            user.setRequestAdd(false);
        }
        return user;
    }

    /**
     * This method receives a name of the page from the request.
     *
     * @param request - request object with necessaries parameters.
     * @return - a name of the page from request.
     */
    public static String getPageFromRequest(HttpServletRequest request) {
        String pageFromRequest = request.getSession().getAttribute(Parameters.BACK_PAGE).toString();
        request.getSession().removeAttribute(Parameters.BACK_PAGE);
        if (pageFromRequest != null && !pageFromRequest.isEmpty()) {
            return pageFromRequest;
        } else {
            return null;
        }
    }

    /**
     * This method checks if the fields of the form are filled.
     *
     * @param request - an object of request with necessary parameters.
     * @return - boolean value of the condition.
     */
    public static boolean areFieldsFilled(HttpServletRequest request) {
        if (!request.getParameter(Parameters.LOGIN).isEmpty()
                && !request.getParameter(Parameters.PASSWORD).isEmpty()
                && !request.getParameter(Parameters.FIRST_NAME).isEmpty()
                && !request.getParameter(Parameters.SURNAME).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}