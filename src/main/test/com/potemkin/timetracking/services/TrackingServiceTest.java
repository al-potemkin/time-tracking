package com.potemkin.timetracking.services;

import com.potemkin.timetracking.connection.ConnectionPool;
import com.potemkin.timetracking.dao.interfacesdao.TrackingDAO;
import com.potemkin.timetracking.entities.Tracking;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackingServiceTest {
    private TrackingService trackingService;
    private TrackingDAO trackingDaoMock;
    private ConnectionPool connectionPoolMock;
    private Connection connectionMock;
    private User user;
    private Tracking tracking;

    @Before
    public void setUp() throws SQLException {
        trackingDaoMock = mock(TrackingDAO.class);
        trackingService = TrackingService.getInstance();
        trackingService.setTrackingDao(trackingDaoMock);
        connectionPoolMock = mock(ConnectionPool.class);
        trackingService.setConnectionPool(connectionPoolMock);
        connectionMock = mock(Connection.class);
        user = new User( "Bob", "Bobob", "admin", "admin",
                new UserType(2, "admin"), null);
        user.setUserId(1);
        tracking = new Tracking ();
        tracking.setUser(user);
        tracking.setTrackingId(1);
        when(connectionPoolMock.getConnection()).thenReturn(connectionMock);
    }

    @Test
    public void getTrackingByClientIdSuccess() throws DAOException, SQLException {
        List<Tracking> expectedTracking = new ArrayList<>();
        expectedTracking.add(tracking);
        when(trackingDaoMock.getTrackingByClientId(eq(user),eq(connectionMock))).thenReturn(expectedTracking);
        List<Tracking> actualTracking = trackingService.getTrackingByClientId(user);
        assertEquals(expectedTracking, actualTracking);
    }

    @Test
    public void getTrackingByIdSuccess() throws DAOException, SQLException {
        Tracking expectedTracking = tracking;
        when(trackingDaoMock.getTrackingById(eq(tracking.getTrackingId().toString()),eq(connectionMock)))
                .thenReturn(expectedTracking);
        Tracking actualTracking = trackingService.getTrackingById(tracking.getTrackingId().toString());
        assertEquals(expectedTracking, actualTracking);
    }

    @Test
    public void getAllTracking() throws DAOException, SQLException {
        List<Tracking> expectedTrackingList = new ArrayList<>();
        expectedTrackingList.add(tracking);
        when(trackingDaoMock.getAll(eq(connectionMock))).thenReturn(expectedTrackingList);
        List<Tracking> actualTrackingList = trackingService.getAllTracking();
        assertEquals(expectedTrackingList, actualTrackingList);
    }
}