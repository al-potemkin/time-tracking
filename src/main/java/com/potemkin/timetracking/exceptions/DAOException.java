package com.potemkin.timetracking.exceptions;

import java.sql.SQLException;

/**
 * Description: This class send exception to service module.
 */
public class DAOException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public DAOException(String message) {
    }

    /**
     * This method constructs a new exception with the specified detail message and cause.
     * Note that the detail message associated with {@code e} is not automatically incorporated in
     * this exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param e   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     */
    public DAOException(String message, SQLException e) {
    }
}
