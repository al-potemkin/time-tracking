package com.potemkin.timetracking.services;

import com.potemkin.timetracking.connection.ConnectionPool;
import com.potemkin.timetracking.connection.TransactionHandler;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.dao.interfacesdao.TrackingDAO;
import com.potemkin.timetracking.entities.Tracking;
import com.potemkin.timetracking.entities.User;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class TrackingService {
    private volatile static TrackingService instance;
    private TrackingDAO trackingDao;
    private ConnectionPool connectionPool;

    private TrackingService() {
    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static TrackingService getInstance() {
        if (instance == null) {
            synchronized (TrackingService.class) {
                if (instance == null) {
                    return instance = new TrackingService();
                }
            }
        }
        return instance;
    }

    public void setTrackingDao(TrackingDAO trackingDao) {
        this.trackingDao = trackingDao;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * This method add new tracking entity in DB. This method implements work with transaction support.
     *
     * @param tracking - a new tracking which will be registered.
     * @throws SQLException
     */
    public void registerTracking(Tracking tracking) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                        trackingDao.add(tracking, connection),connectionPool.getConnection()
        );
    }

    /**
     * This method receives all activities from database which belongs corresponding user.
     * This method implements work with transaction support.
     *
     * @return - a list of tracking from the database.
     * @throws SQLException
     */
    public List<Tracking> getTrackingByClientId(User user) throws SQLException {
        final List<Tracking>[] trackingList = new List[1];
        TransactionHandler.runInTransaction(connection ->
                        trackingList[0] = trackingDao.getTrackingByClientId(user, connection),
                connectionPool.getConnection()
        );
        return trackingList[0];
    }

    /**
     * This method receives all activities from database which belongs corresponding user.
     * This method implements work with transaction support.
     *
     * @return - a list of tracking from the database.
     * @throws SQLException
     */
    public Tracking getTrackingById(String trackingId) throws SQLException {
        final Tracking[] tracking = new Tracking[1];
        TransactionHandler.runInTransaction(connection ->
                        tracking[0] = trackingDao.getTrackingById(trackingId, connection),
                connectionPool.getConnection()
        );
        return tracking[0];
    }

    /**
     * This method receives all activities from database which belongs corresponding user.
     * This method implements work with transaction support.
     *
     * @return - a list of tracking from the database.
     * @throws SQLException
     */
    public void deleteTrackingById(Integer trackingId) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                        trackingDao.deleteTrackingById(trackingId, connection),connectionPool.getConnection()
        );
    }

    /**
     * This method receives all trackings from database. This method implements work with transaction support.
     *
     * @return - a list of activities from the database.
     * @throws SQLException
     */
    public List<Tracking> getAllTracking() throws SQLException {
        final List<Tracking>[] trackingList = new List[1];
        TransactionHandler.runInTransaction(connection ->
                        trackingList[0] = trackingDao.getAll(connection),connectionPool.getConnection()
        );
        return trackingList[0];
    }

    /**
     * An additional accessory method that provides work with some attributes of the object of http session.
     * This method sets user's parameters to the session.
     *
     * @param session - an object of the current session.
     */
    public void setAttributeTrackingListToSession(List<Tracking> trackingList, HttpSession session) {
        session.setAttribute(Parameters.TRACKING_LIST, trackingList);
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param id       - the tracking id which will be updated.
     * @param tracking - the tracking which will be updated.
     * @throws SQLException
     */
    public void updateTracking(String id, Tracking tracking) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                        trackingDao.updateTrackingById(id, tracking, connection),connectionPool.getConnection()
        );
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param id - the id number of tracking which will be updated.
     * @throws SQLException
     */
    public void setStatusAndTimeStartTracking(String id, String status, Long startTime) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                trackingDao.setStatusAndStartTime(id, status, startTime, connection),connectionPool.getConnection()
        );
    }

}
