package com.potemkin.timetracking.commands.implementations.admin;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.entities.Activity;
import com.potemkin.timetracking.entities.Tracking;
import com.potemkin.timetracking.entities.User;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import com.potemkin.timetracking.services.*;
import com.potemkin.timetracking.tag.MyTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Description: This describes actions of registration new user.
 * <p>
 */
public class CreateActivityCommand implements BasicCommand {
    private static final Logger logger = Logger.getLogger(CreateActivityCommand.class);
    private ActivityService activityService = (ActivityService) ServiceHelper.getInstance().getService("activityService");
    private UserService userService = (UserService)ServiceHelper.getInstance().getService("userService");
    private TrackingService trackingService = (TrackingService)ServiceHelper.getInstance().getService("trackingService");

    /**
     * This method describes the adding new activities logic.
     * The method uses methods of the RequestParameterIdentifier and AdminService.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        Activity activity = AdminService.getInstance().geActivityFromRequest(request);
        try {
            if (AdminService.getInstance().areFieldsFilled(request)) {
                if (activityService.isUniqueActivity(activity)) {
                    activityService.createActivityDB(activity);
                    List<Activity> activityAdminList = activityService.getAllActivities();
                    MyTag.activityList = activityAdminList;
                    List<Tracking> trackingList = trackingService.getAllTracking();
                    List<User> userList = userService.getAllUser();
                    userService.setAttributeToSession(activityAdminList, trackingList, userList, session);
                    page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
                    logger.info(MessageConstants.SUCCESS_CREATION);
                } else {
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageConstants.ACTIVITY_EXISTS);
                    List<Activity> activityAdminList = activityService.getAllActivities();
                    List<Tracking> trackingList = trackingService.getAllTracking();
                    List<User> userList = userService.getAllUser();
                    UserService.getInstance().setAttributeToSession(activityAdminList, trackingList, userList, session);
                    page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
                }
            } else {
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageConstants.EMPTY_FIELDS_ACTIVITY);
                List<Activity> activityAdminList = activityService.getAllActivities();
                List<Tracking> trackingList = trackingService.getAllTracking();
                List<User> userList = userService.getAllUser();
                userService.setAttributeToSession(activityAdminList, trackingList, userList, session);
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
            }
        } catch (SQLException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}