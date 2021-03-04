package com.potemkin.timetracking.dao.mysqldaoimpl;

import com.potemkin.timetracking.connection.ConnectionPool;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.QueriesDB;
import com.potemkin.timetracking.dao.interfacesdao.UserTypeDAO;
import com.potemkin.timetracking.entities.UserType;
import com.potemkin.timetracking.exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: This class contains implementation of interface methods which works with <i>usertype</i> database table.
 * <p>
 */
public class UserTypeDAOImpl implements UserTypeDAO {
    private static final Logger logger = Logger.getLogger(UserTypeDAOImpl.class);

    private volatile static UserTypeDAOImpl instance;

    private UserTypeDAOImpl() {
    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static UserTypeDAOImpl getInstance() {
        if (instance == null) {
            synchronized (UserTypeDAOImpl.class) {
                if (instance == null) {
                    instance = new UserTypeDAOImpl();
                }
            }
        }
        return instance;
    }

    /**
     * This method creates and inserts an entity in a database table.
     *
     * @param userType   - the current user which has been created.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    @Override
    public void add(UserType userType, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.ADD_USER_TYPE);
            statement.setString(1, userType.getUserType());
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
     * @param userType   - the current entity of user which will be updated.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    @Override
    public void update(UserType userType, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_USER_TYPE_BY_ID);
            statement.setString(1, userType.getUserType());
            statement.setString(2, userType.getUserTypeId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    /**
     * This method deletes an existing record (row) in a database table.
     *
     * @param id         - id number of the current entity which will be deleted.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    @Override
    public void deleteById(int id, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.DELETE_USER_TYPE_BY_ID);
            statement.setInt(1, id);
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
    public UserType getById(int id, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserType userType = new UserType();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_USER_TYPE_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUserType(resultSet, userType);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return userType;
    }
    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param type         - type of the record (row) in the database table..
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - an entity from a database table according to the incoming id number.
     */
    @Override
    public UserType getByType(String type, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserType userType = new UserType();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_USER_TYPE_BY_TYPE);
            statement.setString(1, type);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUserType(resultSet, userType);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return userType;
    }

    /**
     * This method reads and returns information from all records (rows) of a database table.
     *
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - list of all entities from a database table.
     */
    @Override
    public List<UserType> getAll(Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<UserType> users = new ArrayList<>();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ALL_USERS_TYPE);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(createUserType(resultSet, new UserType()));
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return users;
    }

    /**
     * An additional method.
     * This method creates entity of UserType class from data received from ResultSet.
     *
     * @param resultSet - a database result "row" with required values.
     * @param userType  - the entity of User with "null" value for setting corresponding values.
     * @return - created user with fields.
     * @throws SQLException
     */
    private UserType createUserType(ResultSet resultSet, UserType userType) throws SQLException {
        userType.setUserTypeId(resultSet.getInt(Parameters.USER_TYPE_ID));
        userType.setUserType(resultSet.getString(Parameters.USER_TYPE));
        return userType;
    }
}