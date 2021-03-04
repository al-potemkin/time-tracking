package com.potemkin.timetracking.services;

import com.potemkin.timetracking.connection.ConnectionPool;
import com.potemkin.timetracking.dao.interfacesdao.ActivityDAO;
import com.potemkin.timetracking.entities.Activity;
import com.potemkin.timetracking.entities.User;
import com.potemkin.timetracking.exceptions.DAOException;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActivityServiceTest {
    private ActivityService activityService;
    private ActivityDAO activityDaoMock;
    private ConnectionPool connectionPoolMock;
    private Connection connectionMock;
    private User user;
    private Activity activity;

    @Before
    public void setUp() throws SQLException {
        activityDaoMock = mock(ActivityDAO.class);
        activityService = ActivityService.getInstance();
        activityService.setActivityDao(activityDaoMock);
        connectionPoolMock = mock(ConnectionPool.class);
        activityService.setConnectionPool(connectionPoolMock);
        connectionMock = mock(Connection.class);
        activity = new Activity();
        activity.setActivityId(1);
        user = new User();
        user.setUserId(1);
        when(connectionPoolMock.getConnection()).thenReturn(connectionMock);
    }

    @Test
    public void getActivityByIdSuccess() throws DAOException, SQLException {
        when(activityDaoMock.getById(eq(activity.getActivityId().toString()), eq(connectionMock)))
                .thenReturn(activity);
        Activity expectedActivity = activity;
        Activity actualActivity = activityService.getActivityById(activity.getActivityId().toString());
        assertEquals(expectedActivity, actualActivity);
    }

    @Test
    public void getAllActivitiesSuccess() throws DAOException, SQLException {
        List<Activity> expectedActivityList = new ArrayList<>();
        expectedActivityList.add(activity);
        when(activityDaoMock.getAll(eq(connectionMock))).thenReturn(expectedActivityList);
        List<Activity> actualActivityList = activityService.getAllActivities();
        assertEquals(expectedActivityList, actualActivityList);
    }

    @Test
    public void isUniqueActivitySuccess() throws DAOException, SQLException {
        when(activityDaoMock.checkUniqueActivity(eq(activity.getActivityName()), eq(connectionMock)))
                .thenReturn(true);
        boolean result = activityService.isUniqueActivity(activity);
        assertTrue(result);
    }

    @Test
    public void isUniqueClientActivitySuccess() throws DAOException, SQLException {
        when(activityDaoMock.checkUniqueActivityByUser(eq(activity.getActivityId().toString()),
                eq(user.getUserId().toString()), eq(connectionMock))).thenReturn(true);
        boolean result = activityService.isUniqueClientActivity(activity.getActivityId().toString(),
                user.getUserId().toString());
        assertTrue(result);
    }
}