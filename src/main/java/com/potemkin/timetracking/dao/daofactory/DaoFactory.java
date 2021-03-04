package com.potemkin.timetracking.dao.daofactory;


import com.potemkin.timetracking.dao.interfacesdao.ActivityDAO;
import com.potemkin.timetracking.dao.interfacesdao.TrackingDAO;
import com.potemkin.timetracking.dao.interfacesdao.UserDAO;

/**
 * Description: This abstract class allowed to work with different type of databases,
 * and return the DAO object according the database we choose.
 * <p>
 */
public abstract class DaoFactory {

    /**
     * List of DAO types supported by the factory
     */
    public static final int MYSQL = 1;
    public static final int ORACLE = 2;
    public static final int SYBASE = 3;

    /**
     * The concrete factories will have to implement these methods.
     */
    public abstract ActivityDAO getActivityDao();

    public abstract TrackingDAO getTrackingDao();

    public abstract UserDAO getUserDao();


    /**
     * This method return the object of concrete factory
     *
     * @param whichFactory  - type of factory which will be created.     *
     * @return              - object DAO factory.
     */
    public static DaoFactory getDaoFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL:
                return new MySqlDaoFactory();
            case ORACLE:
                //return new OracleDAOFactory();
            case SYBASE:
                //return new SybaseDAOFactory();
            default:
                return null;
        }
    }
}

