package com.potemkin.timetracking.dao.interfacesdao;

import com.potemkin.timetracking.entities.Activity;
import com.potemkin.timetracking.exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description: This interface describes methods for working with <i>activity</i> database table,
 * extending the capabilities of basic DAO interface.
 */
public interface ActivityDAO extends AbstractDAO<Activity> {

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param id         - id number of the record (row) in the database table..
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - an entity from a database table according to the incoming id number.
     */
    Activity getById(String  id, Connection connection) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param activity   - the current entity of activity which will be updated.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    void update(Activity activity, Connection connection) throws DAOException;

    /**
     * This method check the uniqueness of the activity name.
     *
     * @param activityName - entered <i>login</i>.
     * @param connection   - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - boolean value of the condition.
     * @throws DAOException
     */
    boolean checkUniqueActivity(String activityName, Connection connection) throws DAOException;

    /**
     * An additional method.
     * This method creates entity of Activity class from data received from ResultSet.
     *
     * @param resultSet - a database result "row" with required values.
     * @param activity  - the entity of User with "null" value for setting corresponding values.
     * @return - created user with fields.
     * @throws SQLException
     */
    Activity createActivity(ResultSet resultSet, Activity activity) throws SQLException;

    /**
     * This method check the uniqueness of the activity name in overview client list.
     *
     * @param id - an activity object with fields will be checked.
     * @param userId - a user object with fields will be checked.
     * @return - boolean value of the condition.
     * @throws DAOException
     */
    boolean checkUniqueActivityByUser(String id, String userId, Connection connection) throws DAOException;
}