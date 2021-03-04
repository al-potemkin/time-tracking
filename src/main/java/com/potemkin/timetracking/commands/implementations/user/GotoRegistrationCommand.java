package com.potemkin.timetracking.commands.implementations.user;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.manager.ConfigManagerPages;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class describes action to redirect guest to the registration page.
 */
public class GotoRegistrationCommand implements BasicCommand {

    /**
     * This method describes action to redirect guest to the registration page.
     *
     * @param request   - request which will be processed.
     * @return          - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        return ConfigManagerPages.getInstance().getProperty(PathPageConstants.REGISTRATION_PAGE_PATH);
    }
}