package com.potemkin.timetracking.dao.mysqldaoimpl;

import com.potemkin.timetracking.connection.ConnectionPool;
import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.constants.Parameters;
import com.potemkin.timetracking.constants.QueriesDB;
import com.potemkin.timetracking.dao.interfacesdao.UserDAO;
import com.potemkin.timetracking.entities.User;
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
 * Description: This class contains implementation of interface methods which works with <i>user</i> database table.
 * <p>
 */
public class UserDAOImpl implements UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

    private volatile static UserDAOImpl instance;

    private UserDAOImpl() {
    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static UserDAOImpl getInstance() {
        if (instance == null) {
            synchronized (UserDAOImpl.class) {
                if (instance == null) {
                    instance = new UserDAOImpl();
                }
            }
        }
        return instance;
    }

    /**
     * This method creates and inserts an entity in a database table.
     *
     * @param user       - the current user which has been created.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    @Override
    public void add(User user, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.ADD_NEW_USER_CLIENT);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSurName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setInt(5, 2);
            statement.setBoolean(6, user.getRequestAdd());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    /**
     * This method checks the availability of the <i>login</i> and <i>password</i> in the <i>users</i> database table.
     *
     * @param login      - entered <i>login</i> filed of the user.
     * @param password   - entered <i>password</i> field of the user.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - boolean value as a result:
     * returns "true" if the incoming data correspond to the record of the database table;
     * returns "false" if the incoming data do not correspond to the record of the database table.
     */
    @Override
    public boolean isAuthorized(String login, String password, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean isLogined = false;
        try {
            statement = connection.prepareStatement(QueriesDB.CHECK_AUTHORIZATION);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isLogined = true;
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return isLogined;
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param user       - the current entity of user which will be updated.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    @Override
    public void update(User user, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(QueriesDB.UPDATE_CLIENT_BY_ID);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSurName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setInt(5, 2);
            statement.setBoolean(6, user.getRequestAdd());
            statement.setInt(7, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeStatement(statement);
        }
    }

    /**
     * This method reads data from <i>users</i> database table, creates and returns User object according to the entered login.
     *
     * @param login      - entered <i>login</i>.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - User object.
     */
    @Override
    public User getByLogin(String login, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_USER_BY_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUser(resultSet, user);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return user;
    }

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param id         - id number of the record (row) in the database table..
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - an entity from a database table according to the incoming id number.
     */
    @Override
    public User getById(String id, Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_USER_BY_ID);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUser(resultSet, user);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return user;
    }

    /**
     * This method reads and returns information from all records (rows) of a database table.
     *
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - list of all entities from a database table.
     */
    @Override
    public List<User> getAll(Connection connection) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            statement = connection.prepareStatement(QueriesDB.GET_ALL_USERS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(createUser(resultSet, new User()));
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
     * This method creates entity of User class from data received from ResultSet.
     *
     * @param resultSet - a database result "row" with required values.
     * @param user      - the entity of User with "null" value for setting corresponding values.
     * @return - created user with fields.
     * @throws SQLException
     */
    public User createUser(ResultSet resultSet, User user) throws SQLException {
        user.setUserId(resultSet.getInt(Parameters.USER_ID_DB));
        user.setFirstName(resultSet.getString(Parameters.FIRST_NAME_DB));
        user.setSurName(resultSet.getString(Parameters.SURNAME_DB));
        user.setLogin(resultSet.getString(Parameters.LOGIN));
        user.setPassword(resultSet.getString(Parameters.PASSWORD));
        user.setUserType(new UserType(resultSet.getInt(Parameters.USER_TYPE_ID_DB)
                , resultSet.getString(Parameters.USER_TYPE_NAME_DB)));
        user.setRequestAdd(resultSet.getBoolean(Parameters.REQUEST));
        return user;
    }

    /**
     * This method check the uniqueness of the user.
     *
     * @param login      - entered <i>login</i>.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - boolean value of the condition.
     * @throws DAOException
     */
    @Override
    public boolean checkUniqueUser(String login, Connection connection) throws DAOException {
        boolean isUniqueUser = true;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(QueriesDB.GET_USER_BY_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isUniqueUser = false;
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } finally {
            ConnectionPool.closeResultSet(resultSet);
            ConnectionPool.closeStatement(statement);
        }
        return isUniqueUser;
    }
}