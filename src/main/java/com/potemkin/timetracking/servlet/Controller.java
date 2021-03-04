package com.potemkin.timetracking.servlet;

import com.potemkin.timetracking.commands.BasicCommand;
import com.potemkin.timetracking.commands.factory.CommandsFactory;
import com.potemkin.timetracking.constants.PathPageConstants;
import com.potemkin.timetracking.manager.ConfigManagerPages;
import com.potemkin.timetracking.session.SessionLogic;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Description: This class contains method that handles a request from a *.jsp page and defines a command
 * according to the parameter from request.
 * <p>
 */
public class Controller extends HttpServlet {

    public Controller() {
    }

    /**
     * This method defines a command from request that will be executed and set session .
     * This method follows the next steps:
     * - defines the command that received from a *.jsp page;
     * - calls the implemented <i>execute()</i> method and passes parameters to the handler class,
     * which is related to a particular command;
     * - redirects to the required page, it also might be an error page if the required page are not found.
     * <p>
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request  - an object of request from a client.
     * @param response - an object of response from Controller.
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     * <p>
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = SessionLogic.getSession(request);
        if (SessionLogic.isSessionNotAlive(session)) {
            String page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.SESSION_PAGE_PATH);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            CommandsFactory factory = CommandsFactory.getInstance();
            BasicCommand command = factory.defineCommand(request);
            String page = command.execute(request);
            if (page != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            } else {
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.INDEX_PAGE_PATH);
                response.sendRedirect(request.getContextPath() + page);
                SessionLogic.flag = true;
            }
        }


    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}