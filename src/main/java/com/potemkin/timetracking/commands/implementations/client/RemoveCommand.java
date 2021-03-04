package com.potemkin.timetracking.commands.implementations.client;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.commands.implementations.admin.BackAdminCommand;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.entities.Tracking;
import com.potemkin.timetracking.entities.UserRequest;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import com.potemkin.timetracking.services.ServiceHelper;
import com.potemkin.timetracking.services.TrackingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class RemoveCommand implements BasicCommand {
    private static final Logger logger = Logger.getLogger(BackAdminCommand.class);
    private TrackingService trackingService = (TrackingService) ServiceHelper.getInstance().getService("trackingService");

    /**
     * This method describes remove logic that allows client to remove finished activities.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        String trackingId = request.getParameter(Parameters.TRACKING_ID);
        try {
            Tracking tracking = trackingService.getTrackingById(trackingId);
            tracking.setUserRequest(UserRequest.REMOVE);
            trackingService.updateTracking(trackingId, tracking);
            List<Tracking> trackingList = trackingService.getAllTracking();
            trackingService.setAttributeTrackingListToSession(trackingList, session);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
        } catch (SQLException e) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
