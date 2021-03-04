package com.potemkin.timetracking.connection;

import com.potemkin.timetracking.constants.MessageConstants;
import com.potemkin.timetracking.exceptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHandler {
    private final static Logger logger = Logger.getLogger(TransactionHandler.class);

    public static void runInTransaction(Transaction transaction, Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        try {
            transaction.execute(connection);
            connection.commit();
            logger.info(MessageConstants.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DAOException e) {
            if (connection != null) {
                connection.rollback();
            }
            logger.error(MessageConstants.TRANSACTION_FAILED, e);
            throw new SQLException(e);
        } finally {
            if(connection != null){
                connection.close();
            }
        }
    }

}
