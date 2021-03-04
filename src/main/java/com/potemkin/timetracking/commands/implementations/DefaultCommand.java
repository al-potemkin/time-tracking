package com.potemkin.timetracking.commands.implementations;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import com.potemkin.timetracking.session.SessionLogic;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class defines action in the case if servlet receives a message without a command.
 * <p>
 */
public class DefaultCommand implements BasicCommand {

    /**
     * The method describes action in the case if servlet receives a message without a command.
     * In this case user will be redirected to the main page of the application index.jsp,
     * which redirects user to the login page login.jsp.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to, in this case it will be the index.jsp.
     */
    @Override
    public String execute(HttpServletRequest request) {
        SessionLogic.flag = true;
        return ConfigManagerPages.getInstance().getProperty(PathPageConstants.INDEX_PAGE_PATH);
    }
}