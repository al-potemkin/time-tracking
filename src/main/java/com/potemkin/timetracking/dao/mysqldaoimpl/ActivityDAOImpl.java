package com.potemkin.timetracking.dao.mysqldaoimpl;

import com.potemkin.timetracking.connection.ConnectionPool;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.QueriesDB;
import com.potemkin.timetracking.dao.interfacesdao.ActivityDAO;
import com.potemkin.timetracking.entities.Activity;
import com.potemkin.timetracking.exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: This class contains implementation of interface methods which works with <i>activity</i> database table.
 * <p>
 */
public class ActivityDAOImpl implements ActivityDAO {
    private static final Logger logger = Logger.getLogger(UserTypeDAOImpl.class);

    private volatile static ActivityDAOImpl instance;

    private ActivityDAOImpl() {
    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static ActivityDAOImpl getInstance() {
        if (instance == null) {
            synchronized (ActivityDAOImpl.class) {
                if (instance == null) {
                    instance = new ActivityDAOImpl();
                }
            }
        }
        return instance;
    }

    /**
     * This method creates and inserts an entity in a database table.
     *
     * @param activity   - the current user which has been created.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    @Override
    public void add(Activity activity, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.ADD_ACTIVITY);
            statement.setString(1, activity.getActivityName());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param activity   - the current entity of user which will be updated.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    @Override
    public void update(Activity activity, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_ACTIVITY_BY_ID);
            statement.setString(1, activity.getActivityName());
            statement.setString(2, activity.getActivityId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param id         - id number of the record (row) in the database table..
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - an entity from a database table according to the incoming id number.
     */
    @Override
    public Activity getById(String id, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Activity activity = new Activity();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ACTIVITY_BY_ID);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createActivity(resultSet, activity);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return activity;
    }

    /**
     * This method reads and returns information from all records (rows) of a database table.
     *
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - list of all entities from a database table.
     */
    @Override
    public List<Activity> getAll(Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Activity> activities = new ArrayList<>();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ALL_ACTIVITIES);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                activities.add(createActivity(resultSet, new Activity()));
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return activities;
    }

    /**
     * An additional method.
     * This method creates entity of Activity class from data received from ResultSet.
     *
     * @param resultSet - a database result "row" with required values.
     * @param activity  - the entity of User with "null" value for setting corresponding values.
     * @return - created user with fields.
     * @throws SQLException
     */
    @Override
    public Activity createActivity(ResultSet resultSet, Activity activity) throws SQLException {
        activity.setActivityId(resultSet.getInt(Parameters.ACTIVITY_ID_DB));
        activity.setActivityName(resultSet.getString(Parameters.ACTIVITY_NAME_DB));
        return activity;
    }

    /**
     * This method check the uniqueness of the activity name.
     *
     * @param activityName - entered <i>login</i>.
     * @param connection   - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - boolean value of the condition.
     * @throws DAOException
     */
    @Override
    public boolean checkUniqueActivity(String activityName, Connection connection) throws DAOException {
        boolean isUniqueActivity = true;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ACTIVITY_BY_NAME);
            statement.setString(1, activityName);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isUniqueActivity = false;
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return isUniqueActivity;
    }

    /**
     * This method check the uniqueness of the activity name in overview client list.
     *
     * @param id     - an activity object with fields will be checked.
     * @param userId - a user object with fields will be checked.
     * @return - boolean value of the condition.
     * @throws DAOException
     */
    @Override
    public boolean checkUniqueActivityByUser(String id, String userId, Connection connection) throws DAOException {
        boolean isUniqueActivity = true;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ACTIVITY_BY_USER_FROM_TRACKING);
            statement.setString(1, id);
            statement.setString(2, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isUniqueActivity = false;
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return isUniqueActivity;
    }
}