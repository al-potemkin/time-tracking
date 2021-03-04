package com.potemkin.timetracking.commands.implementations.admin;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.entities.User;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import com.potemkin.timetracking.services.ServiceHelper;
import com.potemkin.timetracking.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class ChosePageCommand implements BasicCommand {
    private static final Logger logger = Logger.getLogger(CreateActivityCommand.class);
    private UserService userService = (UserService)ServiceHelper.getInstance().getService("userService");

    /**
     * itemsPerPage variable sets the amount of displayed items per page.
     */
    public static int itemsPerPage =4;

    /**
     * This method describes pagination logic and allowed us to chose page.
     *
     * @param request - request which will be processed.
     * @return - a page which will be display on admin page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        try {
            List<User> userList = userService.getAllUser();
            List<String> numbersPages = userService.getNumbersPages(userList, itemsPerPage);
            String lasPage = String.valueOf(numbersPages.size());
            String currentPage = request.getParameter("currentPage");
            userService.setPaginationAttributeToSession(numbersPages, lasPage, currentPage,itemsPerPage,
                    session);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
            logger.info(MessageConstants.SUCCESS_CHOOSING_PAGE);
        } catch (SQLException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
