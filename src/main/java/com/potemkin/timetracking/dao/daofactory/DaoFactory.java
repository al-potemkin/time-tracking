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
     * The concrete factories will have to implement these methods.
     */
    public abstract ActivityDAO getActivityDao();

    public abstract TrackingDAO getTrackingDao();

    public abstract UserDAO getUserDao();


    /**
     * This method return the object of concrete factory
     *
     * @return              - object DAO factory.
     */
    public static DaoFactory getDaoFactory() {
        return new MySqlDaoFactory();
    }
}

