package com.potemkin.timetracking.constants;

/**
 * Description: This class contains the sql-queries to the current database. The queries are formatted in constant view.
 * <p>
 */
public class QueriesDB {

    /*Queries to work with user database table.*/
    public static final String CHECK_AUTHORIZATION = "SELECT login, password FROM user WHERE login = ? AND password = ?;";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM user JOIN user_type ON user.user_type_id = \n" +
            "    user_type.user_type_id  WHERE login = ? ;";
    public static final String GET_USER_BY_ID = "SELECT * FROM user JOIN user_type ON user.user_type_id =" +
            " user_type.user_type_id WHERE user_id = ?;";
    public static final String ADD_NEW_USER_CLIENT = "INSERT INTO user (first_name, sur_name, login, password, user_type_id," +
            " request) VALUES (?, ?, ?, ?, ?, ?);";
    public static final String DELETE_USER_BY_ID = "DELETE FROM user WHERE id = ?;";
    public static final String UPDATE_CLIENT_BY_ID = "UPDATE user SET first_name = ?, sur_name = ?, " +
            "login = ?, password = ?, user_type_id = ?, request = ?  WHERE user_id = ?;";
    public static final String GET_ALL_USERS = "SELECT * FROM user JOIN user_type ON user.user_type_id = \n" +
            "    user_type.user_type_id;";
    public static final String GET_ALL_CLIENTS = "SELECT * FROM user JOIN user_type ON user.user_type_id = \n" +
            "    user_type.user_type_id WHERE user_type_id = 2;";
    /*Queries to work with userType database table.*/
    public static final String ADD_USER_TYPE = "INSERT INTO user_type (user_type_name) VALUES (?);";
    public static final String UPDATE_USER_TYPE_BY_ID = "UPDATE user_type SET user_type_name=? WHERE user_type_id=?;";
    public static final String DELETE_USER_TYPE_BY_ID = "DELETE FROM user_type WHERE user_type_id = ?;";

    public static final String GET_USER_TYPE_BY_ID = "SELECT * FROM user_type WHERE user_type_id = ?;";
    public static final String GET_ALL_USERS_TYPE = "SELECT * FROM user_type;";
    public static final String GET_USER_TYPE_BY_TYPE = "SELECT * FROM user_type WHERE user_type_name = ?;";

    /*Queries to work with activity database table.*/
    public static final String ADD_ACTIVITY = "INSERT INTO activity (activity_name) VALUES (?);";
    public static final String UPDATE_ACTIVITY_BY_ID = "UPDATE activity SET activity_name=? WHERE activity_id=?;";
    public static final String DELETE_ACTIVITY_BY_ID = "DELETE FROM activity WHERE activity_id = ?;";
    public static final String GET_ACTIVITY_BY_ID = "SELECT * FROM activity WHERE activity_id = ?;";
    public static final String GET_ACTIVITY_BY_NAME = "SELECT * FROM activity WHERE activity_name = ?;";
    public static final String GET_ALL_ACTIVITIES = "SELECT * FROM activity;";
    /*Queries to work with tracking database table.*/
    public static final String GET_ACTIVITY_BY_USER_FROM_TRACKING = "SELECT * FROM tracking WHERE activity_id = ? AND" +
            " user_id = ?;";
    public static final String DELETE_TRACKING_BY_USER_ID = "DELETE FROM tracking WHERE user_id = ?;";
    public static final String DELETE_TRACKING_BY_ID = "DELETE FROM tracking WHERE tracking_id = ?;";
    public static final String GET_TRACKING_BY_ID = "SELECT * FROM tracking \n" +
            "    JOIN user ON tracking.user_id = user.user_id\n" +
            "    LEFT JOIN user_request ON tracking.user_request_id = user_request.user_request_id\n" +
            "    JOIN status ON tracking.status_id = status.status_id \n" +
            "    JOIN activity ON tracking.activity_id = activity.activity_id\n" +
            "    JOIN user_type ON user.user_type_id = user_type.user_type_id \n" +
            "    WHERE tracking_id=?;";
    public static final String UPDATE_TRACKING_STATUS_AND_TIME_BY_ID = "UPDATE tracking SET status_id = ?, time = ? " +
            "WHERE tracking_id=?;";
    public static final String UPDATE_TRACKING_TIME = "UPDATE tracking SET status_id = ?, time = ?, " +
            " time_stop = ?, difference_time=?  WHERE tracking_id=?;";
    public static final String UPDATE_TRACKING = "UPDATE tracking SET status_id = ?, user_request_id= ?,time = ?, \n" +
            "     time_start = ?, time_stop = ?, difference_time=?, time_switch =? WHERE tracking_id=?;";
    public static final String UPDATE_TRACKING_STATUS_AND_START_TIME_BY_ID = "UPDATE tracking SET status_id = ?, time_start = ? " +
            "WHERE tracking_id=?;";
    public static final String UPDATE_TRACKING_STATUS_BY_ID = "UPDATE tracking SET status_id = ? WHERE tracking_id=?;";
    public static final String ADD_TRACKING = "INSERT INTO tracking (user_id, activity_id, status_id, user_request_id, time, " +
            "time_start, time_stop, difference_time, time_switch) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String GET_ALL_TRACKING = "SELECT * FROM tracking \n" +
            "    JOIN user ON tracking.user_id = user.user_id\n" +
            "    LEFT JOIN user_request ON tracking.user_request_id = user_request.user_request_id\n" +
            "    JOIN status ON tracking.status_id = status.status_id \n" +
            "    JOIN activity ON tracking.activity_id = activity.activity_id\n" +
            "    JOIN user_type ON user.user_type_id = user_type.user_type_id;";
    public static final String GET_ALL_TRACKING_BY_CLIENT_ID = "SELECT * FROM tracking \n" +
            "JOIN user ON tracking.user_id = user.user_id\n" +
            "JOIN user_request ON tracking.user_request_id = user_request.user_request_id\n" +
            "JOIN status ON tracking.status_id = status.status_id \n" +
            "JOIN activity ON tracking.activity_id = activity.activity_id\n" +
            "JOIN user_type ON user.user_type_id = user_type.user_type_id WHERE user_id = ?";

}
