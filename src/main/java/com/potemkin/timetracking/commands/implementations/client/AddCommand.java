package com.potemkin.timetracking.commands.implementations.client;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.commands.implementations.admin.CreateActivityCommand;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.entities.Tracking;
import com.potemkin.timetracking.entities.User;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import com.potemkin.timetracking.services.ActivityService;
import com.potemkin.timetracking.services.ServiceHelper;
import com.potemkin.timetracking.services.TrackingService;
import com.potemkin.timetracking.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;


public class AddCommand implements BasicCommand {
    private static final Logger logger = Logger.getLogger(CreateActivityCommand.class);
    private ActivityService activityService = (ActivityService) ServiceHelper.getInstance().getService("activityService");
    private UserService userService = (UserService) ServiceHelper.getInstance().getService("userService");
    private TrackingService trackingService = (TrackingService) ServiceHelper.getInstance().getService("trackingService");

    /**
     * This method describes the adding activities logic for client.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        String userId = request.getParameter(Parameters.USER_ID);
        try {
            User clientUser = userService.getUserById(userId);
            clientUser.setRequestAdd(true);
            userService.updateUser(clientUser);
            List<User> userList = userService.getAllUser();
            List<Tracking> trackingList = trackingService.getAllTracking();
            userService.setAttributeClientToSession(clientUser, session);
            userService.setAttributeToSession(trackingList, userList, session);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
            logger.info(MessageConstants.SUCCESS_ADD_REQUEST);
        } catch (SQLException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
