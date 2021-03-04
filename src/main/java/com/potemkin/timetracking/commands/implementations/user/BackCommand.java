package com.potemkin.timetracking.commands.implementations.user;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import com.potemkin.timetracking.session.SessionLogic;
import com.potemkin.timetracking.utils.RequestParameterIdentifier;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class describe action to "back" command.
 */
public class BackCommand implements BasicCommand {

    /**
     * This method describes action to "back" command.
     *
     * @param request   - request which will be processed.
     * @return          - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String pageFromRequest = RequestParameterIdentifier.getPageFromRequest(request);
        String page = null;
        if (pageFromRequest != null && pageFromRequest.equals(Parameters.LOGIN)) {
            SessionLogic.flag = true;
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.LOGIN_PAGE_PATH);
        } else if (pageFromRequest != null && pageFromRequest.equals(Parameters.CLIENT)){
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
        } else if (pageFromRequest != null && pageFromRequest.equals(Parameters.ADMIN)) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
        }
        return page;
    }
}