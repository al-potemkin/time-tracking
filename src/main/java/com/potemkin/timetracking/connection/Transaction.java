package com.potemkin.timetracking.connection;

import com.potemkin.timetracking.exceptions.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public interface Transaction {
    void execute(Connection connection) throws SQLException, DAOException;
}

