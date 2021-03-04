package com.potemkin.timetracking.services;

import com.potemkin.timetracking.connection.ConnectionPool;
import com.potemkin.timetracking.connection.TransactionHandler;
import com.potemkin.timetracking.dao.interfacesdao.ActivityDAO;
import com.potemkin.timetracking.entities.Activity;

import java.sql.SQLException;
import java.util.List;

/**
 * Description: This class describes actions on the user object.
 * This class contains methods that implement work with transaction support.
 * <p>
 */
public class ActivityService {

    private volatile static ActivityService instance;
    private ActivityDAO activityDao;
    private ConnectionPool connectionPool;

    private ActivityService() {
    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static ActivityService getInstance() {
        if (instance == null) {
            synchronized (ActivityService.class) {
                if (instance == null) {
                    return instance = new ActivityService();
                }
            }
        }
        return instance;
    }

    public void setActivityDao(ActivityDAO activityDao) {
        this.activityDao = activityDao;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * This method add new activity in DB. This method implements work with transaction support.
     *
     * @param activity - a new user which will be registered.
     * @throws SQLException
     */
    public void createActivityDB(Activity activity) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                activityDao.add(activity, connection),connectionPool.getConnection()
        );
    }

    /**
     * This method receives user object. This method implements work with transaction support.
     *
     * @param id - entered id.
     * @return - User object.
     */
    public Activity getActivityById(String id) throws SQLException {
        final Activity[] activity = new Activity[1];
        TransactionHandler.runInTransaction(connection ->
                activity[0] = activityDao.getById(id, connection),connectionPool.getConnection()
        );
        return activity[0];
    }

    /**
     * This method receives all activities from database. This method implements work with transaction support.
     *
     * @return - a list of activities from the database.
     * @throws SQLException
     */
    public List<Activity> getAllActivities() throws SQLException {
        final List<Activity>[] activityList = new List[1];
        TransactionHandler.runInTransaction(connection ->
                activityList[0] = activityDao.getAll(connection),connectionPool.getConnection()
        );
        return activityList[0];
    }

      /**
     * This method checks the uniqueness of the activity. This method implements work with transaction support.
     *
     * @param activity - an activity object with fields will be checked.
     * @return - boolean value of the condition.
     * @throws SQLException
     */
    public boolean isUniqueActivity(Activity activity) throws SQLException {
        final boolean[] isUnique = new boolean[1];
        TransactionHandler.runInTransaction(connection ->
                isUnique[0] = activityDao.checkUniqueActivity(activity.getActivityName(), connection),
                connectionPool.getConnection()
        );
        return isUnique[0];
    }

    /**
     * This method checks the uniqueness of the activity. This method implements work with transaction support.
     *
     * @param id - an activity object with fields will be checked.
     * @param userId - a user object with fields will be checked.
     * @return - boolean value of the condition.
     * @throws SQLException
     */
    public boolean isUniqueClientActivity(String id, String userId) throws SQLException {
        final boolean[] isUnique = new boolean[1];
        TransactionHandler.runInTransaction(connection ->
                isUnique[0] = activityDao.checkUniqueActivityByUser(id, userId, connection),
                connectionPool.getConnection()
        );
        return isUnique[0];
    }

}