package com.potemkin.timetracking.commands.implementations.user;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.entities.User;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import com.potemkin.timetracking.services.ServiceHelper;
import com.potemkin.timetracking.services.UserService;
import com.potemkin.timetracking.session.SessionLogic;
import com.potemkin.timetracking.utils.RequestParameterIdentifier;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Description: This describes actions of registration new user.
 * <p>
 */
public class RegistrationCommand implements BasicCommand {
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);
    private UserService userService = (UserService) ServiceHelper.getInstance().getService("userService");

    /**
     * This method describes the registration logic. The method uses methods of the RequestParameterIdentifier and AdminService.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page ;
        User user = RequestParameterIdentifier.getUserFromRequest(request);
        try {
            if (RequestParameterIdentifier.areFieldsFilled(request)) {
                if (userService.isUniqueUser(user)) {
                    userService.registerUser(user);
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageConstants.SUCCESS_REGISTRATION);
                    page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.LOGIN_PAGE_PATH);
                    logger.info(MessageConstants.SUCCESS_REGISTRATION);
                    SessionLogic.flag = true;
                } else {
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageConstants.USER_EXISTS);
                    page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.REGISTRATION_PAGE_PATH);
                }
            } else {
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageConstants.EMPTY_FIELDS);
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.REGISTRATION_PAGE_PATH);
            }
        } catch (SQLException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}