package com.potemkin.timetracking.commands.implementations.user;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import com.potemkin.timetracking.session.SessionLogic;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class describes actions of logout logic.
 */
public class LogoutCommand implements BasicCommand {
    private final static Logger logger = Logger.getLogger(LogoutCommand.class);

    /**
     * This method describes the logout logic.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        SessionLogic.flag = true;
        logger.info(MessageConstants.SUCCESS_LOGOUT);
        return ConfigManagerPages.getInstance().getProperty(PathPageConstants.INDEX_PAGE_PATH);
    }
}