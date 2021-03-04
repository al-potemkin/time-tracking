package com.potemkin.timetracking.commands.implementations.user;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.commands.implementations.admin.OverviewClientCommand;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SetLanguageCommand implements BasicCommand {
    private static final Logger logger = Logger.getLogger(OverviewClientCommand.class);

    /**
     * This method describes set up language logic.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession(false);
        String currentPage = request.getParameter(Parameters.PAGE);
        String language  = request.getParameter(Parameters.CHOSENLANGUAGE);
        session.setAttribute(Parameters.LANGUAGE, language);
        switch (currentPage) {
            case ("loginPage"): {
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.LOGIN_PAGE_PATH);
                break;
            }
            case ("adminMainPage"): {
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
                break;
            }
            case ("adminOverviewPage"): {
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH_CLIENT_OVERVIEW);
                break;
            }
            case ("clientPage"): {
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
                break;
            }
            case ("registerPage"): {
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.REGISTRATION_PAGE_PATH);
                break;
            }
        }
        logger.info(MessageConstants.SUCCESS_SET_LANGUAGE);
        return page;
    }
}
