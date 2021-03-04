package com.potemkin.timetracking.commands.implementations.user;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.commands.implementations.admin.ChosePageCommand;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.entities.Activity;
import com.potemkin.timetracking.entities.Tracking;
import com.potemkin.timetracking.entities.User;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import com.potemkin.timetracking.services.ActivityService;
import com.potemkin.timetracking.services.ServiceHelper;
import com.potemkin.timetracking.services.TrackingService;
import com.potemkin.timetracking.services.UserService;
import com.potemkin.timetracking.utils.RequestParameterIdentifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Description: This class describes actions of login logic.
 * <p>
 */
public class LoginCommand implements BasicCommand {
    private final static Logger logger = Logger.getLogger(LoginCommand.class);
    private ActivityService activityService = (ActivityService) ServiceHelper.getInstance().getService("activityService");
    private UserService userService = (UserService) ServiceHelper.getInstance().getService("userService");
    private TrackingService trackingService = (TrackingService) ServiceHelper.getInstance().getService("trackingService");

    /**
     * This method describes the logon logic. The method uses methods of the RequestParameterIdentifier and AdminService
     * classes and works according to the following steps:
     * - getting an user object from request object using login and password saved in the corresponding request
     * object using the <i>getUserLoginPasswordFromRequest(...)<i/> method;
     * - checking user's authorization using the <i>checkUserAuthorization(...)</i> method;
     * - if the user is authorized the user's are created using the <i>getUserByLogin(...)<i/> method;
     * - generating the page according to the user's type (client or admin).
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        User user = RequestParameterIdentifier.getUserLoginPasswordFromRequest(request);
        HttpSession session = request.getSession(false);
        try {
            if (userService.checkUserAuthorization(user.getLogin(), user.getPassword())) {
                List<Activity> activityAdminList = activityService.getAllActivities();
                List<Tracking> trackingList = trackingService.getAllTracking();
                List<User> userList = userService.getAllUser();
                int itemsPerPage = ChosePageCommand.itemsPerPage;
                List<String> numbersPages = userService.getNumbersPages(userList, itemsPerPage);
                String lastPage = String.valueOf(numbersPages.size());
                String currentPage = "1";
                userService.setPaginationAttributeToSession(numbersPages, lastPage, currentPage,
                        itemsPerPage, session);
                user = userService.getUserByLogin(user.getLogin());
                userService.setAttributeToSession(activityAdminList, trackingList, userList, session);
                switch (user.getUserType().getUserType()) {
                    case "admin":
                        User adminUser = user;
                        userService.setAttributeAdminToSession(adminUser, session);
                        page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
                        break;
                    case "client":
                        User clientUser = user;
                        userService.setAttributeClientToSession(clientUser, session);
                        page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
                        break;
                    default:
                        page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
                        break;
                }
                logger.info(MessageConstants.SUCCESS_LOGIN);
            } else {
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.INDEX_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_LOGIN_PASSWORD, MessageConstants.WRONG_LOGIN_OR_PASSWORD);
            }
        } catch (SQLException e) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}