package com.potemkin.timetracking.connection;

import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.manager.Config;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {
    private final static Logger logger = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool instance = null;
    private static final String TOMCAT_JNDI_NAME = "java:comp/env";
    private DataSource pool;
    private final String DATASOURCE;

    public ConnectionPool() {
        DATASOURCE = Config.getInstance().getProperty(Config.DATASOURCE);
        initialPool();
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void initialPool() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup(TOMCAT_JNDI_NAME);
            pool = (DataSource) envContext.lookup(DATASOURCE);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection connection = pool.getConnection();
        return connection;
    }

    /**
     * This method closes Statement after the queries have been executed.
     *
     * @param statement - an using statement.
     */
    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error(MessageConstants.STATEMENT_ERROR);
            }
        }
    }

    /**
     * This method closes ResultSet after queries have been executed and information have been processed.
     *
     * @param resultSet - the result from the query.
     */
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error(MessageConstants.RESULTSET_ERROR);
            }
        }
    }
}
