package com.potemkin.timetracking.services;

import com.potemkin.timetracking.connection.ConnectionPool;
import com.potemkin.timetracking.dao.interfacesdao.UserDAO;
import com.potemkin.timetracking.entities.User;
import com.potemkin.timetracking.entities.UserType;
import com.potemkin.timetracking.exceptions.DAOException;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserService userService;
    private UserDAO userDaoMock;
    private ConnectionPool connectionPoolMock;
    private Connection connectionMock;
    private User user;

    @Before
    public void setUp() throws SQLException {
        userDaoMock = mock(UserDAO.class);
        userService = UserService.getInstance();
        userService.setUserDao(userDaoMock);
        connectionPoolMock = mock(ConnectionPool.class);
        userService.setConnectionPool(connectionPoolMock);
        connectionMock = mock(Connection.class);
        user = new User( "Ievgen", "Kopachev", "admin", "admin",
                new UserType(2, "admin"), null);
        when(connectionPoolMock.getConnection()).thenReturn(connectionMock);
    }

    @Test
    public void checkUserAuthorizationSuccess() throws SQLException, DAOException {
        when(userDaoMock.isAuthorized(eq("admin"),eq( "admin"), any(Connection.class)))
                .thenReturn(true);
        boolean result = userService.checkUserAuthorization("admin", "admin");
        assertTrue(result);
    }

    @Test (expected = SQLException.class)
    public void checkUserAuthorizationException() throws SQLException, DAOException {
        when(userDaoMock.isAuthorized(eq("admin"),eq( "admin"), any(Connection.class)))
                .thenThrow(DAOException.class);
        boolean result = userService.checkUserAuthorization("admin", "admin");
        assertTrue(result);
    }

    @Test
    public void getUserByLoginSuccess() throws Exception {
        when(userDaoMock.getByLogin(eq("admin"), eq(connectionMock)))
                .thenReturn(user);
        User result = userService.getUserByLogin("admin");
        assertEquals(user, result);
    }

    @Test (expected = SQLException.class)
    public void getUserByLoginFailed() throws Exception {
        when(userDaoMock.getByLogin(eq("admin"), eq(connectionMock)))
                .thenThrow(DAOException.class);
        User result = userService.getUserByLogin("admin");
    }

    @Test
    public void getUserByIdSuccess() throws SQLException, DAOException {
        user.setUserId(2);
        when(userDaoMock.getById(eq(user.getUserId().toString()), eq(connectionMock)))
                .thenReturn(user);
        User result = userService.getUserById(user.getUserId().toString());
        assertEquals(user, result);
    }

    @Test
    public void isUniqueUserSuccess() throws DAOException, SQLException {
        when(userDaoMock.checkUniqueUser(eq(user.getLogin()), any(Connection.class)))
                .thenReturn(true);
        boolean result = userService.isUniqueUser(user);
        assertTrue(result);
    }

    @Test
    public void getAllUserSuccess() throws DAOException, SQLException {
        List<User> expectedUserList = new ArrayList<>();
        expectedUserList.add(user);
        when(userDaoMock.getAll(eq(connectionMock))).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getAllUser();
        assertEquals(expectedUserList, actualUserList);
    }

    @Test
    public void getNumbersPagesSuccess() {
        List<User> userList = new ArrayList<>(10);
        for (int i =0; i<11; i++){
            userList.add(user);
        }
        int itemsPerPage = 3;
        List<String> expectedPagesList = new ArrayList<>();
        expectedPagesList.add("1");
        expectedPagesList.add("2");
        expectedPagesList.add("3");
        expectedPagesList.add("4");
        List<String> actualPagesList = userService.getNumbersPages(userList,itemsPerPage);
        assertEquals(expectedPagesList,actualPagesList);
    }
}