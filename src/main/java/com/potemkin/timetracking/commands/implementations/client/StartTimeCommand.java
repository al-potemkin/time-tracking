package com.potemkin.timetracking.commands.implementations.client;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.commands.implementations.admin.BackAdminCommand;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.entities.ActivityStatus;
import com.potemkin.timetracking.entities.Tracking;
import com.potemkin.timetracking.entities.User;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import com.potemkin.timetracking.services.*;
import com.potemkin.timetracking.timer.Time;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Description: This class describes action for starting timer.
 * <p>
 */
public class StartTimeCommand implements BasicCommand {
    private static final Logger logger = Logger.getLogger(BackAdminCommand.class);
    private TrackingService trackingService = (TrackingService) ServiceHelper.getInstance().getService("trackingService");

    /**
     * This method start te Time counter.
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
            User trackingUser = tracking.getUser();
            List<Tracking> trackingList = trackingService.getAllTracking();
            if (ClientService.getInstance().ifUserHasNoOpenActivity(trackingUser, trackingList)) {
                Time.getInstance().start();
                tracking = trackingService.getTrackingById(trackingId);
                tracking.setTimeSwitch(true);
                trackingService.updateTracking(trackingId, tracking);
                trackingService.setStatusAndTimeStartTracking(trackingId,
                        ActivityStatus.IN_PROGRESS.toString(), Time.getInstance().getStartTime());
                trackingList = trackingService.getAllTracking();
                trackingService.setAttributeTrackingListToSession(trackingList, session);
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
            } else {
                Tracking activeTracking = ClientService.getInstance().getActiveTracking(trackingList);
                activeTracking = ClientService.getInstance().setUpDifferenceTime(activeTracking);
                trackingService.updateTracking(activeTracking.getTrackingId().toString(), activeTracking);
                trackingList = trackingService.getAllTracking();
                trackingService.setAttributeTrackingListToSession(trackingList, session);
                request.setAttribute("duplicateStart", "true");
                request.setAttribute("trackingId", trackingId);
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
            }
        } catch (SQLException e) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
