package com.potemkin.timetracking.dao.interfacesdao;

import com.potemkin.timetracking.entities.Tracking;
import com.potemkin.timetracking.entities.User;
import com.potemkin.timetracking.exceptions.DAOException;

import java.sql.Connection;
import java.util.List;

/**
 * Description: This interface describes methods for working with <i>activity</i> database table,
 * extending the capabilities of basic DAO interface.
 */
public interface TrackingDAO extends AbstractDAO<Tracking> {

    /**
     * This method deletes an existing record (row) in a database table.
     *
     * @param id            - id number of the current entity which will be deleted.
     * @param connection    - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    void deleteTrackingByUserId(int id, Connection connection) throws DAOException;
    /**
     * This method deletes an existing record (row) in a database table.
     *
     * @param id            - id number of the current entity which will be deleted.
     * @param connection    - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    void deleteTrackingById(Integer id, Connection connection) throws DAOException ;

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param id            - id number of the record (row) in the database table..
     * @param connection    - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return              - an entity from a database table according to the incoming id number.
     */
    Tracking getTrackingById(String id, Connection connection) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param tracking   - the current entity of user which will be updated.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    void updateTrackingById(String id, Tracking tracking, Connection connection) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param tracking      - the current entity of activity which will be updated.
     * @param connection    - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    void updateTrackingStatusAndTimeByID (Tracking tracking, Connection connection) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param tracking      - the current entity of activity which will be updated.
     * @param connection    - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    void updateTrackingStatusByID (Tracking tracking, Connection connection) throws DAOException;

    /**
     * This method reads and returns information from all records (rows) of a database table.
     *
     * @param user - user which activities will have retrieved from tracking table in DB.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return - list of all entities from a database table.
     */
    List<Tracking> getTrackingByClientId(User user, Connection connection) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param id         - the id number of tracking which will be updated.
     * @param status     - the status of tracking which will be updated.
     * @param startTime  - the start time of tracking which will be updated.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    void setStatusAndStartTime(String id, String status, Long startTime, Connection connection) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param id         - the id number of tracking which will be updated.
     * @param status     - the status of tracking which will be updated.
     * @param time       - the time of tracking which will be updated.
     * @param connection - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    void setStatusAndTime(String id, String status, String time, Connection connection) throws DAOException;
}
